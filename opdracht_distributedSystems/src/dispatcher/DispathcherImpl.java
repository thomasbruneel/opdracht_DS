package dispatcher;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import applicationServer.ActiveGame;
import interfaces.DatabankServerInterface;
import interfaces.DispatcherInterface;

public class DispathcherImpl extends UnicastRemoteObject implements DispatcherInterface,Serializable {
	
	private List<AppServer> appServers;
	private List<DatabankServer> databankServers;
	
	public DispathcherImpl() throws RemoteException{
		appServers=new ArrayList<>();
		databankServers=new ArrayList<>();
	}

	



}
