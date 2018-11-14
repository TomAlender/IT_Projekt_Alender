package de.hdm.itProjektAlender.server;

import java.util.Iterator;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.itProjektAlender.server.db.*;

import de.hdm.itProjektAlender.shared.SocialMediaAdmin;
import de.hdm.itProjektAlender.shared.bo.*;


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
	  @Override
	  public Vector<Nutzer> getAllNutzer(){
		  Vector<Nutzer> n = new Vector<Nutzer>();
		  n= this.nMapper.findAllNutzer();
 
		return n;		  
	  }
	  @Override
	  public Nutzer createNutzer(String name, String nachname, String nickname, String email) {
	  Nutzer n = new Nutzer();	 
	  n.setVorname(name);
	  n.setNachname(nachname);
	  n.setNickname(nickname);
	  n.setEmail(email);
	  
	  n.setId(1);
	  return nMapper.insert(n);   
	  }
	  @Override
	  public Nutzer checkNutzer(String email){
		  
		 Vector<Nutzer> n = getAllNutzer();
		 for (Nutzer nutzer : n) {
			if(email.equals(nutzer.getEmail())){				
				return nutzer;				
			}			
		}
		 return null;
	  }
	  @Override
	  public Vector<Nutzer>listAbonnements(int id){
		  //System.out.println("1111: "+ id);
		Vector<Nutzer> n = new Vector<Nutzer>();
		Vector<Pinnwand> p = new Vector <Pinnwand>();
		Vector <Abonnement> a = this.findAbonnmentsByNutzer(id);
		//System.out.println(a.elementAt(0).getPinnwandId());
		for (Abonnement abonnement : a) {			
			p.add(findPinnwandbyAbo(abonnement));			
		}	
		for (Pinnwand result : p) {
			n.add(findNutzerByPinnwand(result));		
		}
	
		return n;
	  }
	  @Override
	  public Nutzer findNutzerByAbo(Abonnement a){
		  
		Nutzer n = this.findNutzerById(a.getNutzerId());
		return n;
	  }
	  @Override
	  public Pinnwand createPinnwand(int erstellerId){
		  Pinnwand p = new Pinnwand();
		  p.setErsteller_Id(erstellerId);
		 
		  return pMapper.insert(p);
	  }
	  @Override
	  public Nutzer findNutzerByPinnwand(Pinnwand p){
		  Nutzer n = new Nutzer();		  
		 n = this.findNutzerById(p.getErsteller_Id());
		  return n;
	  }
	  @Override
	  public Pinnwand findPinnwandbyAbo(Abonnement a){
		  
		  Pinnwand p = pMapper.findPinnwandById(a.getPinnwandId());
		  return p;
	  }
	 @Override
	 public Vector <Nutzer> getAllOtherNutzer(int id){
	 Vector <Nutzer> n = this.getAllNutzer();
     Nutzer currentNutzer = this.findNutzerById(id);		 
     //Vector <Abonnement> a = this.aMapper.findAbonnements(id);
     Vector <Nutzer> nu = new Vector <Nutzer>();
	 
		for (Nutzer nutzer : n){
			if(!currentNutzer.getNickname().equals(nutzer.getNickname())) {
				nu.add(nutzer);
			}
			
		}
		 
		 return nu;
	 }
	 @Override
	 public Vector <Abonnement>findAbonnmentsByNutzer(int nutzerId){
		 return this.aMapper.findAbonnements(nutzerId);
	 }
	 @Override
	  public Nutzer findNutzerbyNickname(String nickname){
		  return this.nMapper.findByNickname(nickname);
	  }
	 @Override
	 public Pinnwand findPinnwandByNutzer(int id){
		 return this.pMapper.findPinnwandByNutzer(id);
	 }
	 @Override
	 public Abonnement createAbonnement(int nutzerId, String nickname){
		Nutzer n = findNutzerbyNickname(nickname);		
		Pinnwand p = findPinnwandByNutzer(n.getId());
		
		Abonnement a = new Abonnement();
		  a.setNutzerId(nutzerId);
		  a.setPinnwandId(p.getId());
		  a.setId(1);
		
		  return aMapper.insert(a);
	 }
	 @Override
	 public Nutzer findNutzerById(int id){
		 return this.nMapper.findNutzerById(id);
	 }
	 @Override
	 public Boolean checkAbonnement(int id, String nickname){
		 Vector <Nutzer> n = this.listAbonnements(id);
		 
		 for (Nutzer nutzer : n) {
			 
			 if(nutzer.getNickname().equals(nickname)){
				 return false;
				 
			 }
			 
		}
		 
		 return true;
	 }
	 @Override 
	 public void deleteAbonnement(int nutzerId, String nickname){
		 Nutzer n = findNutzerbyNickname(nickname);
		 Pinnwand p = findPinnwandByNutzer(n.getId());
		 Abonnement a = new Abonnement();
		  a.setNutzerId(nutzerId);
		  a.setPinnwandId(p.getId());
		  a.setId(1);
		  this.aMapper.delete(a);
		  
		  return; 
		 
	 }
	
	 @Override
	  public Beitrag createBeitrag(int erstellerId, String text, int pinnwandId){
		  Beitrag b = new Beitrag();
		  b.setErsteller_Id(erstellerId);
		  b.setText(text);
		  b.setPinnwand_Id(pinnwandId);
		  
		 
		 return bMapper.insert(b);
	  }
	 
	 
}
	  



	
	


