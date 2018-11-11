package applicationServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServerMain {

	public static void main(String[] args) {
		
		try {
			Registry appRegistry = LocateRegistry.createRegistry(1001);
			appRegistry.rebind("AppServerService", new ApplicationServerImpl());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		

	}

}
