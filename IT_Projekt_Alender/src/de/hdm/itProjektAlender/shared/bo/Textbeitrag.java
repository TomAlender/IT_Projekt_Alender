package de.hdm.itProjektAlender.shared.bo;

import java.util.Date;

public class Textbeitrag extends BusinessObject {
	
	private static final long serialVersionUID = 1L;

	private Date erstellungszeitpunkt = new Date();
	
	private String text = null;
	
	private Integer ersteller_Id = 0;

	public Date getErstellungszeitpunkt() {
		return erstellungszeitpunkt;
	}

	public void setErstellungszeitpunkt(Date erstellungszeitpunkt) {
		this.erstellungszeitpunkt = erstellungszeitpunkt;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getErsteller_Id() {
		return ersteller_Id;
	}

	public void setErsteller_Id(Integer ersteller_Id) {
		this.ersteller_Id = ersteller_Id;
	}
	
	
	
}
