package de.hdm.itProjektAlender.client;

import java.util.logging.Logger;

import com.google.gwt.core.shared.GWT;

import de.hdm.itProjektAlender.shared.CommonSettings;
import de.hdm.itProjektAlender.shared.LoginServiceAsync;
import de.hdm.itProjektAlender.shared.SocialMediaAdmin;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.LoginService;

public class ClientsideSettings extends CommonSettings{
	
	private static SocialMediaAdminAsync socialMediaAdmin = null;
		
private static LoginServiceAsync loginService = null;
	
	/**
	 * Name des Client-seitigen Loggers.
	 */
	private static final String LOGGER_NAME = "SocialMedia Web Client";
	
	/**
	 * Instanz des Client-seitigen Loggers.
	 */
	
	private static final Logger log = Logger.getLogger(LOGGER_NAME);
	
	/**
	 * Auslesen des applikationsweiten (Client-seitig) zentralen Loggers.
	 * @return die Logger-Instanz f�r die Server-Seite.
	 */
	
	public static Logger getLogger(){
		return log;
	}
	
	/**
	 * Durch die Methode wird die LoginService erstellt, sofern diese noch nicht besteht.
	 * Bei erneutem Aufruf der Methode wird das bereits angelegte Objekt zur�ckgegeben.
	 * 
	 * @return eindeutige Instanz des Typs <code>LoginServiceAsync</code>
	 */
	public static LoginServiceAsync getLoginService(){
		if(loginService == null){
			loginService = GWT.create(LoginService.class);
		}
		return loginService;
	}
	
	public static SocialMediaAdminAsync getSocialMediaAdmin() {
	    // Gab es bislang noch keine BankAdministration-Instanz, dann...
	    if (socialMediaAdmin == null) {
	      // Zun�chst instantiieren wir SocialMedia Applikation
	      socialMediaAdmin = GWT.create(SocialMediaAdmin.class);
	    }

	    
	    return socialMediaAdmin;
	  }
	

}
