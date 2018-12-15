package databankServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DatabankServerMain {

	public static void main(String[] args) {
		
		try{
			System.out.println("dataservermain started with portnumber... "+args[0]);
			Registry dataBankRegistry=LocateRegistry.createRegistry(Integer.parseInt(args[0]));
			dataBankRegistry.rebind("DataBankService", new DatabankServerImpl(Integer.parseInt(args[1])));	//Depricated
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

}
