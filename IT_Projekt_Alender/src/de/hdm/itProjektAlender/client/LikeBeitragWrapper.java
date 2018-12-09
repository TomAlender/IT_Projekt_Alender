package de.hdm.itProjektAlender.client;


import java.io.Serializable;

import de.hdm.itProjektAlender.shared.bo.Beitrag;

public class LikeBeitragWrapper implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int anzahlLikes = 0;
	private boolean like = false;
	private Beitrag beitrag = new Beitrag();
	
	public LikeBeitragWrapper(){
	}
	
	public LikeBeitragWrapper(boolean like, Beitrag beitrag, int anzahl){
		this.anzahlLikes = anzahl;
		this.like = like;
		this.beitrag = beitrag;	
	}

	public int getAnzahlLikes() {
		return anzahlLikes;
	}

	public void setAnzahlLikes(int anzahlLikes) {
		this.anzahlLikes = anzahlLikes;
	}

	public boolean isLike() {
		return like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public Beitrag getBeitrag() {
		return beitrag;
	}

	public void setBeitrag(Beitrag beitrag) {
		this.beitrag = beitrag;
	}
	
	
	
}
