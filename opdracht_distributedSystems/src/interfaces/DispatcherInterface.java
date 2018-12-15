package interfaces;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DispatcherInterface extends Remote{
	

	int getPortNumberAppServer() throws RemoteException, NotBoundException;

	void addAsi(AppServerInterface asi) throws RemoteException;

    AppServerInterface requestAppserver(int appServerId) throws RemoteException;
}
