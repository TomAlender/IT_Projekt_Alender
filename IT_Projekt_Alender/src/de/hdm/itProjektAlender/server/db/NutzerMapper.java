package de.hdm.itProjektAlender.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;

import de.hdm.itProjektAlender.shared.bo.Nutzer;




public class NutzerMapper {
	
	private static NutzerMapper nutzerMapper = null;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd k:mm:s");
	
	protected NutzerMapper(){
		
	}

	public static NutzerMapper nutzerMapper() {
		if (nutzerMapper == null) {
			nutzerMapper = new NutzerMapper();
		}

		return nutzerMapper;
	}
	
	public Nutzer findNutzerById(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT Nutzer_Id, Vorname, Nachname, Nickname, Email, Erstellungszeitpunkt FROM nutzer " + "WHERE Nutzer_Id=" + id + " ORDER BY Nachname");

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Nutzer n = new Nutzer();
				n.setId(rs.getInt("Nutzer_Id"));
				n.setVorname(rs.getString("Vorname"));
				n.setNachname(rs.getString("Nachname"));
				n.setNickname(rs.getString("Nickname"));
				n.setEmail(rs.getString("Email"));
				n.setErstellungszeitpunkt(rs.getDate("Erstellungszeitpunkt"));

				return n;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}
	
	public Nutzer findNutzerByEmail(String email) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM nutzer " + "WHERE Email=" + email + " ORDER BY Nachname");

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Nutzer n = new Nutzer();
				n.setId(rs.getInt("Nutzer_Id"));
				n.setVorname(rs.getString("Vorname"));
				n.setNachname(rs.getString("Nachname"));
				n.setNickname(rs.getString("Nickname"));
				n.setEmail(rs.getString("Email"));
				n.setErstellungszeitpunkt(rs.getDate("Erstellungszeitpunkt"));

				return n;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}
	
	protected Nutzer findByObject(Nutzer n){
		return this.findNutzerById(n.getId()); 
	}
	
	public Nutzer findByNickname(String nickname)
	{
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM nutzer WHERE Nickname='" + nickname + "'");

			
			
			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Nutzer n = new Nutzer();
				
				n.setId(rs.getInt("Nutzer_Id"));
				n.setVorname(rs.getString("Vorname"));
				n.setNachname(rs.getString("Nachname"));
				n.setNickname(rs.getString("Nickname"));
				n.setEmail(rs.getString("Email"));
				n.setErstellungszeitpunkt(rs.getDate("Erstellungszeitpunkt"));
				
				return n;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}
	
	public Vector<Nutzer> findAllNutzer(){
		Connection con = DBConnection.connection();
		
		Vector<Nutzer> result = new Vector<Nutzer>();
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ " FROM nutzer ORDER BY Nutzer_Id");
			
			
			while (rs.next()){
				Nutzer n = new Nutzer();
				n.setId(rs.getInt("Nutzer_Id"));
				n.setVorname(rs.getString("Vorname"));
				n.setNachname(rs.getString("Nachname"));
				n.setNickname(rs.getString("Nickname"));
				n.setEmail(rs.getString("Email"));
				n.setErstellungszeitpunkt(rs.getDate("Erstellungszeitpunkt"));
				
				
				result.add(n);
				} 
			}  
		catch (SQLException e) {
		e.printStackTrace();
		}
		return result;
		
	}
	
	public Nutzer insert(Nutzer n) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(Nutzer_Id) AS maxid " + "FROM nutzer ");

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * n erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				n.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO nutzer (Nutzer_Id, Vorname, Nachname, Nickname, Email, Erstellungszeitpunkt ) " + "VALUES (" + n.getId() + ",'"
						+ n.getVorname() + "','" + n.getNachname() + "','" + n.getNickname() + "','" + n.getEmail() + "','" + format.format(n.getErstellungszeitpunkt()) + "')");
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
		return n;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param c
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Nutzer update(Nutzer n) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE nutzer " + "SET Vorname=\"" + n.getVorname() + "\", " + "lastName=\""
					+ n.getNachname()+ "\", " + "Nickname=\""
					+ n.getNickname()+ "\" " + "WHERE id=" + n.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Um Analogie zu insert(Customer c) zu wahren, geben wir c zurück
		return n;
	}
	
	

	/**
	 * Löschen der Daten eines <code>Customer</code>-Objekts aus der Datenbank.
	 * 
	 * @param c
	 *            das aus der DB zu löschende "Objekt"
	 */
	public void delete(Nutzer n) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM nutzer " + "WHERE Nutzer_Id=" + n.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

}
