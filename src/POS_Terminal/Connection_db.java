package POS_Terminal;

/*
 * U ovoj klasi koristim Singleton pattern da bih napravila samo jednu instancu klase kada mi zatreba konektovanje na bazu
 */

import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Connection_db { 
	
	private static Connection_db dbInstance; //static je da bi mogli globalno da koristimo promenljivu
	
	private Connection_db() { } //konstruktor je private da bi onemogucili kreiranje new Connection_db()
	
	public static Connection_db getInstance() {
		if(dbInstance==null) { //proveravamo da li vec imamo instancu
			dbInstance=new Connection_db(); //ukoliko nemamo kreiramo je
		}
		return dbInstance; //ukoliko postoji vracamo opet tu instancu
	}
	
	public Connection getConnection() { //uspostavljanje konekcije za bazu mysql koriscenjem jdbc Driver-a
			try {
				Class.forName("com.mysql.jdbc.Driver");
				return (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/terminal?autoReconnect=true&useSSL=false", "root", "root");
			} catch (Exception ex) {
				System.out.println("Greska: "+ex.getMessage());
				throw new RuntimeException(ex);
			}
	}
	public Statement getStatement() throws SQLException { //kreiramo Statement
		return (Statement) getInstance().getConnection().createStatement();
	}
}
