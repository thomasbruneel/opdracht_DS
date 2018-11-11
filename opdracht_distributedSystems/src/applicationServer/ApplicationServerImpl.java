package applicationServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import interfaces.AppServerInterface;
import interfaces.DatabankServerInterface;

public class ApplicationServerImpl extends UnicastRemoteObject implements AppServerInterface {
	
	private DatabankServerInterface dsi;
	
	public ApplicationServerImpl() throws RemoteException{
		try {
			Registry registry=LocateRegistry.getRegistry("localhost",1002);
			dsi=(DatabankServerInterface) registry.lookup("DataBankService");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	

}
