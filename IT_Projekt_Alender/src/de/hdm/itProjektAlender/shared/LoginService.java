package de.hdm.itProjektAlender.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itProjektAlender.client.LoginInfo;

/**
 * Interface um den LogIn zu realisieren
 * 
 */
@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService{
	/**
	 * 
	 * @param requestUri
	 * @return
	 */
	public LoginInfo login(String requestUri);

}
