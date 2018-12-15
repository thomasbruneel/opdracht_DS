package dispatcher;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import Constanten.Constanten;
import applicationServer.AppServerImpl;
import databankServer.DatabankServerImpl;
import interfaces.AppServerInterface;
import interfaces.DatabankServerInterface;
import interfaces.DispatcherInterface;

public class Dispatcher {
	public static List<AppServer> appServers;
	public static List<DatabankServer> databankServers;
	static int dBcounter = 0;
	int appcounter = 0;
	
	public static DispathcherImpl dispathcherImpl;
	
	public static List<AppServerInterface> asis=new ArrayList<>();
	
	
	
	
	public static void initDispatcher() throws Exception{
		

		createDBservers();
		createAppServer();
		dispathcherImpl=new DispathcherImpl(appServers, databankServers);
		try{
			System.out.println("dispatcher started...");
			Registry dispatcherRegistry=LocateRegistry.createRegistry(9999);
			dispatcherRegistry.rebind("DispathcerService", dispathcherImpl);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		startDBservers();
		startAppServer(appServers.get(0));
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
		int id=0;
		for(DatabankServer ds:databankServers){
			try{
				
				Registry dataBankRegistry=LocateRegistry.createRegistry(ds.getPoortnummer());
				dataBankRegistry.rebind("DataBankService", new DatabankServerImpl(dBcounter++));
				System.out.println("dataserver started with portnumber "+ds.getPoortnummer());
			}
			catch(Exception e){
				e.printStackTrace();
			}
			ds.setOnline(true);
			id++;
		}

        for(DatabankServer ds : databankServers){
            try {
                Registry reg = LocateRegistry.getRegistry(ds.getIpAdres(), ds.getPoortnummer());
                DatabankServerInterface dsimpl = (DatabankServerInterface) reg.lookup("DataBankService");
			    for(DatabankServer d: databankServers){
    				if(ds != d) {
				        dsimpl.registreerdb(d.getIpAdres(), d.getPoortnummer());
                    }
			    }
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        }

        for(DatabankServer ds : databankServers) {
            Registry reg = null;
            try {
                reg = LocateRegistry.getRegistry(ds.getIpAdres(), ds.getPoortnummer());
                DatabankServerInterface dsimpl = (DatabankServerInterface) reg.lookup("DataBankService");
                dsimpl.verwerkQueues();

            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        }
		
	}
	

	private static void startAppServer(AppServer appServer) throws Exception {
		try{
			
			Registry appRegistry=LocateRegistry.createRegistry(appServer.getPoortnummer());
			appRegistry.rebind("AppService", new AppServerImpl(appServer.getDBportnummer()));
			System.out.println("appserver started with portnumber "+appServer.getPoortnummer()+" connected with dbportnumber "+appServer.getDBportnummer());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			Registry reg = LocateRegistry.getRegistry(appServer.getIpAdres(), appServer.getPoortnummer());
			asis.add((AppServerInterface) reg.lookup("AppService"));
			
			dispathcherImpl.addAsi((AppServerInterface) reg.lookup("AppService"));
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
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
