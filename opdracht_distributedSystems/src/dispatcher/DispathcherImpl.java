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
	
	public List<AppServer> appServers;
	public List<DatabankServer> databankServers;
	
	public List<AppServerInterface> asis;
	
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
		return appServers.get(appServers.size()-1).getPoortnummer();
	}
	
	@Override
	public void addAsi(AppServerInterface asi) throws RemoteException {
		asis.add(asi);
	}
	
	public List<AppServer> getAppServers() {
		return appServers;
	}

	public void setAppServers(List<AppServer> appServers) {
		this.appServers = appServers;
	}

	public List<DatabankServer> getDatabankServers() {
		return databankServers;
	}

	public void setDatabankServers(List<DatabankServer> databankServers) {
		this.databankServers = databankServers;
	}

	public List<AppServerInterface> getAsis() {
		return asis;
	}

	public void setAsis(List<AppServerInterface> asis) {
		this.asis = asis;
	}

}
