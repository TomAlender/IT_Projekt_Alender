package de.hdm.itProjektAlender.client.gui;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.client.LikeBeitragWrapper;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Like;

public class CellTableWrapper extends VerticalPanel{
	
	 CellTable<LikeBeitragWrapper> table = new CellTable<LikeBeitragWrapper>();
	 SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();
	 
	 
	 ButtonCell buttonCell = new ButtonCell();
	 TextColumn<LikeBeitragWrapper> textColumn = new TextColumn<LikeBeitragWrapper>() {
		 @Override
		 public String getValue(LikeBeitragWrapper object) {
			 return object.getBeitrag().getText();
		 }
	 };
	 
	 TextColumn<LikeBeitragWrapper> dateColumn = new TextColumn<LikeBeitragWrapper>() {
		 @Override
		 public String getValue(LikeBeitragWrapper object) {
			
			 return DateTimeFormat.getFormat("dd.MM.yyyy HH:mm")
						.format(object.getBeitrag().getErstellungszeitpunkt());
		 }
	 };
	 
	 	List<String> likeNames = new ArrayList<String>();
	 	SelectionCell anzlike = new SelectionCell(likeNames) {
		 
		
		
	 
	 };
//		 @Override
//		 public String getValue(LikeBeitragWrapper object) {
//			 String likes;
//			 if(object.getAnzahlLikes()==1){
//			likes = object.getAnzahlLikes() + " Like";
//			 }
//			 else{
//			likes = object.getAnzahlLikes() + " Likes"; 
//			 }
//			 return likes;
//			 
//		 }
//	 };
//	 
	 Column<LikeBeitragWrapper, String> kommbuttonColumn = new Column<LikeBeitragWrapper, String>(buttonCell) {
		 @Override
		 public String getValue(LikeBeitragWrapper object) {
			 // The value to display in the button.
			 return "Kommentare";
		 }
	 };
	 
	 Column<LikeBeitragWrapper, String> buttonColumn = new Column<LikeBeitragWrapper, String>(buttonCell) {
		 @Override
		 public String getValue(LikeBeitragWrapper object) {
			 // The value to display in the button.
			 return "Like";
		 }
	 };
	 
	 Column<LikeBeitragWrapper, String> categoryColumn = new Column<LikeBeitragWrapper, String>(anzlike) {    
			@Override
			public String getValue(LikeBeitragWrapper object) {
				// TODO Auto-generated method stub
				return object.getBeitrag().getText();
			}
		    };
	 public CellTableWrapper(){
		 
		 	this.add(table);
		 	table.addColumn(dateColumn,"");
		    table.addColumn(textColumn, "");
		   // table.addColumn(anzlike,"");
		    table.addColumn(buttonColumn, "");
		    table.addColumn(kommbuttonColumn,"");
		    
		    socialMedia.findBeitragAufEigenerPinnwandWrapper(Integer.parseInt(Cookies.getCookie("id")),
		    		new AsyncCallback<Vector<LikeBeitragWrapper>>() {
				
				@Override
				public void onSuccess(Vector<LikeBeitragWrapper> result) {
					// TODO Auto-generated method stub
					
					table.setRowCount(result.size(), true);
					table.setRowData(0, result);
					for (LikeBeitragWrapper likeBeitragWrapper : result) {
						socialMedia.findLikesByBeitrag(likeBeitragWrapper.getBeitrag().getId(), new LikeCallback());
					}
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
				}
			});
	 }
	 
	 public class  LikeCallback implements AsyncCallback<Vector<Like>>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Vector<Like> result) {
			// TODO Auto-generated method stub
			Vector<Like> eins = new Vector<>();
			for (Like like : result) {
				eins.add(like);
			}
			 
		}
		 
	 }
}
