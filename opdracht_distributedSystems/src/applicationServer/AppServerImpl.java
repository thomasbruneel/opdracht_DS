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
		
	}
	
	@Override
	public void removeActiveGame(ActiveGame activeGame) throws RemoteException {
		ActiveGame tmp = null;
		for(ActiveGame ag:activeGames){
			if(ag.getCreator().equals(activeGame.getCreator())){
				tmp=ag;
			}
		}
		activeGames.remove(tmp);
		System.out.println(activeGames.size());
		
	}
	@Override
	public  ArrayList<ActiveGame> getActiveGames() throws RemoteException {
		return activeGames;
	}

	
	
	
	
	



}
