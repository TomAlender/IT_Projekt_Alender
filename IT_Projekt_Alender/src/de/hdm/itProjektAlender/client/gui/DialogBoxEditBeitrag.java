package de.hdm.itProjektAlender.client.gui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Beitrag;

public class DialogBoxEditBeitrag extends DialogBox{
	
	SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();
	Beitrag b = new Beitrag();
	VerticalPanel eins = new VerticalPanel();
	Label oldText = new Label();
	Button btnAendern = new Button("Änderung speichern");
	Button btnLoeschen = new Button("Beitrag löschen");
	Button btnAbbrechen = new Button("Abbrechen");
	HorizontalPanel btnPanel = new HorizontalPanel();
		
	public DialogBoxEditBeitrag(final int beitragId) {
		// TODO Auto-generated constructor stub
		this.setText("Beitrag editieren");
		this.setAnimationEnabled(false);
		this.setGlassEnabled(true);
		this.center();
		btnPanel.add(btnAendern);
		btnPanel.add(btnLoeschen);
		btnPanel.add(btnAbbrechen);
		
		
		socialMedia.findBeitragById(beitragId, new BeitragCallBack());
		
		
		this.add(eins);
		this.add(btnPanel);
		
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
		oldText.setText("Alter Text: "+b.getText());
		Window.alert(oldText.getText());
		eins.add(oldText);
		
		
		
	}
	
}
	
}
			
		
		
		
