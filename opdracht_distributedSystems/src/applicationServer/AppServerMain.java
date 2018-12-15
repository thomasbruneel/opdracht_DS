package applicationServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import databankServer.DatabankServerImpl;

public class AppServerMain {
public static void main(String[] args) {
		
		try{
			System.out.println("appservermain started with portnumber... "+args[0]);
			Registry appRegistry=LocateRegistry.createRegistry(Integer.parseInt(args[0]));
			appRegistry.rebind("AppService", new AppServerImpl(Integer.parseInt(args[1]),Integer.parseInt(args[0])));
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
}
