package de.hdm.itProjektAlender.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;


import de.hdm.itProjektAlender.shared.bo.Abonnement;
import de.hdm.itProjektAlender.shared.bo.Nutzer;
import de.hdm.itProjektAlender.shared.bo.Pinnwand;

public interface SocialMediaAdminAsync {
	
	void init(AsyncCallback<Void> callback);

	void getAllNutzer(AsyncCallback<Vector<Nutzer>> asyncCallback);

	void createNutzer(String name, String nachname, String nickname, String email,AsyncCallback <Nutzer> asyncCallback);
	
	void checkNutzer(String email,AsyncCallback <Nutzer> asyncCallback);
	
	void findNutzerByAbo(Abonnement a, AsyncCallback <Nutzer> asyncCallback);
	
	void listAbonnements(int id, AsyncCallback <Vector<Nutzer>> asyncCallback);
	
	void createPinnwand(int erstellerId, AsyncCallback <Pinnwand> asyncCallback);
	
	void findNutzerByPinnwand(Pinnwand p, AsyncCallback <Nutzer> asyncCallback);
	
	void findPinnwandbyAbo(Abonnement a, AsyncCallback <Pinnwand> asyncCallback);
	
	void getAllOtherNutzer(int id, AsyncCallback<Vector<Nutzer>> asyncCallback);
	
	void createAbonnement(int nutzerId, String nickname, AsyncCallback <Abonnement> asyncCallback);
	
	void findNutzerbyNickname(String nickname, AsyncCallback <Nutzer> asyncCallback);
	
	void findPinnwandByNutzer(int id, AsyncCallback <Pinnwand> asyncCallback);
	
	void findNutzerById(int id, AsyncCallback <Nutzer> asyncCallback);
	
	void checkAbonnement(int id, String nickname, AsyncCallback <Boolean> asyncCallback);

	
}
