package de.hdm.itProjektAlender.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;

import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Kommentar;

public class Cell extends AbstractCell<BeitragForm>{
	

	SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, BeitragForm value, SafeHtmlBuilder sb) {
//		sb.appendHtmlConstant(value.gettArea().toString());
//		sb.appendHtmlConstant(value.getBt1().toString());
//		sb.appendHtmlConstant(value.getLabel().toString());
		
		sb.appendHtmlConstant(value.flex.toString());
		sb.appendHtmlConstant(value.testHtml.getHTML());
//		sb.appendHtmlConstant(value.flexTest.toString());
	
	}
	
	
}
