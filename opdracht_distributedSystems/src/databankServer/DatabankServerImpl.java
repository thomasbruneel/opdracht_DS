package databankServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import interfaces.DatabankServerInterface;


public class DatabankServerImpl extends UnicastRemoteObject implements DatabankServerInterface {
	BCrypt bcrypt;
	
	public DatabankServerImpl() throws RemoteException{

    }
	
	public Connection connect() {
	    // SQLite connection string
	    String url = "jdbc:sqlite:C:\\Users\\thoma\\OneDrive\\Documents\\GitHub\\opdracht_DS\\opdracht_distributedSystems\\database\\mijnDatabase.db";
	    Connection conn = null;
	    try {
	         conn = DriverManager.getConnection(url);
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    }
	    return conn;
	 }
	
	@Override
	public void register(String naam, String pwd) {
		String time=new Date().toString();
		String salt=bcrypt.gensalt();
		String sql = "INSERT INTO Speler(naam,pwd,salt,token,time) VALUES(?,?,?,?,?)";
		try (Connection conn = this.connect();
	               PreparedStatement pstmt = conn.prepareStatement(sql)) {
	           pstmt.setString(1, naam);
	           pstmt.setString(2, bcrypt.hashpw(pwd, salt));
	           pstmt.setString(3, salt);
	           pstmt.setString(4, "tmp token");
	           pstmt.setString(5, time);
	           pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	

    }
	
	@Override
	public boolean controleerUniekeNaam(String naam) {
		 String sql = "SELECT naam FROM Speler WHERE naam=?";
		 try (Connection conn = this.connect();
					PreparedStatement pstmt  = conn.prepareStatement(sql)){
					// set the value
					pstmt.setString(1,naam);
						
					ResultSet rs  = pstmt.executeQuery();
					String s = null;
					// loop through the result set
					while (rs.next()) {
						s=rs.getString("naam");
					}
					if(s==null){
						return true;
					}
					else {
						return false;
					}
				}catch (SQLException e) {
					System.out.println(e.getMessage());
				}
		return false;
	        
	}
	
	@Override
	public boolean login(String userName,String userPwd){
		String sql="SELECT pwd FROM Speler WHERE naam=?";
		try (Connection conn = this.connect();
			PreparedStatement pstmt  = conn.prepareStatement(sql)){
			// set the value
			pstmt.setString(1,userName);
				
			ResultSet rs  = pstmt.executeQuery();
			String pwd = null;
			// loop through the result set
			while (rs.next()) {
				pwd=rs.getString("pwd");
			}
			if(pwd==null){
				return false;
			}
			else if(bcrypt.checkpw(userPwd, pwd)){
				return true;
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	@Override
	public void updateToken(String userName,String token){
		System.out.println("token???????????");
		String sql="UPDATE Speler SET token= ? WHERE naam = ?";
		try (Connection conn = this.connect();
	               PreparedStatement pstmt = conn.prepareStatement(sql)) {
	           pstmt.setString(1, token);
	           pstmt.setString(2, userName);
	           pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		
		
	}

	public void selectAll(){
        String sql = "SELECT naam, pwd FROM Speler";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("naam") + "\t"+rs.getString("pwd")); 
                                   
                                   
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	
	
}
