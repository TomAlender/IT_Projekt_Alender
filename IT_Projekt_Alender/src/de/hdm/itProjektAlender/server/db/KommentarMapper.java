package de.hdm.itProjektAlender.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;


import de.hdm.itProjektAlender.shared.bo.*;



public class KommentarMapper extends TextbeitragMapper {
	
	private static KommentarMapper kommentarMapper = null;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd k:mm:s");
	
	protected KommentarMapper(){
		
	}

	public static KommentarMapper kommentarMapper() {
		if (kommentarMapper == null) {
			kommentarMapper = new KommentarMapper();
		}

		return kommentarMapper;
	}
	
	public Kommentar findKommentarById(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT Kommentar_Id, Beitrag_Id FROM kommentar " + "WHERE Kommentar_Id=" + id);

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Kommentar k = new Kommentar();
				k.setId(rs.getInt("Kommentar_Id"));
				k.setBeitrag_Id(rs.getInt("Beitrg_Id"));
				k.setErstellungszeitpunkt(super.findTextbeitragById(id).getErstellungszeitpunkt());
				k.setErsteller_Id(super.findTextbeitragById(id).getErsteller_Id());
				k.setText(super.findTextbeitragById(id).getText());
				

				return k;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}
	
	public Kommentar findByObject(Kommentar k){
		return this.findKommentarById(k.getId()); 
	}
	
	public Vector <Kommentar> findKommentarByBeitrag(int beitragId){
		
		Connection con = DBConnection.connection();
		Vector <Kommentar> k = new Vector <Kommentar>();
			try{
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT Kommentar_Id, Beitrag_Id FROM kommentar " + "WHERE Beitrag_Id=" + beitragId);
				
				
				while(rs.next()){
					// Ergebnis-Tupel in Objekt umwandeln
					Kommentar ko = new Kommentar();
					ko.setId(rs.getInt("Kommentar_Id"));
					ko.setBeitrag_Id(rs.getInt("Beitrg_Id"));
					ko.setErstellungszeitpunkt(super.findTextbeitragById(beitragId).getErstellungszeitpunkt());
					ko.setErsteller_Id(super.findTextbeitragById(beitragId).getErsteller_Id());
					ko.setText(super.findTextbeitragById(beitragId).getText());
					
				k.add(ko);
				}
		}
			catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
			return k;
		}
	
	public Kommentar insert(Kommentar k) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			
			 k.setId(super.insert(k));
			

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO beitrag (Kommentar_Id, Beitrag_Id) " + "VALUES (" + k.getId() + ",'"
						+ k.getBeitrag_Id() + "')");
			}
		 catch (SQLException e) {
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
		return k;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param c
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Kommentar update(Kommentar k) {
		//Connection con = DBConnection.connection();
		 k.setId(super.update(k));
	     super.textbeitragMapper().update(k);

		
		// Um Analogie zu insert(Customer c) zu wahren, geben wir c zurück
		return k;
	}

	/**
	 * Löschen der Daten eines <code>Customer</code>-Objekts aus der Datenbank.
	 * 
	 * @param c
	 *            das aus der DB zu löschende "Objekt"
	 */
	public void delete(Kommentar k) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM kommentar " + "WHERE Kommentar_Id=" + k.getId());
			super.delete(k);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
