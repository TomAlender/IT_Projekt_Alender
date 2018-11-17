package de.hdm.itProjektAlender.client.gui;

import java.text.SimpleDateFormat;
import java.util.Vector;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Beitrag;
import de.hdm.itProjektAlender.shared.bo.Nutzer;
import de.hdm.itProjektAlender.shared.bo.Pinnwand;

public class MeinePinnwandForm extends VerticalPanel {
	
	Label user = new Label();
	TextArea text = new TextArea();
	
	int pinnwandId = 0;
	Button neuBtn	= new Button("Neuen Beitrag erstellen");
	VerticalPanel head = new VerticalPanel();
	
	final FlexTable flexTable = new FlexTable();
	SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();
	
	VerticalPanel beitrag = new VerticalPanel();
	//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:s");
	//DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("YYYY/MM/DD");
	
	MeinePinnwandForm(final int nutzerId){
				
		text.setVisibleLines(2);				
		socialMedia.findNutzerById(nutzerId, new AsyncCallback<Nutzer>() {
						
					@Override
					public void onSuccess(Nutzer result) {
						// TODO Auto-generated method stub
					    user.setText("Eigene Pinnwand von: "+ result.getNickname());					    
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
//									beitragText.setText(beitrag.getText());
									Label testlabel = new Label();
									Label datum = new Label();
									Button likebtn = new Button("Like");
									Button kommbtn = new Button("Kommentare");
									Button edit = new Button("Edit");
									testlabel.setText(beitrag.getText());	
									final int test = beitrag.getId();
									
									datum.setText(DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(beitrag.getErstellungszeitpunkt()));
									
									Grid eins = new Grid(2,2);
									
									eins.setWidget(0, 0, datum);
									eins.setWidget(1, 0, testlabel);
									eins.setWidget(0, 1, edit);
									HorizontalPanel btnpanel = new HorizontalPanel();
									VerticalPanel beitragPanel = new VerticalPanel();
									beitragPanel.add(eins);
									btnpanel.add(likebtn);
									btnpanel.add(kommbtn);
									beitragPanel.add(btnpanel);
									
									
									edit.addClickHandler(new ClickHandler(){

										@Override
										public void onClick(ClickEvent event) {
											// TODO Auto-generated method stub
											
											 new DialogBoxEditBeitrag(test);
										}
										
									});
																		
										
									flexTable.setWidget(numRows, 0, beitragPanel);
								}
								
							}
								
							
						});
					}					
				});	
				
				
				neuBtn.addClickHandler(new ClickHandler(){

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
			
				
			
			 public class CreatBeitragCallBack implements AsyncCallback <Beitrag>{

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
