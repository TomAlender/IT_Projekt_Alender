package de.hdm.itProjektAlender.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import de.hdm.itProjektAlender.shared.bo.*;



public class PinnwandMapper {
	
	private static PinnwandMapper pinnwandMapper = null;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd k:mm:s");
	
	protected PinnwandMapper(){
		
	}

	public static PinnwandMapper pinnwandMapper() {
		if (pinnwandMapper == null) {
			pinnwandMapper = new PinnwandMapper();
		}

		return pinnwandMapper;
	}
	
	public Pinnwand findPinnwandById(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT Pinnwand_Id, Ersteller_Id, Erstellungszeitpunkt FROM pinnwand " + "WHERE Pinnwand_Id=" + id);

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Pinnwand p = new Pinnwand();
				p.setId(rs.getInt("Pinnwand_Id"));
				p.setErsteller_Id(rs.getInt("Ersteller_Id"));
				p.setErstellungszeitpunkt(rs.getDate("Erstellungszeitpunkt"));
				
				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}
	
	public Pinnwand findPinnwandByNutzer(int nutzerId){
		
		Connection con = DBConnection.connection();
		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			
			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT Pinnwand_Id, Ersteller_Id, Erstellungszeitpunkt FROM pinnwand " + "WHERE Ersteller_Id=" + nutzerId);

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Pinnwand p = new Pinnwand();
				p.setId(rs.getInt("Pinnwand_Id"));
				p.setErsteller_Id(rs.getInt("Ersteller_Id"));
				p.setErstellungszeitpunkt(rs.getDate("Erstellungszeitpunkt"));
				
				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	
	}
	
	protected Pinnwand findByObject(Pinnwand p){
		return this.findPinnwandById(p.getId()); 
	}
	
	public Pinnwand insert(Pinnwand p) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(Pinnwand_Id) AS maxid " + "FROM pinnwand ");

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * n erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				p.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO pinnwand (Pinnwand_Id, Ersteller_Id, Erstellungszeitpunkt) " + "VALUES (" + p.getId() + ",'"
						+ p.getErsteller_Id() + "','" + format.format(p.getErstellungszeitpunkt()) + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * Rückgabe, des evtl. korrigierten Customers.
		 * 
		 * HINWEIS: Da in Java nur Referenzen auf Objekte und keine physischen
		 * Objekte übergeben werden, wäre die Anpassung des Customer-Objekts
		 * auch ohne diese explizite Rückgabe au�erhalb dieser Methode sichtbar.
		 * Die explizite Rückgabe von c ist eher ein Stilmittel, um zu
		 * signalisieren, dass sich das Objekt evtl. im Laufe der Methode
		 * verändert hat.
		 */
		return p;
	}

	
	/**
	 * Löschen der Daten eines <code>Customer</code>-Objekts aus der Datenbank.
	 * 
	 * @param c
	 *            das aus der DB zu löschende "Objekt"
	 */
	public void delete(Pinnwand p) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM pinnwand " + "WHERE Pinnwand_Id=" + p.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
