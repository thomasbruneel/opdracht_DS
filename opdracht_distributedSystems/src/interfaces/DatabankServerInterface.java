package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import applicationServer.ActiveGame;
import applicationServer.Leaderbord;

public interface DatabankServerInterface extends Remote{
	
	void register(String naam, String pwd) throws RemoteException;
	
	boolean login(String userName,String userPwd) throws RemoteException;
	
	boolean controleerUniekeNaam(String naam) throws RemoteException;

	void updateToken(String userName, String token) throws RemoteException;

	void createActiveGame(ActiveGame activeGame, String string, String string2)throws RemoteException;

	void removeActiveGame(String creator)throws RemoteException;

	void increaseWin(String userName) throws RemoteException;

	void createLeaderbord(String userName) throws RemoteException;
	
	List<Leaderbord> getAllLeaderbord() throws RemoteException;


}
