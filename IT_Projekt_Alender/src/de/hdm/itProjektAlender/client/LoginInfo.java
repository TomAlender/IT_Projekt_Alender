package de.hdm.itProjektAlender.client;

import java.io.Serializable;

/**
 * Die Klasse LoginInfo implementiert Serializable 
 * Serializiable ist eine Technik, mit der Objekte, 
 * inklusive aller dazu gehörigen Verknüpfungen in einen Datenstrom, also eine Abfolge von Bits, umgewandelt wird.
 * @author Tom
 *
 */
public class LoginInfo  implements Serializable{

	/**
	 * Deklarierung Variablen
	 */
	private static final long serialVersionUID = 1L;
	private boolean loggedIn = false;
	private String loginUrl;
	private String logoutUrl;
	private String emailAddress;

	/**
	 * Abfrage ob User eingeloggt ist
	 * @return loggedIn
	 */
	public boolean isLoggedIn(){
		return loggedIn;
	}

	/**
	 * Gibt die LoginUrl in textueller-Form
	 * @return loginUrl
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * Setzt die loginUrl als String
	 * @param loginUrl
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * Gibt die LogoutUrl in textueller-Form zurück
	 * @return logoutUrl
	 */
	public String getLogoutUrl() {
		return logoutUrl;
	}
	
	/**
	 * Setzt die LogoutUrl als String
	 * @param logoutUrl
	 */
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	/**
	 * Gibt die EmailAdresse in textueller-Form zurück
	 * @return emailAdresse
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Setzt emailAdresse als String
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Setzt die variable loggedIn auf true, wenn der User eingeloggt ist
	 * @param loggedIn
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	
	
}
