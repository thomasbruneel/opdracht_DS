package applicationServer;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import interfaces.AppServerInterface;
import interfaces.DatabankServerInterface;

public class AppServerImpl extends UnicastRemoteObject implements AppServerInterface{
	private DatabankServerInterface dsi;
	
	public AppServerImpl() throws RemoteException{
		Registry registry=LocateRegistry.getRegistry("localhost",1003);
		try {
			dsi=(DatabankServerInterface) registry.lookup("DataBankService");
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
		// TODO Auto-generated method stub
		return dsi.controleerUniekeNaam(naam);
	}



}
