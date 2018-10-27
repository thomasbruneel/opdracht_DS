package dispatcher;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import interfaces.DatabankServerInterface;

public class DispathcherImpl extends UnicastRemoteObject implements DatabankServerInterface {
	DatabankServerInterface dsi;
	public DispathcherImpl() throws RemoteException{
		Registry registry=LocateRegistry.getRegistry("localhost",1001);
		try {
			dsi=(DatabankServerInterface) registry.lookup("DatabankService");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		

    }

	@Override
	public void spelerToevoegen(String naam, String pwd) throws RemoteException {
		dsi.spelerToevoegen(naam, pwd);
		
	}

	@Override
	public boolean checkPwd(String userName, String userPwd) throws RemoteException {
		
		return dsi.checkPwd(userName, userPwd);
	}

	@Override
	public boolean controleerUniekeNaam(String naam) throws RemoteException {
		
		return dsi.controleerUniekeNaam(naam);
	}

}
