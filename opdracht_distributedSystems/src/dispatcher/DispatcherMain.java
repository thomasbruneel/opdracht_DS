package dispatcher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import applicationServer.AppServerImpl;

public class DispatcherMain {
	public static void main(String[]args){
		try{
			System.out.println("dispatcher started...");
			Registry dispatcherRegistry=LocateRegistry.createRegistry(3333);
			dispatcherRegistry.rebind("DispathcerService", new DispathcherImpl());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
