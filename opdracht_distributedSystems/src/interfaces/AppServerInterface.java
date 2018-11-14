package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import applicationServer.ActiveGame;

public interface AppServerInterface extends Remote{
	
	void register(String naam, String pwd) throws RemoteException;
	
	String login(String userName,String userPwd) throws RemoteException;
	
	boolean controleerUniekeNaam(String naam) throws RemoteException;
	
	void addActiveGame(ActiveGame activeGame) throws RemoteException;
	
	ArrayList<ActiveGame> getActiveGames() throws RemoteException;

	void removeActiveGame(ActiveGame activeGame) throws RemoteException;

	ActiveGame getActiveGame(String id) throws RemoteException;

	void increasePlayerCount(String gameId,boolean bit) throws RemoteException;
}
