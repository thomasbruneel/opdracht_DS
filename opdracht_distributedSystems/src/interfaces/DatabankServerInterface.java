package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import applicationServer.ActiveGame;

public interface DatabankServerInterface extends Remote{
	
	void register(String naam, String pwd) throws RemoteException;
	
	boolean login(String userName,String userPwd) throws RemoteException;
	
	boolean controleerUniekeNaam(String naam) throws RemoteException;

	void updateToken(String userName, String token) throws RemoteException;

	void createActiveGame(ActiveGame activeGame, String string, String string2)throws RemoteException;

	void removeActiveGame(String creator)throws RemoteException;


}
