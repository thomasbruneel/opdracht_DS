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
	
	public static List<AppServerInterface> asis;
	
	
	public DispathcherImpl(List<AppServer> appServers,List<DatabankServer> databankServers) throws RemoteException{
		this.appServers=appServers;
		this.databankServers=databankServers;
		asis=new ArrayList<>();
	
	}
	
	@Override
	public int getPortNumberAppServer() throws RemoteException {
		for (AppServerInterface asi:asis){
			System.out.println("aantal games op server "+asi.getActiveGames().size());
		}
		System.out.println("aantal games op laatste appserver "+asis.get(asis.size()-1).getActiveGames().size());
		if(asis.get(asis.size()-1).getActiveGames().size()>0){
			System.out.println("make new server");
			int newPortNumber=appServers.get(appServers.size()-1).getPoortnummer()+1;
			int newDbportNumber=appServers.get(appServers.size()-1).getDBportnummer();
			appServers.add(new AppServer("localhost", newPortNumber, newDbportNumber));
		}
		return appServers.get(asis.size()-1).getPoortnummer();
	}

	
	@Override
	public void addAsi(AppServerInterface asi) throws RemoteException {
		asis.add(asi);
	} 
	
	
	
	
	public static List<AppServer> getAppServers() {
		return appServers;
	}

	public static void setAppServers(List<AppServer> appServers) {
		DispathcherImpl.appServers = appServers;
	}

	public static List<DatabankServer> getDatabankServers() {
		return databankServers;
	}

	public static void setDatabankServers(List<DatabankServer> databankServers) {
		DispathcherImpl.databankServers = databankServers;
	}

	public static List<AppServerInterface> getAsis() {
		return asis;
	}

	public static void setAsis(List<AppServerInterface> asis) {
		DispathcherImpl.asis = asis;
	}



	

	
	
	
	

	



}
