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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Nutzer;

public class NavigationForm extends VerticalPanel {
	
	VerticalPanel navPanel = new VerticalPanel();
	ScrollPanel scroll = new ScrollPanel();
	Button aboButton = new Button("Abonnieren");	
	SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();
	Button myPinnwandButton = new Button("Meine Pinnwand");
	
	ProvidesKey<Nutzer> keyProvider = new ProvidesKey<Nutzer>() {
		
    public Object getKey(Nutzer item) {
            // Always do a null check.
            return (item == null) ? null : item.getId();
         }
      };
      final SingleSelectionModel<Nutzer> selectionModel = new SingleSelectionModel<Nutzer>();
      CellList<Nutzer> abo = new CellList<Nutzer>(new NutzerCell(), keyProvider);
    
      
     
      
	public void onLoad(){
		
		super.onLoad();	
		scroll.setSize("auto", "auto");
		navPanel.add(aboButton);
		navPanel.add(myPinnwandButton);
		scroll.add(abo);
		navPanel.add(scroll);
		aboButton.setStylePrimaryName("nav-btn");
		aboButton.addClickHandler(new AbonniereClickhandler());
		myPinnwandButton.setStylePrimaryName("nav-btn");
		myPinnwandButton.addClickHandler(new myPinnwandClickhandler());
		
		this.add(navPanel);
		abo.setSelectionModel(selectionModel);
		     
		
		socialMedia.listAbonnements(Integer.parseInt(Cookies.getCookie("id")), new ListAsyncCallback());
		
		 selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
		        Nutzer n = selectionModel.getSelectedObject();
		        if (n != null) {
		        	
		        RootPanel.get("Details").clear();
		        RootPanel.get("Details").add(new FremdePinnwand(n.getId()));
		        }
		      }
		    });
		
		
		
	}
	
	private class myPinnwandClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();
			RootPanel.get("Navigator").clear();	
			RootPanel.get("Navigator").add(new NavigationForm());
			//RootPanel.get("Details").add(new MeinePinnwandForm(Integer.parseInt(Cookies.getCookie("id"))));
			//RootPanel.get("Details").add(new CellTest(Integer.parseInt(Cookies.getCookie("id"))));
		RootPanel.get("Details").add(new CellTableWrapper());
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
