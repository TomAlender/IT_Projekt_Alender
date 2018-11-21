package de.hdm.itProjektAlender.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;

import de.hdm.itProjektAlender.shared.bo.Like;



public class LikeMapper {

private static LikeMapper likeMapper = null;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd k:mm:s");
	
	protected LikeMapper(){
		
	}

	public static LikeMapper likeMapper() {
		if (likeMapper == null) {
			likeMapper = new LikeMapper();
		}

		return likeMapper;
	}
	
	public Like findLikeById(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT Like_Id, Ersteller_Id, Beitrag_Id, Erstellungszeitpunkt FROM like1 " + "WHERE Like_Id=" + id);

			/*
			 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
			 * werden. Prüfe, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln
				Like l = new Like();
				l.setId(rs.getInt("Pinnwand_Id"));
				l.setErstellerId(rs.getInt("Ersteller_Id"));
				l.setBeitrag_Id(rs.getInt("Beitrag_Id"));
				l.setErstellungszeitpunkt(rs.getDate("Erstellungszeitpunkt"));
				
				return l;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}
	
	protected Like findByObject(Like l){
		return this.findLikeById(l.getId()); 
	}
	
	public Vector<Like> findLikeByNutzer(int nutzerId){
		
		// DB-Verbindung holen
				Connection con = DBConnection.connection();
				Vector <Like> l = new Vector<Like>();
				try {
					// Leeres SQL-Statement (JDBC) anlegen
					Statement stmt = con.createStatement();
					
					// Statement ausfüllen und als Query an die DB schicken
					ResultSet rs = stmt.executeQuery(
							"SELECT *  FROM like1 " + "WHERE Ersteller_Id=" + nutzerId);

					/*
					 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
					 * werden. Prüfe, ob ein Ergebnis vorliegt.
					 */
					while (rs.next()) {
						// Ergebnis-Tupel in Objekt umwandeln
						Like li = new Like();
						li.setId(rs.getInt("Like_Id"));
						li.setErstellerId(rs.getInt("Ersteller_Id"));
						li.setBeitrag_Id(rs.getInt("Beitrag_Id"));
						li.setErstellungszeitpunkt(rs.getTimestamp("Erstellungszeitpunkt"));
						
						l.add(li);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}

				return l;
	
	}
	
	public Vector <Like> findLikeByBeitrag(int beitragId){
		
		// DB-Verbindung holen
				Connection con = DBConnection.connection();
				Vector <Like> l = new Vector<Like>();
				try {
					// Leeres SQL-Statement (JDBC) anlegen
					Statement stmt = con.createStatement();

					// Statement ausfüllen und als Query an die DB schicken
					ResultSet rs = stmt.executeQuery(
							"SELECT Like_Id, Ersteller_Id, Beitrag_Id, Erstellungszeitpunkt FROM like1 " + "WHERE Beitrag_Id=" + beitragId);

					/*
					 * Da id Primärschlüssel ist, kann max. nur ein Tupel zurückgegeben
					 * werden. Prüfe, ob ein Ergebnis vorliegt.
					 */
					while (rs.next()) {
						// Ergebnis-Tupel in Objekt umwandeln
						Like li = new Like();
						li.setId(rs.getInt("Like_Id"));
						li.setErstellerId(rs.getInt("Ersteller_Id"));
						li.setBeitrag_Id(rs.getInt("Beitrag_Id"));
						li.setErstellungszeitpunkt(rs.getDate("Erstellungszeitpunkt"));
						
						l.add(li);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}

				return l;
	
	}
	
	public Like insert(Like l) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunächst schauen wir nach, welches der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(Like_Id) AS maxid " + "FROM like1 ");

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * n erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				l.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsächliche Einfügeoperation
				stmt.executeUpdate("INSERT INTO like1 (Like_Id, Ersteller_Id, Beitrag_Id, Erstellungszeitpunkt) " + "VALUES (" + l.getId() + ",'"
						+ l.getErstellerId() + "','" + l.getBeitrag_Id() + "','" + format.format(l.getErstellungszeitpunkt()) + "')");
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
		return l;
	}

	
	/**
	 * Löschen der Daten eines <code>Customer</code>-Objekts aus der Datenbank.
	 * 
	 * @param c
	 *            das aus der DB zu löschende "Objekt"
	 */
	public void delete(Like l) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM like1 " + "WHERE Like_Id=" + l.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	

}
