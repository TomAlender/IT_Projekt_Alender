package de.hdm.itProjektAlender.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;

import de.hdm.itProjektAlender.shared.bo.*;




public class TextbeitragMapper {
	
	private static TextbeitragMapper textbeitragMapper = null;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd k:mm:s");
	
	protected TextbeitragMapper(){
		
	}

	public static TextbeitragMapper textbeitragMapper() {
		if (textbeitragMapper == null) {
			textbeitragMapper = new TextbeitragMapper();
		}

		return textbeitragMapper;
	}
	
	protected Textbeitrag findTextbeitragById(int id) {
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
	
	protected Vector <Textbeitrag> findTextbeitragByNutzer(int nutzerId){
		
		Connection con = DBConnection.connection();
		Vector <Textbeitrag> t = new Vector <Textbeitrag>();
			try{
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT Organisationseinheit_Id, Strasse, Hausnummer, PLZ, Ort, Partnerprofil_Id"
						+ " FROM organisationseinheit " + "WHERE Partnerprofil_Id=" + nutzerId);
				
				
				while(rs.next()){
					// Ergebnis-Tupel in Objekt umwandeln
					Textbeitrag tb = new Textbeitrag();
					tb.setId(rs.getInt("Nutzer_Id"));
					tb.setText(rs.getString("Text"));
					tb.setErstellungszeitpunkt(rs.getDate("Erstellungszeitpunkt"));
					tb.setErsteller_Id(rs.getInt("Ersteller_Id"));
					
				t.add(tb);
				}
		}
			catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
			return t;
		}
	
	protected Textbeitrag findByObject(Textbeitrag t){
		return this.findTextbeitragById(t.getId()); 
	}
	
	
	
	protected int insert(Textbeitrag t) {
		Connection con = DBConnection.connection();
		 int id=0;
		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(Textbeitrag_Id) AS maxid " + "FROM textbeitrag");

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * n erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				t.setId(rs.getInt("maxid") + 1);
				id=t.getId();

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO textbeitrag (Textbeitrag_Id, Ersteller_Id, Text, Erstellungszeitpunkt) " + "VALUES (" + t.getId() + ",'"
						+ t.getErsteller_Id() + "','" + t.getText() + "','" + format.format(t.getErstellungszeitpunkt()) + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		return id;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param c
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	protected int update(Textbeitrag t) {
		Connection con = DBConnection.connection();
		int id=0;
		try {
			id = t.getId();
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("UPDATE textbeitrag " + "SET Text=\"" + t.getText() +  "WHERE id=" + t.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Um Analogie zu insert(Customer c) zu wahren, geben wir c zurück
		return id;
	}

	/**
	 * Löschen der Daten eines <code>Customer</code>-Objekts aus der Datenbank.
	 * 
	 * @param c
	 *            das aus der DB zu löschende "Objekt"
	 */
	protected void delete(Textbeitrag t) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM textbeitrag " + "WHERE Textbeitrag_Id=" + t.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
