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
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Beitrag;
import de.hdm.itProjektAlender.shared.bo.Like;
import de.hdm.itProjektAlender.shared.bo.Nutzer;
import de.hdm.itProjektAlender.shared.bo.Pinnwand;

public class MeinePinnwandForm extends VerticalPanel {

	Label user = new Label();
	TextArea text = new TextArea();

	int pinnwandId = 0;
	int likeanzahl = 4;
	int kommentaranzahl = 2;
	Button neuBtn = new Button("Neuen Beitrag erstellen");
	VerticalPanel head = new VerticalPanel();
	DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);

	final FlexTable flexTable = new FlexTable();
	SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();

	VerticalPanel beitrag = new VerticalPanel();
	// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:s");
	// DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("YYYY/MM/DD");

	MeinePinnwandForm(final int nutzerId) {

		text.setVisibleLines(2);
		socialMedia.findNutzerById(nutzerId, new AsyncCallback<Nutzer>() {

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
		});

		socialMedia.findPinnwandByNutzer(nutzerId, new AsyncCallback<Pinnwand>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Fehler beim Laden " + caught.getMessage());
			}

			@Override
			public void onSuccess(Pinnwand result) {
				// TODO Auto-generated method stub
				pinnwandId = result.getId();
				socialMedia.findBeitraegeByPinnwand(result.getId(), new AsyncCallback<Vector<Beitrag>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("Fehler beim Laden " + caught.getMessage());
					}

					@Override
					public void onSuccess(Vector<Beitrag> result) {
						// TODO Auto-generated method stub

						for (Beitrag beitrag : result) {
							int numRows = flexTable.getRowCount();
							// beitragText.setText(beitrag.getText());
							Label testlabel = new Label();
							Label datum = new Label();
							final Anchor likebtn = new Anchor();
							Anchor kommbtn = new Anchor("Kommentare");
							Anchor edit = new Anchor("Edit");
							testlabel.setText(beitrag.getText());
							final int beitragId = beitrag.getId();

							datum.setText(DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss")
									.format(beitrag.getErstellungszeitpunkt()));

							Grid eins = new Grid(2, 2);

							eins.setWidget(0, 0, datum);
							eins.setWidget(1, 0, testlabel);
							eins.setWidget(0, 1, edit);
							HorizontalPanel btnpanel = new HorizontalPanel();
							VerticalPanel beitragPanel = new VerticalPanel();
							beitragPanel.add(eins);
							btnpanel.add(likebtn);
							btnpanel.add(kommbtn);
							beitragPanel.add(btnpanel);

							socialMedia.checkLike(nutzerId, beitragId, new AsyncCallback<Boolean>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onSuccess(Boolean result) {
									if (result == true) {
										likebtn.setText("Unlike");
										likebtn.addClickHandler(new ClickHandler() {
											
											@Override
											public void onClick(ClickEvent event) {
												// TODO Auto-generated method stub
												socialMedia.unlike(nutzerId, beitragId, new AsyncCallback<Void>() {
												
													@Override
													public void onFailure(Throwable caught) {
														// TODO Auto-generated method stub
														Window.alert("Fehler beim Laden " + caught.getMessage());
													}


													@Override
													public void onSuccess(Void result) {
														// TODO Auto-generated method stub
														likebtn.setText("Like");
													}
												});
											}
										});
										
									} else {
										likebtn.setText("Like");
										
										likebtn.addClickHandler(new ClickHandler() {
											
											@Override
											public void onClick(ClickEvent event) {
												// TODO Auto-generated method stub
												socialMedia.createLike(nutzerId, beitragId, new AsyncCallback<Like>() {

													@Override
													public void onFailure(Throwable caught) {
														// TODO Auto-generated method stub
														Window.alert("Fehler beim Laden " + caught.getMessage());
													}

													@Override
													public void onSuccess(Like result) {
														// TODO Auto-generated method stub
														likebtn.setText("Unlike");
													}
												});
											}
										});
									}

								}
							});

							edit.addClickHandler(new ClickHandler() {

								@Override
								public void onClick(ClickEvent event) {
									// TODO Auto-generated method stub

									new DialogBoxEditBeitrag(beitragId);
								}

							});

							likebtn.addMouseOverHandler(new MouseOverHandler() {

								@Override
								public void onMouseOver(MouseOverEvent event) {
									// TODO Auto-generated method stub
									final FlexTable likeTbl = new FlexTable();
									final Label likeNutzer = new Label();
									

									socialMedia.findNutzerByLikes(beitragId, new AsyncCallback<Vector<Nutzer>>() {

										@Override
										public void onSuccess(Vector<Nutzer> result) {
											if (result.size() != 0) {
												for (Nutzer nutzer : result) {
													int rows = likeTbl.getRowCount();
													
													Label label = new Label();
													label.setText(nutzer.getNickname());

													likeTbl.setWidget(rows, 0, label);

												}
												simplePopup.setWidget(likeTbl);
												simplePopup.show();
											}

										}

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub

										}
									});

									Widget source = (Widget) event.getSource();
									int left = source.getAbsoluteLeft() + 10;
									int top = source.getAbsoluteTop() + 10;
									simplePopup.setPopupPosition(left, top);
									simplePopup.hide();

								}
							});

							flexTable.setWidget(numRows, 0, beitragPanel);
						}

					}

				});
			}
		});

		neuBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				String eingabe = text.getValue();
				socialMedia.createBeitrag(nutzerId, eingabe, pinnwandId, new CreatBeitragCallBack());

			}
		});

		head.add(user);
		head.add(text);
		head.add(neuBtn);
		this.add(head);
		this.add(flexTable);
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
			RootPanel.get("Details").add(new MeinePinnwandForm(result.getErsteller_Id()));
			Window.alert("Beitrag wurde auf Ihrer Pinnwand angelegt!");
		}

	}

}
