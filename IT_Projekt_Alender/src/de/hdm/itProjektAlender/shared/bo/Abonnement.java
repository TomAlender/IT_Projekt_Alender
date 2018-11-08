package de.hdm.itProjektAlender.shared.bo;

public class Abonnement extends BusinessObject {

	
	private static final long serialVersionUID = 1L;
	private int nutzerId = 0;
	private int pinnwandId =0;
	
	public int getPinnwandId() {
		return pinnwandId;
	}
	public void setPinnwandId(int pinnwandId) {
		this.pinnwandId = pinnwandId;
	}
	public int getNutzerId() {
		return nutzerId;
	}
	public void setNutzerId(int nutzerId) {
		this.nutzerId = nutzerId;
	}
}
