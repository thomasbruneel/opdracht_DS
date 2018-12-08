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
	
	private List<AppServer> appServers;
	private List<DatabankServer> databankServers;
	
	public DispathcherImpl() throws RemoteException{
		appServers=new ArrayList<>();
		databankServers=new ArrayList<>();
	}
	
	
	public void registerDatabaseServer(int port,int id) throws RemoteException{
		Registry registry=LocateRegistry.getRegistry("localhost",port);
		try {
			DatabankServerInterface dsi=(DatabankServerInterface) registry.lookup("DataBankService");
			DatabankServer dbserver=new DatabankServer(id,"localhost",port,dsi);
			databankServers.add(dbserver);
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void registerAppServer(int port,int id) throws RemoteException{
		Registry registry=LocateRegistry.getRegistry("localhost",port);
		try {
			AppServerInterface asi=(AppServerInterface) registry.lookup("DataBankService");
			AppServer appserver=new AppServer(id,"localhost",port,asi);
			appServers.add(appserver);
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	
	
	
	

	



}
