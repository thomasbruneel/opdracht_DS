package dispatcher;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import applicationServer.ActiveGame;
import interfaces.DatabankServerInterface;
import interfaces.DispatcherInterface;

public class DispathcherImpl extends UnicastRemoteObject implements DispatcherInterface {
	
	public DispathcherImpl() throws RemoteException{
		
		
	}

	



}
