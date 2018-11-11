package de.hdm.itProjektAlender.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itProjektAlender.shared.bo.Abonnement;
import de.hdm.itProjektAlender.shared.bo.Nutzer;
import de.hdm.itProjektAlender.shared.bo.Pinnwand;

@RemoteServiceRelativePath("socialmediaadmin")
public interface SocialMediaAdmin extends RemoteService {
	
	public void init() throws IllegalArgumentException;
	
	public Vector<Nutzer> getAllNutzer() throws IllegalArgumentException;
	
	public Nutzer createNutzer(String name, String nachname, String nickname, String email) throws IllegalArgumentException;

	public Nutzer checkNutzer(String email) throws IllegalArgumentException;

	public Nutzer findNutzerByAbo(Abonnement a) throws IllegalArgumentException;

	public Vector<Nutzer> listAbonnements(int id) throws IllegalArgumentException;

	public Pinnwand createPinnwand(int erstellerId) throws IllegalArgumentException;

	public Nutzer findNutzerByPinnwand(Pinnwand p) throws IllegalArgumentException;

	public Pinnwand findPinnwandbyAbo(Abonnement a) throws IllegalArgumentException;

	public Vector<Nutzer> getAllOtherNutzer(int id) throws IllegalArgumentException;

	public Abonnement createAbonnement(int nutzerId, String nickname) throws IllegalArgumentException;

	public Nutzer findNutzerbyNickname(String nickname) throws IllegalArgumentException;

	public Pinnwand findPinnwandByNutzer(int id) throws IllegalArgumentException;

	public Nutzer findNutzerById(int id)throws IllegalArgumentException;

	public Boolean checkAbonnement(int id, String nickname) throws IllegalArgumentException;
}
