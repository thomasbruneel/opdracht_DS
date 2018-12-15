package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DispatcherInterface extends Remote{
	

	int getPortNumberAppServer() throws RemoteException;

	void addAsi(AppServerInterface asi) throws RemoteException;
	

}
