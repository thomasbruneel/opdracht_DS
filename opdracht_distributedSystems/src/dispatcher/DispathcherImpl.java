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
import interfaces.AppServerInterface;
import interfaces.DatabankServerInterface;
import interfaces.DispatcherInterface;

public class DispathcherImpl extends UnicastRemoteObject implements DispatcherInterface,Serializable {
	
	public static List<AppServer> appServers;
	public static List<DatabankServer> databankServers;
	
	
	public DispathcherImpl(List<AppServer> appServers,List<DatabankServer> databankServers) throws RemoteException{
		this.appServers=appServers;
		this.databankServers=databankServers;
	
	}
	
	@Override
	public int getPortNumberAppServer() throws RemoteException {
		return appServers.get(appServers.size()-1).getPoortnummer();
	} 

	

	
	
	
	

	



}
