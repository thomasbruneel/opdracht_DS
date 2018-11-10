package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DatabankServerInterface extends Remote{
	
	void register(String naam, String pwd) throws RemoteException;
	
	boolean login(String userName,String userPwd) throws RemoteException;
	
	boolean controleerUniekeNaam(String naam) throws RemoteException;

	void updateToken(String userName, String token) throws RemoteException;
}
