package de.hdm.itProjektAlender.shared.bo;

import java.util.Date;

import de.hdm.itProjektTA.shared.bo.BusinessObject;

public class Like extends BusinessObject {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private Date erstellungszeitpunkt = null;
	
	private int ersteller_Id = 0;
	
	private int beitrag_Id = 0;
	
	public void setErstellungszeitpunkt(Date erstellungszeitpunkt){
		this.erstellungszeitpunkt=erstellungszeitpunkt;
	}
	public Date getErstellungszeitpunkt()
	{
		return erstellungszeitpunkt;
	}
	
	public void setErstellerId (int ersteller_Id){
		this.ersteller_Id = ersteller_Id;
	}
	
	public int getErstellerId (){
		return ersteller_Id;
	}
	
	
	
	

}
