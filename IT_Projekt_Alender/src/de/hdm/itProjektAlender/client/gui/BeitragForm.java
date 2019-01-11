package de.hdm.itProjektAlender.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RenderablePanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.client.LikeBeitragWrapper;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Kommentar;
import de.hdm.itProjektAlender.shared.bo.Like;
import de.hdm.itProjektAlender.shared.bo.Nutzer;


public class BeitragForm extends VerticalPanel{
	
	private TextArea tArea = new TextArea();
	private Button bt1 = new Button();


	HorizontalPanel btnpanel = new HorizontalPanel();
	VerticalPanel beitragPanel = new VerticalPanel();
	DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
	FlexTable ft = new FlexTable();
	Label testlabel = new Label();
	Label datum = new Label();
	FlexTable flex = new FlexTable();
	
	HTML testHtml = new HTML();
	
	FlexTable flexTest = new FlexTable();
	
	Button edit = new Button("Edit");
	
	Button likebtn = new Button();
	Label likecount = new Label();
	boolean pinn = false;
	

	Vector<Kommentar> kommentarVector = new Vector<Kommentar>();
	final TextArea kommtextarea = new TextArea();

	Button neuKommBtn = new Button("Neuer Kommentar");
	SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();
	
	public FlexTable getFt() {
		return ft;
	}

	public void setFt(FlexTable ft) {
		this.ft = ft;
	}

	public VerticalPanel getBeitragPanel() {
		return beitragPanel;
	}

	public void setBeitragPanel(VerticalPanel beitragPanel) {
		this.beitragPanel = beitragPanel;
	}
	public BeitragForm(final int nutzer, LikeBeitragWrapper beitrag) {
		
		final int rowCount = flex.getRowCount();
		final int beitragId = beitrag.getBeitrag().getId();
		// TODO Auto-generated constructor stub
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

		
		testlabel.setStylePrimaryName("beitrag-text");
		testlabel.setText(beitrag.getBeitrag().getText());
		
			
		flex.setWidget(rowCount, 0, datum);
		flex.setWidget(rowCount, 1, edit);		
		flex.setWidget(rowCount+1, 0, testlabel);
		flex.setWidget(rowCount+2, 0, likebtn);
		flex.setWidget(rowCount+2, 1, likecount);
		flex.setWidget(rowCount+3, 0, kommtextarea);
		flex.setWidget(rowCount+3, 1, neuKommBtn);
	
		socialMedia.findKommentareByBeitrag(beitrag.getBeitrag().getId(), new AsyncCallback<Vector<Kommentar>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Vector<Kommentar> result) {
				// TODO Auto-generated method stub
				beitragPanel.add(flex);
				beitragPanel.setStylePrimaryName("beitrag");
				
				DisclosurePanel dpanel = null;
				if (result.size() == 0) {
					beitragPanel.add(new Label("Keine Kommentare"));
				} else {

					for (Kommentar kommentar : result) {
					Window.alert(kommentar.getText());
//						dpanel = new DisclosurePanel("Kommentare: ");
//						ft.setWidget(ft.getRowCount(), 0, new KommentarForm(kommentar, nutzer, pinn));
					}
					dpanel.setContent(ft);
					beitragPanel.add(dpanel);
					
				}
			}
		});
		edit.addClickHandler(new EditClickhandler(beitrag.getBeitrag().getId()));

		likecount.addMouseOverHandler(new LikeMouseOverHandler(beitrag.getBeitrag().getId()));

		neuKommBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.alert("hallo");
				if (kommtextarea.getText().equals("")) {
					Window.alert("Leeres Textfeld! Bitte Text eingeben!");
				} else {
					
					socialMedia.createKommentar(nutzer, beitragId, kommtextarea.getText(),
							new CreateKommCallBack());
				}

			}
			});
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
