package de.hdm.itProjektAlender.server;

import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itProjektAlender.server.db.*;

import de.hdm.itProjektAlender.shared.SocialMediaAdmin;
import de.hdm.itProjektAlender.shared.bo.Nutzer;

@SuppressWarnings("serial")
public class SocialMediaAdminImpl extends RemoteServiceServlet implements SocialMediaAdmin{
	
	private NutzerMapper nMapper = null;
	private LikeMapper lMapper = null;
	private AbonnementMapper aMapper = null;
	private TextbeitragMapper tMapper = null;
	private BeitragMapper bMapper = null;
	private KommentarMapper kMapper = null;
	private PinnwandMapper pMapper = null;
	
	

	public SocialMediaAdminImpl() throws IllegalArgumentException {
		
	    /*
	     * Eine weitergehende Funktion muss der No-Argument-Constructor nicht haben.
	     * Er muss einfach vorhanden sein.
	     */
	  }
	
	  @Override
	  public void init() throws IllegalArgumentException {
	      
	      this.nMapper  = NutzerMapper.nutzerMapper();
	      this.lMapper = LikeMapper.likeMapper();
	      this.aMapper = AbonnementMapper.abonnementMapper();
	      this.bMapper = BeitragMapper.beitragMapper();
	      this.kMapper = KommentarMapper.kommentarMapper();
	      this.pMapper = PinnwandMapper.pinnwandMapper();
	      this.tMapper = TextbeitragMapper.textbeitragMapper();
	      
	    }
	  
	  public Vector<Nutzer> getAllNutzer(){
		  Vector<Nutzer> n = new Vector<Nutzer>();
		  n= this.nMapper.findAllNutzer();
		 
		  
		return n;
		  
	  }

	
	

}
