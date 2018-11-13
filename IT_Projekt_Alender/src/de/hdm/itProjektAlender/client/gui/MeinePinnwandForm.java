package de.hdm.itProjektAlender.client.gui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Nutzer;

public class MeinePinnwandForm extends VerticalPanel {
	
	Label test = new Label();
	SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();			
			MeinePinnwandForm(int nutzerId){
				
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
				this.add(test);
				
			}
	

}
