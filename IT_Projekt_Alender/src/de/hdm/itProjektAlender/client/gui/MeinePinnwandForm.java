package de.hdm.itProjektAlender.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Beitrag;
import de.hdm.itProjektAlender.shared.bo.Nutzer;
import de.hdm.itProjektAlender.shared.bo.Pinnwand;

public class MeinePinnwandForm extends VerticalPanel {
	
	Label test = new Label();
	TextArea text = new TextArea();
	
	int pinnwandId = 0;
	Button neuBtn	= new Button("Neuen Beitrag erstellen");
	VerticalPanel head = new VerticalPanel();
	
	final FlexTable flexTable = new FlexTable();
	SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();
	
	VerticalPanel beitrag = new VerticalPanel();
	Label beitragText = new Label();
	Label datum = new Label();
	
	MeinePinnwandForm(final int nutzerId){
				
		text.setVisibleLines(2);				
		socialMedia.findNutzerById(nutzerId, new AsyncCallback<Nutzer>() {
						
					@Override
					public void onSuccess(Nutzer result) {
						// TODO Auto-generated method stub
					    test.setText("Eigene Pinnwand von: "+ result.getNickname());				
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("Fehler");
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
						Window.alert(""+result.getId());
						socialMedia.findBeitraegeByPinnwand(result.getId(), new AsyncCallback<Vector<Beitrag>>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								Window.alert("Fehler beim Laden " + caught.getMessage());
							}

							@Override
							public void onSuccess(Vector<Beitrag> result) {
								// TODO Auto-generated method stub
								Window.alert("anzahl der beiträge: "+result.size());
							
							
								for (Beitrag beitrag : result) {
									int numRows = flexTable.getRowCount();
//									beitragText.setText(beitrag.getText());
									flexTable.setWidget(numRows, 0, new Label(beitrag.getText()));
										
									
									
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
				
				
				
				head.add(test);
				head.add(neuBtn);
				this.add(text);
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
					Window.alert("Beitrag wurde auf Ihrer Pinnwand angelegt!");					
				}
				
			}
	

}
