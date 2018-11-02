package de.hdm.itProjektAlender.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.itProjektAlender.shared.bo.Nutzer;
import de.hdm.itProjektAlender.shared.bo.Pinnwand;


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
	  
	  public void insert(Nutzer n, Pinnwand p){
		  
		  
		    Connection con = DBConnection.connection();
		    
		    try {
		    	
		      Statement stmt = con.createStatement();
		      
		        
		        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
		        stmt.executeUpdate("INSERT INTO abonnement (Nutzer_Id, Pinnwand_Id)" + "VALUES (" + n.getId() + ",'"
							+ p.getId() +"')"); 
		        
		        
		      
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
	  public void delete(Nutzer n, Pinnwand p){
		  
		    Connection con = DBConnection.connection();

		    try {
		    	
		      Statement stmt = con.createStatement();
		      
		        stmt = con.createStatement();

		        // Jetzt erst erfolgt die tatsächliche Einfügeoperation
		        stmt.executeUpdate("DELETE FROM `abonnement` WHERE `abonnement`.`Nutzer_Id` = "+n.getId()+" AND `abonnement`.`Pinnwand_Id` = "+p.getId()); 
		        
		        
		      
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
	  public Vector<Pinnwand> findAbonniertePinnwaende(Nutzer n){
	        // DB-Verbindung holen
	        Connection con = DBConnection.connection();
	        Vector <Pinnwand> p = new Vector<>();

	        try {
	          // Leeres SQL-Statement (JDBC) anlegen
	          Statement stmt = con.createStatement();

	          // Statement ausfüllen und als Query an die DB schicken
	          ResultSet rs = stmt.executeQuery("SELECT Pinnwand_Id FROM abonnement WHERE Nutzer_Id="+n.getId());
	          Vector <Integer> PinnwandIDs= new Vector<>();
	     
	          
	          while(rs.next()){
	        	  PinnwandIDs.add(rs.getInt("Pinnwand_Id"));
	          }
	          
	          for (Integer id : PinnwandIDs) {
	        	  p.add(PinnwandMapper.pinnwandMapper().findPinnwandById(id));
			}
	        }
	        catch (SQLException e) {
	          e.printStackTrace();
	        }
	        return p;
	  }
	  
	  /**
	   * 
	   * @param pr
	   * @return Vector mit allen Personen die sich auf dem übergebenen Projektmarktplatz pr befinden
	   */
	  public Vector<Nutzer> findAbonnenten(Pinnwand p){
	        // DB-Verbindung holen
	        Connection con = DBConnection.connection();
	         Vector <Nutzer> n = new Vector<>();

	        try {
	          // Leeres SQL-Statement (JDBC) anlegen
	          Statement stmt = con.createStatement();

	          // Statement ausfüllen und als Query an die DB schicken
	          ResultSet rs = stmt.executeQuery("SELECT * FROM abonnement WHERE Pinnwand_Id="+p.getId());
	          Vector <Integer> NutzerIds = new Vector<>();
	     
	          
	          while(rs.next()){
	        	 NutzerIds.add(rs.getInt("Nutzer_Id"));
	          }
	          
	          for (Integer id : NutzerIds) {
	        	  n.add(NutzerMapper.nutzerMapper().findNutzerById(id));			
	        }
	        }
	        catch (SQLException e) {
	          e.printStackTrace();
	        }
	        return n;
	  }

}


