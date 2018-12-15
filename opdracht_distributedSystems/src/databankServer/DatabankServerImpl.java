package databankServer;

import java.io.*;
import java.rmi.NotBoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import applicationServer.ActiveGame;
import applicationServer.Leaderbord;
import client.User;
import interfaces.DatabankServerInterface;
import memoryGame.Kaart;


public class DatabankServerImpl extends UnicastRemoteObject implements DatabankServerInterface {
	BCrypt bcrypt;
	List<DatabankServerInterface> databanken = new ArrayList<>();
	int id;
	int counter=0;

	List<ActiveGame> activeGames_updateQueue = new ArrayList<>();
	List<User> user_updateQueue = new ArrayList<>();

	public DatabankServerImpl(int id) throws RemoteException{
        this.id = id;
    }
	
	public Connection connect() {
	    // SQLite connection string
		File file = new File("databasepath.txt");
		String url = null;
		try {
			url=Files.readAllLines(Paths.get("databasepath.txt")).get(id);
			System.out.println(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Connection conn = null;
	    try {
	         conn = DriverManager.getConnection(url);
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    }
	    return conn;
	 }
	//---------------------Data Table Speler---------------------
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
		createLeaderbord(naam);

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
	
	//---------------------Data Table ActiveGame---------------------

	@Override
	public void createActiveGame(ActiveGame activeGame){
		String sql = "INSERT INTO ActiveGame(creator,numberPlayers,maxPlayers,size,theme,bord,omgedraaid) VALUES(?,?,?,?,?,?,?)";

        StringBuffer sb1=new StringBuffer();
        StringBuffer sb2=new StringBuffer();
        Kaart[][]matrix=activeGame.getGame().getBord().getMatrix();
        for(int i=0;i<activeGame.getSize();i++){
            for(int j=0;j<activeGame.getSize();j++){
                sb1.append(matrix[i][j].getWaarde()+" ");
                sb2.append("0 ");
            }
        }

		try (Connection conn = this.connect();
	               PreparedStatement pstmt = conn.prepareStatement(sql)) {
	           pstmt.setString(1, activeGame.getCreator());
	           pstmt.setInt(2, activeGame.getNumberPlayers());
	           pstmt.setInt(3, activeGame.getMaxPlayers());
	           pstmt.setInt(4,activeGame.getSize());
	           pstmt.setString(5,activeGame.getTheme());
	           pstmt.setString(6,sb1.toString());
	           pstmt.setString(7,sb2.toString());
	         
	           pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	

    }
	
	@Override
	public void removeActiveGame(String creator){
		String sql = "DELETE FROM ActiveGame WHERE creator=?";
		try (Connection conn = this.connect();
	               PreparedStatement pstmt = conn.prepareStatement(sql)) {
	          pstmt.setString(1, creator);
	         
	           pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	

    }
	
	//---------------------Data Table LeaderBord---------------------
	@Override
	public void increaseWin(String userName){
		String sql="UPDATE Leaderbord SET wins= wins+1 WHERE userName = ?";
		try (Connection conn = this.connect();
	               PreparedStatement pstmt = conn.prepareStatement(sql)) {
	           pstmt.setString(1, userName);
	           pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		
		
	}
	@Override
	public void createLeaderbord(String userName) {
		String sql = "INSERT INTO Leaderbord(userName,wins) VALUES(?,?)";
		try (Connection conn = this.connect();
	               PreparedStatement pstmt = conn.prepareStatement(sql)) {
	           pstmt.setString(1, userName);
	           pstmt.setInt(2,0);

	           pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	

    }

	@Override
	public List<Leaderbord> getAllLeaderbord() throws RemoteException {
        String sql = "SELECT * FROM LEADERBORD";
        List<Leaderbord>leaderbordlist=new ArrayList<Leaderbord>();
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                String userName=rs.getString("userName");
                int wins=rs.getInt("wins");
                
                Leaderbord leaderbord=new Leaderbord(userName,wins);
                leaderbordlist.add(leaderbord);
                                   
                                   
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return leaderbordlist;
	}
	
	//---------------------Data Table images---------------------
	@Override
	public List<byte[]> getImagesByTheme(String theme) throws RemoteException{
		List<byte[]> images=new ArrayList<>();
	      String sql = "SELECT image FROM images WHERE theme = ?";
	        List<Leaderbord>leaderbordlist=new ArrayList<Leaderbord>();
	        try (Connection conn = this.connect();
	        		PreparedStatement pstmt = conn.prepareStatement(sql)) {
		           	pstmt.setString(1, theme);
		           	ResultSet rs=pstmt.executeQuery();

	            
	            // loop through the result set
	            while (rs.next()) {
	               images.add(rs.getBytes("image"));

	                                   
	                                   
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return images;
		
	}

	@Override
	public void registreerdb(String ip, int poortnummer) throws RemoteException{
		try {
			Registry reg = LocateRegistry.getRegistry(ip, poortnummer);
			databanken.add((DatabankServerInterface) reg.lookup("DataBankService"));
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
    }

    @Override
    public String pingAnderen() throws RemoteException{
	    String result = " pingresult van anderen:\t";
        for(DatabankServerInterface di: databanken){
            result += di.pingResult();
            result += "\t";
        }
        return result;
    }

    @Override
    public String pingResult() throws RemoteException{
        return String.valueOf(id);
    }

    @Override
    public void verwerkQueues() throws RemoteException {
        for(DatabankServerInterface di: databanken){
            List<User> clients_te_verwerken = di.getUserQueue();
            List<ActiveGame> activeGames = di.getActivegames();

            for(User u : clients_te_verwerken) register(u.getNaam(), u.getWachtwoord());
            for(ActiveGame a : activeGames) createActiveGame(a);

            di.clearQueues();

            System.out.println("Server: " + id + " verwerkte queues van server: " + di.getid());
        }
    }

    @Override
    public List<User> getUserQueue() throws RemoteException {
        return user_updateQueue;
    }

    @Override
    public List<ActiveGame> getActivegames() throws RemoteException {
        return activeGames_updateQueue;
    }

    @Override
    public void clearQueues() throws RemoteException {
        user_updateQueue.clear();
        activeGames_updateQueue.clear();
    }

    @Override
    public String getid() throws RemoteException {
        return String.valueOf(id);
    }

	@Override
	public void aanmelden() throws RemoteException {
		counter++;
	}
	@Override
	public int getCount() throws RemoteException {
		return counter;
	}
	
	

}
