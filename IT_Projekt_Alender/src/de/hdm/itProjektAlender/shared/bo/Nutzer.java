package de.hdm.itProjektAlender.shared.bo;

import java.util.Date;

public class Nutzer extends BusinessObject{
	
	private static final long serialVersionUID = 1L;
	
	private Date erstellungszeitpunkt = new Date();
	
	private String vorname = null;
	
	private String nachname = null;
	
	private String nickname = null;

	public Date getErstellungszeitpunkt() {
		return erstellungszeitpunkt;
	}

	public void setErstellungszeitpunkt(Date erstellungszeitpunkt) {
		this.erstellungszeitpunkt = erstellungszeitpunkt;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	

}
