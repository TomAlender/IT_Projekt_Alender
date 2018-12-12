package de.hdm.itProjektAlender.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.client.LikeBeitragWrapper;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Beitrag;
import de.hdm.itProjektAlender.shared.bo.Kommentar;
import de.hdm.itProjektAlender.shared.bo.Like;
import de.hdm.itProjektAlender.shared.bo.Nutzer;
import de.hdm.itProjektAlender.shared.bo.Pinnwand;

public class MeinePinnwandForm extends VerticalPanel {

	Label user = new Label();
	TextArea text = new TextArea();
	

	int nutzer = 0;
	int anzahlLike;
	int pinnwandId = 0;

	Label kommtext = null;
	Label kommdatum = null;
	Label kommNickname = new Label();

	Button neuBtn = new Button("Neuen Beitrag erstellen");
	Button neuKommBtn = null;
	VerticalPanel head = new VerticalPanel();
	DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
	final FlexTable likeTbl = new FlexTable();
	final Label likeNutzer = new Label();
	Button likebtn = new Button();
	Label likecount = new Label();
	HorizontalPanel btnpanel = null;
	VerticalPanel beitragPanel = null;
	ScrollPanel scroll = new ScrollPanel();
	FlexTable innerFlex = null;
	VerticalPanel kommPanel = null;
	String textareastring = "";

	Label testlabel = null;
	Label datum = null;

	
	Button edit = null;
	Button kommedit = null;
	boolean pinn = true;

	final FlexTable flexTable = new FlexTable();

	SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();

	VerticalPanel beitrag = new VerticalPanel();
	

	MeinePinnwandForm(final int nutzerId) {
		this.nutzer = nutzerId;
		text.setVisibleLines(2);
		scroll.setSize("900px", "470px");
		socialMedia.findNutzerById(nutzerId, new NutzerAsyncCallback());
		socialMedia.findPinnwandByNutzer(nutzerId, new FindPinnwandCallBack());
		socialMedia.findBeitragAufEigenerPinnwandWrapper(nutzerId, new FindBeitraegeCallback());

		head.add(user);
		head.add(text);
		head.add(neuBtn);
		scroll.add(flexTable);
		this.add(head);
		this.add(scroll);
		
	}

	public class FindBeitraegeCallback implements AsyncCallback<Vector<LikeBeitragWrapper>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<LikeBeitragWrapper> result) {
			for (LikeBeitragWrapper beitrag : result) {

				pinnwandId = beitrag.getBeitrag().getPinnwand_Id();
				int numRows = flexTable.getRowCount();
				final int beitragId = beitrag.getBeitrag().getId();

				btnpanel = new HorizontalPanel();
				beitragPanel = new VerticalPanel();

				testlabel = new Label();
				datum = new Label();

				
				edit = new Button("Edit");
				edit.setStylePrimaryName("like-btn");
				likebtn = new Button();
				likecount = new Label();
				likebtn.setStylePrimaryName("like-btn");
				

				final TextArea kommtextarea = new TextArea();

				neuKommBtn = new Button("Neuer Kommentar");
				neuKommBtn.setStylePrimaryName("neu-btn");
				if (beitrag.isLike() == true) {
					likebtn.setText("Unlike");
					likebtn.addClickHandler(new LikebtnClickhandler(nutzer, beitrag.getBeitrag().getId()));
				} else {
					likebtn.setText("Like");
					likebtn.addClickHandler(new LikeClickhandler(nutzer, beitrag.getBeitrag().getId()));
				}

				if (beitrag.getAnzahlLikes() > 1) {
					likecount.setText(beitrag.getAnzahlLikes() + " Likes");
				} else if (beitrag.getAnzahlLikes() == 1) {
					likecount.setText(beitrag.getAnzahlLikes() + " Like");
				}

				datum.setText(DateTimeFormat.getFormat("dd.MM.yyyy HH:mm")
						.format(beitrag.getBeitrag().getErstellungszeitpunkt()));

				final Grid eins = new Grid(5, 4);
				testlabel.setStylePrimaryName("beitrag-text");
				testlabel.setText(beitrag.getBeitrag().getText());
				
				eins.setWidget(0, 0, datum);
				eins.setWidget(1, 0, testlabel);
				eins.setWidget(0, 1, edit);
				eins.setWidget(2, 0, likebtn);
				eins.setWidget(2, 1, likecount);
				eins.setWidget(3, 0, kommtextarea);
				eins.setWidget(3, 1, neuKommBtn);
				
				eins.setStylePrimaryName("eins");

				socialMedia.findKommentareByBeitrag(beitragId, new AsyncCallback<Vector<Kommentar>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Vector<Kommentar> result) {
						// TODO Auto-generated method stub

						beitragPanel.add(eins);
						beitragPanel.setStylePrimaryName("beitrag");
						FlexTable ft = new FlexTable();
						DisclosurePanel dpanel = null;
						if (result.size() == 0) {
							beitragPanel.add(new Label("Keine Kommentare"));
						} else {

							for (Kommentar kommentar : result) {
								dpanel = new DisclosurePanel("Kommentare: ");
								ft.setWidget(ft.getRowCount(), 0, new KommentarForm(kommentar, nutzer, pinn));

							}
							dpanel.setContent(ft);
							beitragPanel.add(dpanel);
						}
					}
				});

				edit.addClickHandler(new EditClickhandler(beitragId));

				likecount.addMouseOverHandler(new LikeMouseOverHandler(beitragId));

				neuKommBtn.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						if (kommtextarea.getText().equals("")) {
							Window.alert("Leeres Textfeld! Bitte Text eingeben!");
						} else {
							socialMedia.createKommentar(nutzer, beitragId, kommtextarea.getText(),
									new CreateKommCallBack());
						}

					}
				});

				flexTable.setWidget(numRows, 0, beitragPanel);
			}
		}

	}

	public class CreateKommCallBack implements AsyncCallback<Kommentar> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}

		@Override
		public void onSuccess(Kommentar result) {

			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(new MeinePinnwandForm(result.getErsteller_Id()));
			// Window.alert("Kommentar angelegt");
		}

	}

	public class FindPinnwandCallBack implements AsyncCallback<Pinnwand> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}

		@Override
		public void onSuccess(Pinnwand result) {
			neuBtn.setStylePrimaryName("neu-btn");
			neuBtn.addClickHandler(new CreateClickhandler(nutzer, result.getId()));
		}

	}

	public class CreatBeitragCallBack implements AsyncCallback<Beitrag> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}

		@Override
		public void onSuccess(Beitrag result) {
			// TODO Auto-generated method stub
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(new MeinePinnwandForm(nutzer));

		}

	}

	public class NutzerAsyncCallback implements AsyncCallback<Nutzer> {

		@Override
		public void onSuccess(Nutzer result) {
			// TODO Auto-generated method stub
			user.setText("Eigene Pinnwand von: " + result.getNickname());

		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}

	}

	public class KommNutzerAsyncCallback implements AsyncCallback<Nutzer> {

		@Override
		public void onSuccess(Nutzer result) {
			// TODO Auto-generated method stub
			kommNickname.setText(result.getNickname());
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}

	}

	public class EditClickhandler implements ClickHandler {

		int id;

		public EditClickhandler(int beitragId) {
			id = beitragId;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

			new DialogBoxEditBeitrag(id);
		}

	}

	public class LikeMouseOverHandler implements MouseOverHandler {
		int bId;

		public LikeMouseOverHandler(int bId) {
			// TODO Auto-generated constructor stub
			this.bId = bId;
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			socialMedia.findNutzerByLikes(bId, new FindNutzerByLikesAsyncCallback());

			Widget source = (Widget) event.getSource();
			int left = source.getAbsoluteLeft() + 10;
			int top = source.getAbsoluteTop() + 10;
			simplePopup.setPopupPosition(left, top);
			simplePopup.hide();
		}

	}

	public class FindNutzerByLikesAsyncCallback implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			// TODO Auto-generated method stub
			anzahlLike = result.size();
			HTML html = new HTML();
			if (result.size() != 0) {
				for (Nutzer nutzer : result) {
					html.setHTML(result.toString().replace("[", "").replace("]", "").replace(",", ""));
				}
				simplePopup.setWidget(html);
				simplePopup.show();
			}

		}

	}

	public class CreateClickhandler implements ClickHandler {

		int nId;
		int unique;

		public CreateClickhandler(int nId, int unique) {
			this.nId = nId;
			this.unique = unique;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

			String eingabe = text.getValue();
			if (text.getText().equals("")) {
				Window.alert("Leeres Textfeld! Bitte Text eingeben");
			} else {
				socialMedia.createBeitrag(nId, unique, eingabe, new CreatBeitragCallBack());
			}

		}

	}

	public class LikeClickhandler implements ClickHandler {

		int nId;
		int bId;

		public LikeClickhandler(int nId, int bId) {
			this.nId = nId;
			this.bId = bId;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			socialMedia.createLike(nId, bId, new CreateLikeAsyncCallback(nId));
		}

	}

	public class CreateLikeAsyncCallback implements AsyncCallback<Like> {

		int nId;

		public CreateLikeAsyncCallback(int nId) {
			// TODO Auto-generated constructor stub
			this.nId = nId;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}

		@Override
		public void onSuccess(Like result) {
			// TODO Auto-generated method stub
			// likebtn.setText("Unlike");
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(new MeinePinnwandForm(nId));
		}

	}

	public class CheckLikeAsyncCallback implements AsyncCallback<Boolean> {

		int nId;
		int bId;

		public CheckLikeAsyncCallback(int nId, int bId) {
			this.nId = nId;
			this.bId = bId;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}

		@Override
		public void onSuccess(Boolean result) {
			Window.alert("PAUSE");
			if (result == true) {
				likebtn.setText("Unlike");
				likebtn.addClickHandler(new LikebtnClickhandler(nId, bId));
			} else {
				likebtn.setText("Like");
				likebtn.addClickHandler(new LikeClickhandler(nId, bId));
			}
		}

	}

	public class LikebtnClickhandler implements ClickHandler {

		int nId;
		int bId;

		public LikebtnClickhandler(int nId, int bId) {
			this.nId = nId;
			this.bId = bId;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

			socialMedia.unlike(nId, bId, new UnlikeAsyncCallback(nId));
		}

	}

	public class UnlikeAsyncCallback implements AsyncCallback<Void> {

		int nId;
		

		public UnlikeAsyncCallback(int nId) {
			this.nId = nId;
			
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			// likebtn.setText("Like");
			RootPanel.get("Details").clear();
			RootPanel.get("Details").add(new MeinePinnwandForm(nId));
		}

	}

	public class FindNutzerByLikeAsyncCallback implements AsyncCallback<Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			// TODO Auto-generated method stub
			if (result.size() > 1) {
				likecount.setText(result.size() + " Likes");
			} else if (result.size() == 1) {
				likecount.setText(result.size() + " Like");
			}

		}

	}

}
