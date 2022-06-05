package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatenbankVerbindung {

	private String sAddress;
	private int    iPort;
	private String sDatabase;
	private String user;
	private String password;
	
	Connection     conn;
	
	public DatenbankVerbindung() {
		this.sAddress  = "192.168.178.21";
		this.iPort     = 3306;
		this.sDatabase = "ik_scooter";
		
		
		
		this.user     = "scooter";
		this.password = "scooter";
	}
	
	public boolean connect() {
		try  {
			String url = String.format("jdbc:mysql://%s:%d/%s", this.sAddress, this.iPort, this.sDatabase);
			conn = DriverManager.getConnection(url, user, password);
		} catch(SQLException ex) {
			System.err.println(ex.getMessage());
			return false;
		}
		return true;
	}
	
	public void disconnect () {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Statement createStatement() throws SQLException {
		return conn.createStatement();
	}
	
	public String[] sendQuerry(String sQuerry) throws SQLException {
		String query = sQuerry;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		int columns = rs.getMetaData().getColumnCount();
		String[] rsString = new String[columns];
		while(rs.next()) {
			for(int i=0;i<columns;i++) {
				rsString[i] = rs.getString(i+1);
			}
		}
		stmt.close();
		return rsString;
	}
	
	public void sendQuerryNoReturn(String sQuerry) throws SQLException {
		String query = sQuerry;
		Statement stmt = conn.createStatement();
		stmt.execute(query);
		stmt.close();
	}
	
	public void setServer(String sServer) {
		this.sAddress = sServer;
	}
	
	public void setPort(int iPort) {
		this.iPort = iPort;
	}
	
	public void setDatabase(String sDatabase) {
		this.sDatabase = sDatabase;
	}
	
	public void setUser(String sUser) {
		this.user = sUser;
	}
	
	public void setPassword(String sPassword) {
		this.password = sPassword;
	}

	public String getServer() {
		return this.sAddress;
	}
	
	public int getPort() {
		return this.iPort;
	}
	
	public String getDatabase() {
		return this.sDatabase;
	}
	
	public String getUser() {
		return this.user;
	}
	
}
