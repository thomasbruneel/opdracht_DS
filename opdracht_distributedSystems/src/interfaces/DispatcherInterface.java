package interfaces;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface van de dispatcher aangesproken door de client
 */
public interface DispatcherInterface extends Remote{

	/**
	 * Opgeroepen door de client wanneer deze de eerste keer met het systeem in contact komt.
	 * <p>
	 *     De dispatcher berekent welke applicatieserver het minst druk bezet is om dan de nieuwe client naar die applicatieserver door te verwijzen.
	 * </p>
	 * @return het poortnummer van de applicatieserver waar de client mee mag verbinden
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	int getPortNumberAppServer() throws RemoteException, NotBoundException;

	/**
	 * Wordt opgeroepen bij opstarten van de eerste appserver
	 * @param asi de interface van de eerste appserver
	 * @throws RemoteException
	 */
	void addAsi(AppServerInterface asi) throws RemoteException;

	/**
	 * De client vraagt om een bepaalde appserver te joinen om daar een game te spelen of spectaten
	 * @param appServerId de ID van de appserver waar het gekozen spel zich bevindt
	 * @return de interface van de gevraagde apserver
	 * @throws RemoteException
	 */
    AppServerInterface requestAppserver(int appServerId) throws RemoteException;
}
