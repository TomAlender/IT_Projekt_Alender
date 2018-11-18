package de.hdm.itProjektAlender.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Beitrag;

public class DialogBoxEditBeitrag extends DialogBox{
	
	SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();
	Beitrag b = new Beitrag();
	VerticalPanel eins = new VerticalPanel();
	TextBox oldText = new TextBox();
	Button btnAendern = new Button("�ndern");
	Button btnLoeschen = new Button("L�schen");
	Button btnAbbrechen = new Button("Abbrechen");
	HorizontalPanel btnPanel = new HorizontalPanel();
		
	public DialogBoxEditBeitrag(final int beitragId) {
		// TODO Auto-generated constructor stub
		this.setText("Beitrag editieren");
		this.setAnimationEnabled(false);
		this.setGlassEnabled(true);
		this.center();		
		
		
		
		socialMedia.findBeitragById(beitragId, new BeitragCallBack());
		
		btnAbbrechen.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				hide();
				
			}
		});
		
		btnAendern.addClickHandler(new AendernClickHandler());
		btnLoeschen.addClickHandler(new LoeschenClickHandler());
		
		eins.add(oldText);
		eins.add(btnAendern);
		eins.add(btnLoeschen);
		eins.add(btnAbbrechen);
		
		
		this.add(eins);
		//this.add(btnPanel);
		
	}	
	public class BeitragCallBack implements AsyncCallback <Beitrag>{


	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		Window.alert("Fehler beim Laden " + caught.getMessage());
	}

	@Override
	public void onSuccess(Beitrag result) {
		// TODO Auto-generated method stub
		b=result;		
		oldText.setText(b.getText());
		
	}
	
}
	public class AendernClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			b.setText(oldText.getText());
			socialMedia.editBeitrag(b, new AsyncCallback<Void>(){

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					Window.alert("Fehler beim Laden " + caught.getMessage());
				}

				@Override
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub
					Window.alert("Beitrag wurde geändert");
					RootPanel.get("Details").clear();
					RootPanel.get("Details").add(new MeinePinnwandForm(b.getErsteller_Id()));
					hide();
					
				}
				
			});
		}	
		
	}
	
	public class LoeschenClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			socialMedia.deleteBeitrag(b, new AsyncCallback<Void>(){

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
					RootPanel.get("Details").add(new MeinePinnwandForm(b.getErsteller_Id()));
					hide();
					
				}
				
			});
		}
		
	}
	
}
			
		
		
		
