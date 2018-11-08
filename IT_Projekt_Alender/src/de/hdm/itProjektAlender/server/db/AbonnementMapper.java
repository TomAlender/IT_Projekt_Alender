package de.hdm.itProjektAlender.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itProjektAlender.shared.bo.Abonnement;



/**
 * Mapper f�r Teilnahme-Objekte um die Teilnahme von Personen auf Marktpl�tzen darzustellen.
 */
public class AbonnementMapper{

	
	/**
	 * Speicherung der einzigen Instanz dieser Mapperklasse.
	 */
	private static AbonnementMapper abonnementMapper = null;
	
	/**
	 * Geschuetzter Konstruktor um zu verhindern, dass unkontrolliert Instanzen dieser Klasse erstellt werden
	 */
	protected AbonnementMapper(){
	}
	
	/**
	 * @return teilnahmeMapper - Sicherstellung der Singleton-Eigenschaft der Mapperklasse 
	 */
	  public static AbonnementMapper abonnementMapper() {
		    if (abonnementMapper == null) {
		      abonnementMapper = new AbonnementMapper();
		    }

		    return abonnementMapper;
		  }
	  
	  public void insert(Abonnement a){
		  
		  
		    Connection con = DBConnection.connection();
		    
		    try {
		    	
		      Statement stmt = con.createStatement();
		      
		        
		        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
		        stmt.executeUpdate("INSERT INTO abonnement (Nutzer_Id, Pinnwand_Id)" + "VALUES (" + a.getNutzerId() + ",'"
							+ a.getPinnwandId() +"')"); 
		        
		        
		      
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    return;
	  }
	  
	  /**
	   * 
	   * @param pe
	   * @param pr
	   */
	  public void delete(Abonnement a){
		  
		    Connection con = DBConnection.connection();

		    try {
		    	
		      Statement stmt = con.createStatement();
		      
		        stmt = con.createStatement();

		        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
		        stmt.executeUpdate("DELETE FROM `abonnement` WHERE `abonnement`.`Nutzer_Id` = "+ a.getNutzerId()+" AND `abonnement`.`Pinnwand_Id` = "+a.getPinnwandId()); 
		        
		        
		      
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		    }

		    return;
	  }
	  
	  /**
	   * 
	   * @param pe
	   * @return Vector mit allen Pinnwanaenden welche der übergebene Nutzer n abonniert hat
	   */
	  public Vector<Abonnement> findAbonniertePinnwaende(int nutzerId){
	        // DB-Verbindung holen
	        Connection con = DBConnection.connection();
	        Vector <Abonnement> a = new Vector<Abonnement>();

	        try {
	          // Leeres SQL-Statement (JDBC) anlegen
	          Statement stmt = con.createStatement();

	          // Statement ausfüllen und als Query an die DB schicken
	          ResultSet rs = stmt.executeQuery("SELECT Pinnwand_Id FROM abonnement WHERE Nutzer_Id="+nutzerId);
	        
	     
	          
	          while(rs.next()){
	        	  Abonnement ab = new Abonnement();
	        	  ab.setPinnwandId(rs.getInt("Pinnwand_Id"));
	        	  a.add(ab);
	          }        	 
	          
	        }
	        catch (SQLException e) {
	          e.printStackTrace();
	        }
	        return a;
	  }
	  
	  /**
	   * 
	   * @param pr
	   * @return Vector mit allen Personen die sich auf dem übergebenen Projektmarktplatz pr befinden
	   */
	  public Vector<Abonnement> findAbonnenten(int pinnwandId){
	        // DB-Verbindung holen
	        Connection con = DBConnection.connection();
	         Vector <Abonnement> a = new Vector<Abonnement>();

	        try {
	          // Leeres SQL-Statement (JDBC) anlegen
	          Statement stmt = con.createStatement();

	          // Statement ausfüllen und als Query an die DB schicken
	          ResultSet rs = stmt.executeQuery("SELECT * FROM abonnement WHERE Pinnwand_Id="+pinnwandId);
	          Abonnement abo = new Abonnement();
	     
	          
	          while(rs.next()){
	        	 abo.setNutzerId(rs.getInt("Nutzer_Id"));
	        	a.add(abo);
	          }
	          
	          
	        }
	        catch (SQLException e) {
	          e.printStackTrace();
	        }
	        return a;
	  }

}


