package de.hdm.itProjektAlender.client.gui;

import java.util.Vector;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;

import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Nutzer;

public class NavigationForm extends VerticalPanel {
	
	VerticalPanel navPanel = new VerticalPanel();
	Button aboButton = new Button("Abonnieren");	
	SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();
	Button myPinnwandButton = new Button("Meine Pinnwand anzeigen");
	
	ProvidesKey<Nutzer> keyProvider = new ProvidesKey<Nutzer>() {
		
    public Object getKey(Nutzer item) {
            // Always do a null check.
            return (item == null) ? null : item.getId();
         }
      };
      CellList<Nutzer> abo = new CellList<Nutzer>(new NutzerCell(), keyProvider);
	public void onLoad(){
		
		super.onLoad();	
		
		navPanel.add(aboButton);
		navPanel.add(myPinnwandButton);
		navPanel.add(abo);
		aboButton.addClickHandler(new AbonniereClickhandler());
		myPinnwandButton.addClickHandler(new myPinnwandClickhandler());
		this.add(navPanel);
		
		socialMedia.listAbonnements(Integer.parseInt(Cookies.getCookie("id")), new ListAsyncCallback());	
		
		
		
	}
	
	private class myPinnwandClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();
						
		    RootPanel.get("Details").add(new MeinePinnwandForm());
			
		}
		
	}
	
	 private class AbonniereClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			new DialogBoxAbonnieren().show();
			
		}
		 
	 }
	 
	  private static class NutzerCell extends AbstractCell<Nutzer> {
		  
			@Override
			public void render(Context context, Nutzer value, SafeHtmlBuilder sb) {
				if(value != null){
				sb.appendEscaped(value.getNickname());
//				sb.appendEscaped(value.getNachname());
			}
		  }
	  }
	
	class ListAsyncCallback implements AsyncCallback<Vector<Nutzer>>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}
			

		@Override
		public void onSuccess(Vector<Nutzer> result) {
			
			abo.setRowData(0,result);
			abo.setRowCount(result.size(), true);		
			
			abo.redraw();
			
			
		}
		
	}
}