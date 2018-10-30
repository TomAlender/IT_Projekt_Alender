package de.hdm.itProjektAlender.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;


import de.hdm.itProjektAlender.shared.bo.*;



public class BeitragMapper extends TextbeitragMapper {
	
	private static BeitragMapper beitragMapper = null;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd k:mm:s");
	
	protected BeitragMapper(){
		
	}

	public static BeitragMapper BeitragMapper() {
		if (beitragMapper == null) {
			beitragMapper = new BeitragMapper();
		}

		return beitragMapper;
	}
	
	public Textbeitrag findTextbeitragById(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT Textbeitrag_Id, Text, Ersteller_Id, Erstellungszeitpunkt FROM textbeitrag " + "WHERE Textbeitrag_Id=" + id);

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Textbeitrag t = new Textbeitrag();
				t.setId(rs.getInt("Nutzer_Id"));
				t.setText(rs.getString("Text"));
				t.setErstellungszeitpunkt(rs.getDate("Erstellungszeitpunkt"));
				t.setErsteller_Id(rs.getInt("Ersteller_Id"));
				

				return t;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}
	
	protected Textbeitrag findByObject(Textbeitrag t){
		return this.findTextbeitragById(t.getId()); 
	}
	
	
	
	public Textbeitrag insert(Textbeitrag t) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(Nutzer_Id) AS maxid " + "FROM textbeitragr ");

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * n erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				t.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO nutzer (Textbeitrag_Id, Ersteller_Id, Text, Erstellungszeitpunkt ) " + "VALUES (" + t.getId() + ",'"
						+ t.getErsteller_Id() + "','" + t.getText() + "','" + format.format(t.getErstellungszeitpunkt()) + "')");
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
		return t;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param c
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Textbeitrag update(Textbeitrag t) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE textbeitrag " + "SET Text=\"" + t.getText() +  "WHERE id=" + t.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Um Analogie zu insert(Customer c) zu wahren, geben wir c zurück
		return t;
	}

	/**
	 * Löschen der Daten eines <code>Customer</code>-Objekts aus der Datenbank.
	 * 
	 * @param c
	 *            das aus der DB zu löschende "Objekt"
	 */
	public void delete(Textbeitrag t) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM textbeitrag " + "WHERE Textbeitrag_Id=" + t.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
