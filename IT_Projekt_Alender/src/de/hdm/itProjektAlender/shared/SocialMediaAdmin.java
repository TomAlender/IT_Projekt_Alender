package de.hdm.itProjektAlender.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itProjektAlender.client.LikeBeitragWrapper;
import de.hdm.itProjektAlender.shared.bo.Abonnement;
import de.hdm.itProjektAlender.shared.bo.Beitrag;
import de.hdm.itProjektAlender.shared.bo.Kommentar;
import de.hdm.itProjektAlender.shared.bo.Like;
import de.hdm.itProjektAlender.shared.bo.Nutzer;
import de.hdm.itProjektAlender.shared.bo.Pinnwand;
import de.hdm.itProjektAlender.shared.bo.Textbeitrag;

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

	public void deleteAbonnement(int nutzerId, String nickname) throws IllegalArgumentException;

	public Vector<Abonnement> findAbonnmentsByNutzer(int nutzerId) throws IllegalArgumentException;

	public Beitrag createBeitrag(int erstellerId, int pinnwandId, String text) throws IllegalArgumentException;

	public Vector<Beitrag> findBeitraegeByPinnwand(int id) throws IllegalArgumentException;

	public Beitrag findBeitragById(int id) throws IllegalArgumentException;

	public void editBeitrag(Beitrag b) throws IllegalArgumentException;

	public void deleteBeitrag(Beitrag b) throws IllegalArgumentException;

	public Vector<Like> findLikesByBeitrag(int beitragId) throws IllegalArgumentException;

	Vector<Nutzer> findNutzerByLikes(int beitragId)throws IllegalArgumentException;

	public boolean checkLike(int nutzerId, int beitragId) throws IllegalArgumentException;

	public Like createLike(int erstellerId, int beitragId) throws IllegalArgumentException;

	public void unlike(int nutzerId, int beitragId) throws IllegalArgumentException;

	public Like findLikeById(int id)throws IllegalArgumentException;

	public Vector<Beitrag> findBeitraegeaufEigenerPinnwand(int nutzerId)throws IllegalArgumentException;

	public Vector<LikeBeitragWrapper> findBeitragAufEigenerPinnwandWrapper(int nutzerId);

	public Vector<Kommentar> findKommentareByBeitrag(int beitragId);	

	public Kommentar createKommentar(int erstellerId, int beitragId, String text);

	public Vector<LikeBeitragWrapper> findBeitragAufFremderPinnwandWrapper(int nutzerId, int currentnutzer);
}
