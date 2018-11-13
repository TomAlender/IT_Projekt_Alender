package de.hdm.itProjektAlender.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Nutzer;

public class FremdePinnwand extends VerticalPanel {
	
	Label test = new Label();
	String nickname = null;
	HorizontalPanel eins = new HorizontalPanel();
	SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();
	Button aboLoeschen = new Button("Deabonnieren");
	public FremdePinnwand(int nutzerId) {
		// TODO Auto-generated constructor stub
		socialMedia.findNutzerById(nutzerId, new AsyncCallback<Nutzer>() {
			
			@Override
			public void onSuccess(Nutzer result) {
				// TODO Auto-generated method stub
			    test.setText("Pinnwand von: "+ result.getNickname());	
			    nickname = result.getNickname();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Fehler");
			}
		});
		
		aboLoeschen.addClickHandler(new DeabonnierenCLickhandler());
		eins.add(test);
		eins.add(aboLoeschen);
		this.add(eins);
	}
	
	public class DeabonnierenCLickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			socialMedia.deleteAbonnement(Integer.parseInt(Cookies.getCookie("id")), nickname, new DeaboniereAsyncCallback());
			
		}
	}
	
	public class DeaboniereAsyncCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Window.alert("User wurde deabonniert!");
			RootPanel.get("Details").clear();
			RootPanel.get("Navigator").clear();
			RootPanel.get("Navigator").add(new NavigationForm());
			
		}
		
	}
}
