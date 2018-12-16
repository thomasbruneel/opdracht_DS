package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import applicationServer.ActiveGame;

/**
 * Kleine client API.
 * Wordt aangesproken door de applicationserver tijdens het spelen van een game.
 * @author Birgen & Thomas
 */
public interface gameControllerInterface extends Remote{
    /**
     * De server roept deze methode op en de speler krijgt de beurt.
     * @throws RemoteException
     */
    void giveTurn() throws RemoteException;

    /**
     * Refresht 1 specifieke node op het speelveldhet bord.
     * Server kan dit oproepen om zeker te zijn dat de data correct wordt weergegeven in de UI.
     * @param i Rij van de node
     * @param j Colom van de node
     * @throws RemoteException
     */
    void refreshBord(int i, int j) throws RemoteException;

    /**
     * Methode om de scores op het scorebord correct weer te geven.
     * @param ag Activegame van de server, om zeker te zijn dat het de correcte scores zijn
     * @throws RemoteException
     */
	void refreshScore(ActiveGame ag) throws RemoteException;

    /**
     * Wordt door de server opgeroepen wanneer iemand de game verlaten heeft en het spel vroegtijdig beïndigd werd.
     * <p>
     *     De speler komt terug in de lobby en het spel wordt afgesloten
     * </p>
     * @throws RemoteException
     */
	void backToLobby() throws RemoteException;
}
