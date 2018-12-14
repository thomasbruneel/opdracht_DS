package dispatcher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import applicationServer.AppServerImpl;
import databankServer.DatabankServerImpl;
import interfaces.DispatcherInterface;

public class Dispatcher {
	public static List<AppServer> appServers;
	public static List<DatabankServer> databankServers;
	
	
	
	
	public static void initDispatcher(){
		createDBservers();
		createAppServer();
		
		try{
			System.out.println("dispatcher started...");
			Registry dispatcherRegistry=LocateRegistry.createRegistry(9999);
			dispatcherRegistry.rebind("DispathcerService", new DispathcherImpl());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		startDBservers();
	}



	private static void createDBservers() {
		databankServers=new ArrayList<>();
		for(int i=0;i<3;i++){
			databankServers.add(new DatabankServer("localhost", 4000+i));
		}
		
	}
	
	private static void createAppServer() {
		appServers=new ArrayList<>();
		appServers.add(new AppServer("localhost",5000,databankServers.get(0)));
		
	}

	private static void startDBservers() {
		for(DatabankServer ds:databankServers){
			try{
				
				Registry dataBankRegistry=LocateRegistry.createRegistry(ds.getPoortnummer());
				dataBankRegistry.rebind("DataBankService", new DatabankServerImpl());
				System.out.println("dataserver started with portnumber "+ds.getPoortnummer());
			}
			catch(Exception e){
				e.printStackTrace();
			}
			ds.setOnline(true);
		}
	
		
	}



	public static List<AppServer> getAppServers() {
		return appServers;
	}



	public static void setAppServers(List<AppServer> appServers) {
		Dispatcher.appServers = appServers;
	}



	public static List<DatabankServer> getDatabankServers() {
		return databankServers;
	}



	public static void setDatabankServers(List<DatabankServer> databankServers) {
		Dispatcher.databankServers = databankServers;
	}
	
	

}
