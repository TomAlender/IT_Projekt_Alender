package de.hdm.itProjektAlender.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itProjektAlender.shared.bo.Nutzer;

@RemoteServiceRelativePath("socialmediaadmin")
public interface SocialMediaAdmin extends RemoteService {
	
	public void init() throws IllegalArgumentException;
	
	public Vector<Nutzer> getAllNutzer() throws IllegalArgumentException;
	
	public Nutzer createNutzer(String name, String nachname, String nickname, String email) throws IllegalArgumentException;
}
