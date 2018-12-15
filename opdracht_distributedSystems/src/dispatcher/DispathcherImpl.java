package dispatcher;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import Constanten.Constanten;
import applicationServer.ActiveGame;
import applicationServer.AppServerMain;
import interfaces.AppServerInterface;
import interfaces.DatabankServerInterface;
import interfaces.DispatcherInterface;

public class DispathcherImpl extends UnicastRemoteObject implements DispatcherInterface,Serializable {
	
	public List<AppServer> appServers;
	public List<DatabankServer> databankServers;
	
	public List<AppServerInterface> asis;

	int dbCounter;
	int appCounter;

	
	public DispathcherImpl(List<AppServer> appServers,List<DatabankServer> databankServers, int dBcounter, int appCounter) throws RemoteException{
		this.appServers=appServers;
		this.databankServers=databankServers;
		asis=new ArrayList<>();
		this.dbCounter = dBcounter;
		this.appCounter = appCounter;
	}
	
	@Override
	public int getPortNumberAppServer() throws RemoteException, NotBoundException {
		for (AppServerInterface asi:asis){
			System.out.println("aantal games op server "+asi.getActiveGames().size());  //for debugging
		}

		AppServerInterface a = asis.get(0);
        int leastGames = a.getActiveGamessize();
        if (asis.size() > 1) {
            for (AppServerInterface aa : asis.subList(1, asis.size() - 1)) {
                if (aa.getActiveGamessize()<leastGames){
                    leastGames = aa.getActiveGamessize();
                    a = aa;
                }
            }
        }

		if(leastGames>=Constanten.ACTIVEGAMES_PER_APPSERVER){
			System.out.println("make new server");
			int newPortNumber= Constanten.APPSERVER_POORTRANGE_START+appCounter;
			appCounter++;
			int newDbportNumber=findDBport();  //TODO: loadbalancing
			appServers.add(new AppServer("localhost", newPortNumber, newDbportNumber));
			
			String[] args = new String[2];
            args[0] = String.valueOf(newPortNumber);
            args[1] = String.valueOf(newDbportNumber);
            AppServerMain.main(args);
            
            Registry reg = LocateRegistry.getRegistry("localhost", newPortNumber);
			asis.add((AppServerInterface) reg.lookup("AppService"));
            return appServers.get(asis.size()-1).getPoortnummer();
		} else {
		    return a.getServerid();
        }

	}
	
	private int findDBport() throws RemoteException, NotBoundException {	//loadbalancing
		int dbport=4000;
		int max=Integer.MAX_VALUE;
		for(DatabankServer ds:databankServers){
			Registry reg = LocateRegistry.getRegistry(ds.getIpAdres(), ds.getPoortnummer());
	        DatabankServerInterface dsimpl = (DatabankServerInterface) reg.lookup("DataBankService");
	        if(dsimpl.getCount()<max){
	        	max=dsimpl.getCount();
	        	dbport=ds.getPoortnummer();
	        }
	        
		}
	
		return dbport;
	}

	@Override
	public void addAsi(AppServerInterface asi) throws RemoteException {
		asis.add(asi);
	}

    @Override
    public AppServerInterface requestAppserver(int appServerId) throws RemoteException {
        for(AppServerInterface a : asis){
            if(a.getServerid() == appServerId){
                return a;
            }
        }
        return null;
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
