package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatenbankVerbindung {
	//private oConnection;
	private String sAddress;
	private int iPort;
	Connection conn;
	//ha siehe Diagramm
	/*public void connect(mysqlConnection) {
		
	}*/
	String url = "jdbc:mysql://192.168.178.21:3306/ik_scooter";
	String user = "nils312";
	String password = "admin";
	
	public void connect() {
		try  {
			conn = DriverManager.getConnection(url, user, password);
		} catch(SQLException ex) {
			
			System.err.println(ex.getMessage());
			
		}
	}
	public void disconnect () {
	}
	
	public String[] sendQuerry(String sQuerry) throws SQLException {
		String query = sQuerry;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		stmt.close();
		int columns = rs.getMetaData().getColumnCount();
		String[] rsString = new String[columns];
		while(rs.next()) {
			for(int i=1;i<=columns;i++) {
				rsString[i] = rs.getString(i);
			}
		}
		return rsString;
	}
	
	public void sendQuerryNoReturn(String sQuerry) throws SQLException {
		String query = sQuerry;
		Statement stmt = conn.createStatement();
		stmt.execute(query);
		stmt.close();
	}
}
