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

	public static BeitragMapper beitragMapper() {
		if (beitragMapper == null) {
			beitragMapper = new BeitragMapper();
		}

		return beitragMapper;
	}
	
	public Beitrag findBeitragById(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT Beitrag_Id, Pinnwand_Id FROM beitrag " + "WHERE Beitrag_Id=" + id);

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Beitrag b = new Beitrag();
				b.setId(rs.getInt("Beitrag_Id"));
				b.setPinnwand_Id(rs.getInt("Pinnwand_Id"));
				b.setErstellungszeitpunkt(super.findTextbeitragById(id).getErstellungszeitpunkt());
				b.setErsteller_Id(super.findTextbeitragById(id).getErsteller_Id());
				b.setText(super.findTextbeitragById(id).getText());
				

				return b;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}
	
	public Vector <Beitrag> findBeitragByPinnwand(int kommentarId){
		
		Connection con = DBConnection.connection();
		Vector <Beitrag> b = new Vector <Beitrag>();
			try{
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT Beitrag_Id, Pinnwand_Id FROM beitrag " + "WHERE Beitrag_Id=" + kommentarId);
				
				
				while(rs.next()){
					// Ergebnis-Tupel in Objekt umwandeln
					Beitrag be = new Beitrag();
					be.setId(rs.getInt("Beitrag_Id"));
					be.setPinnwand_Id(rs.getInt("Pinnwand_Id"));
					be.setErstellungszeitpunkt(super.findTextbeitragById(kommentarId).getErstellungszeitpunkt());
					be.setErsteller_Id(super.findTextbeitragById(kommentarId).getErsteller_Id());
					be.setText(super.findTextbeitragById(kommentarId).getText());
					
				b.add(be);
				}
		}
			catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
			return b;
		}
	
	
	public Beitrag findByObject(Beitrag b){
		return this.findBeitragById(b.getId()); 
	}
	
	
	
	public Beitrag insert(Beitrag b) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			
			 b.setId(super.insert(b));
			

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO beitrag (Beitrag_Id, Pinnwand_Id) " + "VALUES (" + b.getId() + ",'"
						+ b.getPinnwand_Id() + "')");
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
		return b;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param c
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Beitrag update(Beitrag b) {
		//Connection con = DBConnection.connection();
		 b.setId(super.update(b));
	     super.textbeitragMapper().update(b);

		
		// Um Analogie zu insert(Customer c) zu wahren, geben wir c zurück
		return b;
	}

	/**
	 * Löschen der Daten eines <code>Customer</code>-Objekts aus der Datenbank.
	 * 
	 * @param c
	 *            das aus der DB zu löschende "Objekt"
	 */
	public void delete(Beitrag b) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM beitrag " + "WHERE Beitrag_Id=" + b.getId());
			super.delete(b);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
