package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DispatcherInterface extends Remote{
	
	void spelerToevoegen(String naam, String pwd) throws RemoteException;
	
	boolean checkPwd(String userName,String userPwd) throws RemoteException;
	
	boolean controleerUniekeNaam(String naam) throws RemoteException;
}
