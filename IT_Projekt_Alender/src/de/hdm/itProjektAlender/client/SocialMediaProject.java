package de.hdm.itProjektAlender.client;

import java.util.Vector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
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
	private Button logout = new Button("Logout");
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
					socialMediaAdmin.checkNutzer(loginInfo.getEmailAddress(),new AsyncCallback <Nutzer>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Login fehlgeschlagen!");
						}

						@Override
						public void onSuccess(Nutzer result) {
							
							
							if(result != null){	
								
								 Cookies.setCookie("id", ""+result.getId());
								 Cookies.setCookie("email", result.getEmail());
							     loadSocialMediaAdmin(result.getId());
							      
								}
							
							else{
								
								Window.alert("Noch nicht registriert");
								
								RootPanel.get("Details").clear();
								
								RootPanel.get("Details").add(new RegistrierenForm());
								
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
	    RootPanel.get("Header").add(logout);
		RootPanel.get("Details").clear();
	    RootPanel.get("Navigator").add(new NavigationForm());
		RootPanel.get("Details").add(new StartseiteForm());
		
		
	    
	    //TopPanel für Logut
//	    VerticalPanel topPanel = new VerticalPanel();
//	    RootPanel.get("Header").add(topPanel);
	    //Erstellen Projektmarktzplatz Button
	    
	    logout.setWidth("150px");
	    logout.setStylePrimaryName("login-btn");

	    
	   
	    logout.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.open(signOutLink.getHref(), "_self",
						"");
			}
		});
	    
	    
	}
	
	public class RegistrierenForm extends VerticalPanel {
		
		SocialMediaAdminAsync socialmedia = ClientsideSettings.getSocialMediaAdmin();
		Nutzer n = null;
		VerticalPanel homePanel = new VerticalPanel();
		Label name = new Label("Name: ");
		Label nickname = new Label("Nickname: ");
		Label nachname = new Label("Nachname: ");
		Label email = new Label("E-Mail: ");
		TextBox tname = new TextBox();
		TextBox tnachname = new TextBox();
		TextBox tnickname = new TextBox();
		TextBox temail = new TextBox();
		
		//private FlexTable ftForm = new FlexTable();
		
		Button btnAnmelden = new Button("Registrieren");
		
		public void onLoad(){
			super.onLoad();
			
			Grid registerGrid = new Grid(5,2);
			homePanel.add(registerGrid);
			
			registerGrid.setWidget(0,0,name);
			registerGrid.setWidget(0, 1, tname);
			registerGrid.setWidget(1,0,nachname);
			registerGrid.setWidget(1, 1, tnachname);
			registerGrid.setWidget(2,0,nickname);
			registerGrid.setWidget(2, 1, tnickname);
			registerGrid.setWidget(3,0,email);
			registerGrid.setWidget(3, 1, temail);
			registerGrid.setWidget(4,0,btnAnmelden);
			btnAnmelden.addClickHandler(new AnmeldenClickhandler());
			this.add(homePanel);
		}
		
		private class AnmeldenClickhandler implements ClickHandler{

			@Override
			public void onClick(ClickEvent event) {
				String vorname = tname.getText();
				String nachname = tnachname.getText();
				String nickname = tnickname.getText();
				String email = temail.getText();
				
				socialmedia.createNutzer(vorname, nachname, nickname, email, new CreateNutzerCallback());
				
			}
			
		}
		
		class CreateNutzerCallback implements AsyncCallback<Nutzer> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Das Registrieren ist Fehlgeschlagen! " + caught.getMessage());
			}

			@Override
			public void onSuccess(Nutzer nutzer) {
				Cookies.setCookie("id", ""+nutzer.getId());
				Cookies.setCookie("email", nutzer.getEmail());
				
				socialmedia.createPinnwand(nutzer.getId(), new CreatePinnwandCallback());
				
					// Das erfolgreiche Hinzufügen eines Kunden wird an den Kunden- und
					// Kontenbaum propagiert.
				Window.alert("Glückwunsch " + tnickname.getText()+" ! Sie sind jetzt Mitglied bei Gesichtsbuch!");
				loadSocialMediaAdmin(nutzer.getId());
				
				
			}
		
			class CreatePinnwandCallback implements AsyncCallback<Pinnwand> {
				
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Das Registrieren ist Fehlgeschlagen!" +  caught.getMessage());
					
					
					
				}

				@Override
				public void onSuccess(Pinnwand result) {
					// TODO Auto-generated method stub					
				}
				
				
			}

			
		}
		
		
		
		


	}
    
		
}


