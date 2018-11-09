package applicationServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import databankServer.DatabankServerImpl;

public class AppServerMain {
public static void main(String[] args) {
		
		try{
			System.out.println("appserver started...");
			Registry appRegistry=LocateRegistry.createRegistry(1001);
			appRegistry.rebind("AppService", new AppServerImpl());
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
}
