package de.hdm.itProjektAlender.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itProjektAlender.shared.bo.Nutzer;

public interface SocialMediaAdminAsync {
	
	void init(AsyncCallback<Void> callback);

	void getAllNutzer(AsyncCallback<Vector<Nutzer>> asyncCallback);

}
