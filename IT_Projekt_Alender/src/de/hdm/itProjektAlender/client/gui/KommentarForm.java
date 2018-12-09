package de.hdm.itProjektAlender.client.gui;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Kommentar;
import de.hdm.itProjektAlender.shared.bo.Nutzer;

public class KommentarForm extends FlexTable{
	
	private Label text = new Label();
	private Label datum = new Label();
	private Label nickname = new Label();
	
	
	private SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();
	
	public KommentarForm(final Kommentar kommentar){
		
		final int rowCount = this.getRowCount();
		
		
		socialMedia.findNutzerById(kommentar.getErsteller_Id(), new AsyncCallback<Nutzer>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Nutzer result) {
				nickname.setText(result.getNickname());
				text.setText(kommentar.getText());			
				datum.setText(DateTimeFormat.getFormat("dd-MM-yyyy HH:mm:ss").format(kommentar.getErstellungszeitpunkt()));

				
				setWidget(rowCount, 0, datum);
				setWidget(rowCount, 1, nickname);
				setWidget(rowCount+1, 0, text);
				
			}
		});
		
	
		
		
	}
	
	

}
