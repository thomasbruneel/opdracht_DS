package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import applicationServer.ActiveGame;
import applicationServer.ActiveGameInfo;
import applicationServer.Leaderbord;
import client.User;

/**
 * Alle methoden die kunnen uitgevoerd worden door de appserver en dispatcher
 * @author Birgen en Thomas
 */
public interface DatabankServerInterface extends Remote{

    /**
     * Maakt een nieuwe gebruiker aan in de tabel voor gebruikers.
     * <p>
     *     Maakt een salt aan en voegt de nieuwe gebruiker toe aan de locale database.
     * </p>
     * <p>
     *     Roept vervolgens de persistRegistermethode op bij de andere databanken.
     * </p>
     * @param naam Gekozen unieke naam de gebruiker.
     * @param pwd Wachtwoord van de gebruiker.
     * @throws RemoteException
     * @see #persistRegister(String, String)
     */
	void register(String naam, String pwd) throws RemoteException;

    /**
     * Controleert of de naam en wachtwoord gelinkt zijn aan een gebruiker
     * @param userName Naam van de user
     * @param userPwd wWachtwoord van de user
     * @return Boolean, true wanneer er werd ingelogd, false wanneer opgegeven ww en usernaam niet samen bestaan in de databank
     * @throws RemoteException
     */
	boolean login(String userName,String userPwd) throws RemoteException;

    /**
     * Methode die controleert of de gekozen usernaam nog niet bestaat
     * @param naam Gekozen naam
     * @return True als de naam uniek is, false wanneer ze al bestaat.
     * @throws RemoteException
     */
	boolean controleerUniekeNaam(String naam) throws RemoteException;

    /**
     * Creërt of refresht de token van een user.
     * @param userName Naam van de user.
     * @param token Wachtwoord van de user.
     * @throws RemoteException
     */
	void updateToken(String userName, String token) throws RemoteException;

    /**
     * Maakt een active game aan in de databank en persist deze naar de andere databanken.
     * @deprecated
     * @param activeGame
     * @throws RemoteException
     */
	void createActiveGame(ActiveGame activeGame)throws RemoteException;

    /**
     * Updatet de totale score van een speler en persist het naar de andere databanken.
     * @param userName Naam van de speler
     * @throws RemoteException
     * @see #increaseWinToOtherDBs(String)
     */
	void increaseWin(String userName) throws RemoteException;

    /**
     * Geeft het volledige leaderbord van alle spelers terug.
     * @return alle naam/score paren
     * @throws RemoteException
     */
	List<Leaderbord> getAllLeaderbord() throws RemoteException;

    /**
     * Genereert alle images uit de databank voor een bepaald thema. Deze methode wordt opgeroepen wanneer de client images van een bepaald thema wenst te cachen.
     * @param theme Thema van de images.
     * @return Lijst van de images in byte[]
     * @throws RemoteException
     */
	public List<byte[]> getImagesByTheme(String theme) throws RemoteException;

    /**
     * Wordt door de dispatcher opgeroepen om een nieuwe database connectie te leggen van de huidige naar die gespecifieerd door de parameters.
     * @param ip Ip adres van de nieuwe database.
     * @param poortnummer Poornummer waar de nieuwe database op werkt.
     * @throws RemoteException
     */
    void registreerdb(String ip, int poortnummer)throws RemoteException;

    /**
     * Korte methode om de connectie tussen deze database en zijn gekende databases te testen.
     * @return
     * @throws RemoteException
     *
     */
	String pingAnderen() throws RemoteException;

    /**
     * Korte methode om met 1 bepaalde databank te pingen.
     * @return id van de databank als resultaat van het pingen
     * @throws RemoteException
     */
	String pingResult() throws RemoteException;

    /**
     * Methode die nog moet worden uitgebreid om recovery toe te passen bij uitval van en databank.
     * <p>
     *     Deze methode wordt opgeroepen bij het opstarten van een databank en haalt alle persists op die zijn uitgevoerd tijdens het offline zijn. Dit van alle andere databanken.
     * </p>
     * @throws RemoteException
     */
	void verwerkQueues() throws RemoteException;

    /**
     * Methode om de users die gepersist moesten worden tijdens het offline zijn van een andere database te halen.
     * @return Gegevens over alle gebruikers die nog in de database moeten aangemeld worden.
     * @throws RemoteException
     */
	List<User> getUserQueue() throws RemoteException;

    /**
     * Methode om alle actieve games die bezig zijn binnen te halen van een andere databank.
     * @return Gegevens over alle games.
     * @throws RemoteException
     * @deprecated Moet veranderd worden naar ActiveGameInfo
     */
	List<ActiveGame> getActivegames() throws RemoteException;

    /**
     * Leegt alle "persist queues".
     * @throws RemoteException
     */
	void clearQueues() throws RemoteException;

    /**
     * Getter
     * @return Id van de databank.
     * @throws RemoteException
     */
	String getid() throws  RemoteException;

    /**
     * @deprecated vervangen door #persistActiveGameInfo
     * @param activeGame
     * @throws RemoteException
     * @see #persistActiveGameInfo(ActiveGameInfo)
     */
    void persistActiveGame(ActiveGame activeGame) throws RemoteException;

    /**
     * Verhoogt de counter van hoeveel applicatieservers verbonden zijn.
     * @throws RemoteException
     */
	void aanmelden() throws  RemoteException;

    /**
     * Geeft terug hoeveel appservers verbonden zijn met de databankserver.
     * @return ^
     * @throws RemoteException
     */
	int getCount() throws RemoteException;

    /**
     * Geeft alle info i.v.m. de actieve spellen weer om in een lobby te tonen.
     * @return ^
     * @throws RemoteException
     */
	ArrayList<ActiveGameInfo> getAllActiveGamesInfo() throws RemoteException;

    /**
     * Creërt aan niewe rij in de databank voor de nieuwe Activegame die gestart is. Vervolgens wordt gepersist naar alle andere databanken.
     * @param activeGameInfo Spel toe te voegen
     * @throws RemoteException
     * @see #persistActiveGameInfo(ActiveGameInfo)
     */
	void createActiveGameInfo(ActiveGameInfo activeGameInfo) throws RemoteException;

    /**
     * Schrijft de info over het nieuwe activegame weg naar de databank.
     * @param activeGameInfo
     * @throws RemoteException
     * @see #createActiveGameInfo(ActiveGameInfo)
     */
	void persistActiveGameInfo(ActiveGameInfo activeGameInfo) throws RemoteException;

    /**
     * Verwijdert active game uit de databank waneer dat spel afgelopen is. persists vorvolgens naar de andere databanken.
     * @param creator
     * @throws RemoteException
     * @see #removeActiveGameInfoToOtherDbs(String)
     */
	void removeActiveGameInfo(String creator) throws RemoteException;

    /**
     * Verwijdert active game uit de databank waneer dat spel afgelopen is.
     * @param creator
     * @throws RemoteException
     * @see #removeActiveGameInfo(String)
     */
    void removeActiveGameInfoToOtherDbs(String creator) throws RemoteException;

    /**
     * Registreert gebruiker bij de databank.
     * @param naam Naam van de gebruiker
     * @param pwd Wachtwoord van de gebruiker
     * @throws RemoteException
     * @see #register(String, String)
     */
    void persistRegister(String naam, String pwd) throws RemoteException;

    /**
     * Wijzigt het "aantalspelers"-veld van een bepaalde Activegame in de databank. Persists vervolgens de data naar de andere databanken.
     * @param gameId Id van de game waarvan de counter verhoogd moet worden
     * @param bit Boolean die bij een volgende iteratie gebruikt zal worden om de count ook te kunnen verlagen.
     * @throws RemoteException
     * @see #increasePlayerCountToOtherDBs(String, boolean)
     */
	void increasePlayerCount(String gameId, boolean bit) throws RemoteException;

    /**
     * Wijzigt het "aantalspelers"-veld van een bepaalde Activegame in de databank.
     * @param gameId Id van de game waarvan de counter verhoogd moet worden
     * @param bit Boolean die bij een volgende iteratie gebruikt zal worden om de count ook te kunnen verlagen.
     * @throws RemoteException
     */
	void increasePlayerCountToOtherDBs(String gameId, boolean bit) throws RemoteException;

    /**
     * Updatet de totale score van een speler.
     * @param userName
     * @throws RemoteException
     * @see #increasePlayerCount(String, boolean)
     */
	void increaseWinToOtherDBs(String userName) throws RemoteException;

}
