package de.hdm.itProjektAlender.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itProjektAlender.client.LoginInfo;

/**
 * 
 * Asynchrones Interface für den LogIn
 *
 */
public interface LoginServiceAsync {
/**
 * 
 * @param requestUri
 * @param callback
 */
	void login(String requestUri, AsyncCallback<LoginInfo> callback);

}
