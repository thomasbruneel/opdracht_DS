package dispatcher;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import interfaces.DatabankServerInterface;

public class DispathcherImpl extends UnicastRemoteObject implements DatabankServerInterface {
	
	public DispathcherImpl() throws RemoteException{
		
		
	}

	@Override
	public void spelerToevoegen(String naam, String pwd) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkPwd(String userName, String userPwd) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean controleerUniekeNaam(String naam) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
