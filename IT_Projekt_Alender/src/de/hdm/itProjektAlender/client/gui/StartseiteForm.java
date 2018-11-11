package de.hdm.itProjektAlender.client.gui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Klasse welche die Startseite repräsentiert.
 * @author Tom
 *
 */
public class StartseiteForm extends VerticalPanel{


	public StartseiteForm() {
		/**
		 * Anlegen der GUI-Elemente
		 */
		VerticalPanel homePanel = new VerticalPanel();
		VerticalPanel inputPanel = new VerticalPanel();
		
		Label welcomeLabel = new Label("Herzlich Willkommen auf Gesichtsbuch");
		
		
		welcomeLabel.setStylePrimaryName("willkommen_label");
		
		
		inputPanel.setSpacing(8);
		homePanel.setSpacing(10);
		this.setSpacing(8);
		this.add(welcomeLabel);
		this.add(homePanel);
		this.add(inputPanel);

	}
}
