package de.hdm.itProjektAlender.client;

import java.util.Vector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itProjektAlender.shared.LoginServiceAsync;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;

import de.hdm.itProjektAlender.shared.bo.*;
import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.client.gui.*;
import de.hdm.itProjektAlender.client.LoginInfo;

public class SocialMediaProject implements EntryPoint {
	
	private SocialMediaAdminAsync socialMediaAdmin;
	private LoginServiceAsync loginservice;
	private LoginInfo loginInfo = null;
	final Button Logout = new Button("Logout");
	private Button loginButton = new Button("Login");
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel1 = new Label("Herzlich Willkommen auf Gesichtsbuch");
	private Label loginLabel = new Label("Bitte melde dich mit deinem Google Account Email an, um Zugang zu erhalten.");
	private Anchor signInLink= new Anchor("Login");
	private Anchor signOutLink = new Anchor("Logout");

	@Override
	public void onModuleLoad() {
		
		socialMediaAdmin = ClientsideSettings.getSocialMediaAdmin();
		loginservice = ClientsideSettings.getLoginService();
		
		loginservice.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {


			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler: " + caught.toString());
				
			}

			@Override
			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if(loginInfo.isLoggedIn()){
					
					/**
					 * @return Vector mit allen Personen
					 */
					socialMediaAdmin.getAllNutzer(new AsyncCallback<Vector<Nutzer>>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Login fehlgeschlagen!");
						}

						@Override
						public void onSuccess(Vector<Nutzer> result) {
							boolean isUserRegistered = false;
							for (Nutzer nutzer : result) {
								/**
								 * �berpr�fung ob User bereits registriert ist
								 */
								if(nutzer.getEmail()==loginInfo.getEmailAddress()){
									isUserRegistered = true;
									/**
									 * Falls User registriert ist wird der Projektmarktplatz geladen
									 * @param id des jeweiligen Person-Objekts
									 */
							       loadSocialMediaAdmin(nutzer.getId());
							       
									break;
								}
							}
							if(isUserRegistered==false){
								/*
								RootPanel.get("Details").clear();
								
								RootPanel.get("Details").add(new RegistrierenForm());*/	
								
							}
						}
					});
					
				} else{
					loadLogin();
				}
				
			}
		});	
	
	}
	
	public void loadLogin(){
		
		loginLabel.setStylePrimaryName("startseite_label");
		loginLabel1.setStylePrimaryName("willkommen_label");
		loginPanel.setSpacing(10);
		loginPanel.add(loginLabel1);
		loginPanel.add(loginLabel);
		signInLink.setHref(loginInfo.getLoginUrl());

		RootPanel.get("Details").add(loginPanel);
		RootPanel.get("Navigator").add(loginButton);
		
		loginButton.setWidth("150px");
		loginButton.setStylePrimaryName("login-btn");
		loginButton.addClickHandler(new ClickHandler() {
			
			
			
			@Override
			public void onClick(ClickEvent event) {
				Window.open(signInLink.getHref(), "_self",
						"");	
			}
		});
	}
	
	private void loadSocialMediaAdmin(int nutzerId){
		//Erstellen des Logout-Links
		signOutLink.setHref(loginInfo.getLogoutUrl());

		
		//Navigation navigation = new Navigation();
		//IdentityMarketChoice identityMarketChoice = new IdentityMarketChoice(personId, navigation);
		//navigation.setIdentityMarketChoice(identityMarketChoice);
		//RootPanel.get("Header").add(navigation.getIdentityMarketChoice());
		//Integer test = IdentityMarketChoice.getNavigation(3).getSelectedIdentityId();
	    RootPanel.get("Header").add(Logout);
		RootPanel.get("Details").clear();
	    //RootPanel.get("Navigator").add(navigation);
		RootPanel.get("Details").add(new StartseiteForm());
		
		
	    
	    //TopPanel f�r Logut
//	    VerticalPanel topPanel = new VerticalPanel();
//	    RootPanel.get("Header").add(topPanel);
	    //Erstellen Projektmarktzplatz Button
	    
	    Logout.setWidth("150px");
	    Logout.setStylePrimaryName("login-btn");

	    
	   
	    Logout.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.open(signOutLink.getHref(), "_self",
						"");
			}
		});
	    
	    
	}
    
		
}


