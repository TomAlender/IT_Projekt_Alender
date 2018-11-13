package de.hdm.itProjektAlender.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.*;

public class DialogBoxAbonnieren extends DialogBox {
	
	MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	FlexTable ftAbonnieren = new FlexTable();
	Nutzer n = new Nutzer();
	
	SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();
	
	Button btnAbonnieren = new Button("Abonnieren");
	Button btnAbbrechen = new Button("Abbrechen");
	
	//Label lbNickname = new Label("Nickname: ");
	//TextBox txtNickname = new TextBox();
	
	SuggestBox abo = new SuggestBox(oracle);
	
	public DialogBoxAbonnieren(){		
		  
		VerticalPanel dbAbo = new VerticalPanel();
		this.setText("User abbonieren");
		this.setAnimationEnabled(false);
		this.setGlassEnabled(true);
		this.center();
		//HorizontalPanel btnPanel = new HorizontalPanel();
		socialMedia.getAllOtherNutzer(Integer.parseInt(Cookies.getCookie("id")), new NutzerAsyncCallback());
		
		//dbAbo.add(ftAbonnieren);
		dbAbo.add(abo);
		dbAbo.add(btnAbonnieren);
		dbAbo.add(btnAbbrechen);
		
		
		
		btnAbonnieren.addClickHandler(new AbonnierenClickhandler());
		
		btnAbbrechen.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				hide();
				
			}
		});
		
		this.add(dbAbo);
		//this.add(btnPanel);
		
		
		
	}
	
	
	public class AbonnierenClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {			
			
			socialMedia.checkAbonnement(Integer.parseInt(Cookies.getCookie("id")),abo.getValue(), new CheckAsyncCallback());
			//socialMedia.createAbonnement(Integer.parseInt(Cookies.getCookie("id")),abo.getValue(), new AbonnementAsyncCallback());
			
			
			
		}
		
	}
	class CheckAsyncCallback implements AsyncCallback <Boolean> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		public void onSuccess(Boolean result) {
			
		if (result == true){
			socialMedia.createAbonnement(Integer.parseInt(Cookies.getCookie("id")),abo.getValue(), new AbonnementAsyncCallback());
		}
		else{
			Window.alert("User bereits Abonniert! Bitte wählen Sie einen anderen Nutzer!");
		}
			
		}
		
	}
	
	class NutzerAsyncCallback implements AsyncCallback <Vector<Nutzer>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			for (Nutzer nutzer : result) {
				oracle.add(nutzer.getNickname());
			}
		
			
		}
		
	}
	
	class AbonnementAsyncCallback implements AsyncCallback <Abonnement>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
	
		}

		@Override
		public void onSuccess(Abonnement result) {
			// TODO Auto-generated method stub			
			Window.alert("Abonnement angelegt");
			RootPanel.get("Navigator").clear();
			RootPanel.get("Navigator").add(new NavigationForm());
			hide();
			
		}
		
	}

	

}
