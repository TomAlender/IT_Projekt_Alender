package de.hdm.itProjektAlender.shared.bo;

import java.util.Date;

public class Pinnwand {

	private Date erstellungszeitpunkt = null;
	private int ersteller_Id = 0;
	
	public Date getErstellungszeitpunkt() {
		return erstellungszeitpunkt;
	}
	
	public void setErstellungszeitpunkt(Date erstellungszeitpunkt) {
		this.erstellungszeitpunkt = erstellungszeitpunkt;
	}
	
	public int getErsteller_Id() {
		return ersteller_Id;
	}
	
	public void setErsteller_Id(int ersteller_Id) {
		this.ersteller_Id = ersteller_Id;
	}
	
	 
}
