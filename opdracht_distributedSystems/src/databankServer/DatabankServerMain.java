package databankServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabankServerMain {
	
	
	private Connection connect() {
	        // SQLite connection string
	        String url = "jdbc:sqlite:C:\\Users\\thoma\\Desktop\\MASTER\\sem1\\gedistrbueerde systemen\\OPDRACHT\\opdracht_distributedSystems\\database\\mijnDatabase.db";
	        Connection conn = null;
	        try {
	            conn = DriverManager.getConnection(url);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return conn;
	 }
	
	public void spelerToevoegen(String naam, String pwd) {
		
        String sql = "INSERT INTO Speler(naam,pwd) VALUES(?,?)";
 
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, naam);
            pstmt.setString(2, pwd);
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
	
	public boolean checkPwd(String userName,String userPwd){
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
			if(pwd.equals(userPwd)){
				return true;
			}
			else{
				return false;
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	

	  
	public static void main(String[] args) {
		DatabankServerMain db = new DatabankServerMain();
		db.selectAll();
		boolean oke=db.checkPwd("thomas","bruneel");
		System.out.println(oke);
		boolean noke=db.checkPwd("thomas","thththtrerer");
		System.out.println(noke);
    }
	
}
