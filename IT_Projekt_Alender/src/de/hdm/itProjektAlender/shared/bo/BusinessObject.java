package de.hdm.itProjektAlender.shared.bo;

import java.io.Serializable;

/**
 * Die Klasse <code>BusinessObject</code> stellt die Basisklasse aller in diesem
 * Projekt fuer die Umsetzung des Fachkonzepts relevanten Klassen dar.
 *  
 * @author Tom Alender
 * @version 1.0
 */

public abstract class BusinessObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Die eindeutige Identifikationsnummer einer Instanz dieser Klasse.
	 */
	private int id = 0;
	
	/** 
	 * Auslesen der ID.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Setzen der ID.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanz.
	 */
		
	public String toString(){
		return this.getClass().getName() + " #" + this.id;
	}
	
	 /**
	  * Feststellen der <em>inhaltlichen</em> Gleichheit zweier <code>BusinessObject</code>-Objekte.
	  */
	public boolean equals(Object o){
		
		if (o != null && o instanceof BusinessObject) {
			BusinessObject bo = (BusinessObject) o;
			
			try {
				
				if (bo.getId() == this.id) {
					return true;
				}
			} catch (IllegalArgumentException e) {
				
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * Erzeugen einer ganzen Zahl, die f�r das <code>BusinessObject</code> charakteristisch ist.
	 */
	public int hashCode(){
		
		return this.id;
	}
}
