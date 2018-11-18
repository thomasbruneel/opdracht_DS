package applicationServer;


import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import interfaces.AppServerInterface;
import interfaces.DatabankServerInterface;
import memoryGame.Kaart;

public class AppServerImpl extends UnicastRemoteObject implements AppServerInterface{
	private DatabankServerInterface dsi;
	
	private ArrayList<ActiveGame>activeGames;
	
	public AppServerImpl() throws RemoteException{
		activeGames=new ArrayList<ActiveGame>();
		Registry registry=LocateRegistry.getRegistry("localhost",2222);
		try {
			dsi=(DatabankServerInterface) registry.lookup("DataBankService");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void register(String naam, String pwd) throws RemoteException {
		dsi.register(naam, pwd);
		
	}

	@Override
	public String login(String userName, String userPwd) throws RemoteException {	//geeft token terug als username en pass overeenkomt
		if(dsi.login(userName, userPwd)){
			String token=TokenGenerator.generate(userName);
			dsi.updateToken(userName,token);
			return token;
			
		}
		else{
			return null;
		}
		
	}

	@Override
	public boolean controleerUniekeNaam(String naam) throws RemoteException {
		return dsi.controleerUniekeNaam(naam);
	}

	@Override
	public void addActiveGame(ActiveGame activeGame) throws RemoteException {
		activeGames.add(activeGame);
		StringBuffer sb1=new StringBuffer();
		StringBuffer sb2=new StringBuffer();
		Kaart[][]matrix=activeGame.getGame().getBord().getMatrix();
		for(int i=0;i<activeGame.getSize();i++){
			for(int j=0;j<activeGame.getSize();j++){
				sb1.append(matrix[i][j].getWaarde()+" ");
				sb2.append("0 ");
			}
		}
		dsi.createActiveGame(activeGame,sb1.toString(),sb2.toString());
		
	}
	
	@Override
	public void removeActiveGame(ActiveGame activeGame) throws RemoteException {
		ActiveGame tmp = null;
		for(ActiveGame ag:activeGames){
			if(ag.getCreator().equals(activeGame.getCreator())){
				tmp=ag;
			}
		}
		dsi.removeActiveGame(tmp.getCreator());
		activeGames.remove(tmp);
		

		
	}
	@Override
	public  ArrayList<ActiveGame> getActiveGames() throws RemoteException {
		return activeGames;
	}
	
	@Override
	public ActiveGame getActiveGame(String id)throws RemoteException {
		System.out.println("Opgeroepen");
		ActiveGame activeGame=null;
    	for(ActiveGame ag:activeGames){
    		if(ag.getCreator().equals(id)){
    			activeGame=ag;
    		}
    	}
		return activeGame;
		
	}

	@Override
	public void increasePlayerCount(String gameId,boolean bit) throws RemoteException{
		ActiveGame activeGame=null;
    	for(ActiveGame ag:activeGames){
    		if(ag.getCreator().equals(gameId)){
    			ag.increasePlayerCount(bit);
    		}
    	}
		
	}

	@Override
	public void removeactiveGameById(String creator) throws RemoteException {
		ActiveGame tmp = null;
		for(ActiveGame ag:activeGames){
			if(ag.getCreator().equals(creator)){
				tmp=ag;
			}
		}
		if(tmp!=null){
			dsi.removeActiveGame(tmp.getCreator());
			activeGames.remove(tmp);
			
		}
		
		
	}
	
	@Override
	public void flipCard(String creator,int x,int y)throws RemoteException{
		ActiveGame activeGame=null;
    	for(ActiveGame ag:activeGames){
    		if(ag.getCreator().equals(creator)){
    			activeGame=ag;
    		}
    	}
    	if(activeGame!=null){
    		activeGame.getGame().getBord().flipCard(x,y);
    	}

		System.out.println(creator + ": " + x + ", " +y);

	}

	@Override
	public void addPlayer(String gameId,String player)throws RemoteException{
		ActiveGame activeGame=null;
    	for(ActiveGame ag:activeGames){
    		if(ag.getCreator().equals(gameId)){
    			activeGame=ag;
    		}
    	}
    	activeGame.addPlayer(player);
    	

	}

	@Override
	public void removePlayer(String gameID, String userName) throws RemoteException {
		ActiveGame tmpActiveGame=null;
    	for(ActiveGame ag:activeGames){
    		if(ag.getCreator().equals(gameID)){
    			tmpActiveGame=ag;
    		}
    	}
    	System.out.println("spelers size voor remove "+tmpActiveGame.getSpelers().size()+ " --> "+tmpActiveGame.getSpelers());
    	String tmp=null;
    	for(String s:tmpActiveGame.getSpelers()){
    		if(s.equals(userName)){
    			tmp=s;
    		}
    	}
    	tmpActiveGame.getSpelers().remove(tmp);
    	System.out.println("spelers size na remove "+tmpActiveGame.getSpelers().size()+ " --> "+tmpActiveGame.getSpelers());
    }
    
	
	
	
}
	
	
	
	




