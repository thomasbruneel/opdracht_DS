package databankServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DatabankServerMain {

	public static void main(String[] args) {
		
		try{
			System.out.println("dataserver started...");
			Registry dataBankRegistry=LocateRegistry.createRegistry(2222);
			//dataBankRegistry.rebind("DataBankService", new DatabankServerImpl());
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

}
