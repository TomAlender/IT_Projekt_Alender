package de.hdm.itProjektAlender.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Beitrag;
import de.hdm.itProjektAlender.shared.bo.Kommentar;

public class DialogBoxEditKommentar extends DialogBox{
	
	SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();
	Kommentar k = new Kommentar();
	
	VerticalPanel eins = new VerticalPanel();
	

	
	FlexTable ftAnordnung = new FlexTable();
	TextBox oldText = new TextBox();
	Button btnAendern = new Button("Ändern");
	Button btnLoeschen = new Button("Löschen");
	Button btnAbbrechen = new Button("Abbrechen");
	HorizontalPanel btnPanel = new HorizontalPanel();
	boolean pinn;
	int nutzerId;
		
	public DialogBoxEditKommentar(final int kommentarId, boolean pinn) {
		// TODO Auto-generated constructor stub
		
		this.pinn = pinn;
		
		this.setText("Kommentar editieren");
		this.setAnimationEnabled(false);
		this.setGlassEnabled(true);
		this.center();		
		btnPanel.add(btnAendern);
		btnPanel.add(btnLoeschen);
		btnPanel.add(btnAbbrechen);
		btnAendern.addClickHandler(new AendernClickHandler());
		btnLoeschen.addClickHandler(new LoeschenClickHandler());
		eins.add(oldText);
		eins.add(btnPanel);
		this.add(eins);
	
		socialMedia.findKommentarById(kommentarId, new KommentarCallBack());
		
		btnAbbrechen.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				hide();
				
			}
		});
		
		
		
		
		
		//this.add(btnPanel);
		
	}
	public class BeitragCallback implements AsyncCallback<Beitrag>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}

		@Override
		public void onSuccess(Beitrag result) {
			// TODO Auto-generated method stub
			nutzerId = result.getErsteller_Id();
		}
		
	}
	public class KommentarCallBack implements AsyncCallback <Kommentar>{


	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		Window.alert("Fehler beim Laden " + caught.getMessage());
	}

	@Override
	public void onSuccess(Kommentar result) {
		// TODO Auto-generated method stub
		k=result;		
		oldText.setText(k.getText());
		socialMedia.findBeitragById(k.getBeitrag_Id(), new BeitragCallback());
	}
	
}
	public class AendernClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			k.setText(oldText.getText());
			socialMedia.editKommentar(k, new AsyncCallback<Void>(){

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					Window.alert("Fehler beim Laden " + caught.getMessage());
				}

				@Override
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub
					Window.alert("Kommentar wurde geändert");
					
					RootPanel.get("Details").clear();
					if (pinn == false ){
					RootPanel.get("Details").add(new FremdePinnwand(nutzerId));
					hide();
					}
					if(pinn == true){
						RootPanel.get("Details").add(new MeinePinnwandForm(k.getErsteller_Id()));
						hide();	
					}
					
				}
				
			});
		}	
		
	}
	
	public class LoeschenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			socialMedia.deleteKommentar(k, new AsyncCallback<Void>(){

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					Window.alert("Fehler beim Laden " + caught.getMessage());
				}

				@Override
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub
					Window.alert("Beitrag wurde gelöscht");
					RootPanel.get("Details").clear();
					if (pinn == false ){
						RootPanel.get("Details").add(new FremdePinnwand(nutzerId));
						hide();
						}
						if(pinn == true){
							RootPanel.get("Details").add(new MeinePinnwandForm(k.getErsteller_Id()));
							hide();	
						}
						
					
				}
				
			});
		}
		
	}
	
}
			
		
		
		
