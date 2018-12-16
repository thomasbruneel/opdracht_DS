package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import applicationServer.ActiveGame;
import applicationServer.ActiveGameInfo;
import applicationServer.Leaderbord;
import client.LobbyController;

public interface AppServerInterface extends Remote{

    /**
     * Tussenmethode gebruikt bij registratie van een client.
     * @param naam Naam van de client.
     * @param pwd Wachtwoord van de client.
     * @throws RemoteException
     */
	void register(String naam, String pwd) throws RemoteException;

    /**
     * Methode gebruikt bij inloggen.
     * <p>
     *     Controleert of de gebruiker wel bestaat.
     *     Vernieuwd vervolgens het Token van de gebruiker.
     * </p>
     * @param userName Gebruikersnaam van de client
     * @param userPwd Wachtwoord van de client
     * @return
     * @throws RemoteException
     */
	String login(String userName,String userPwd) throws RemoteException;

    /**
     * Tussenmethode voor het controleren van het uniek zijn van de gebruikersnaam
     * @param naam te controleren naam
     * @return
     * @throws RemoteException
     */
	boolean controleerUniekeNaam(String naam) throws RemoteException;

    /**
     *
     * @param activeGame
     * @throws RemoteException
     */
	void addActiveGame(ActiveGame activeGame) throws RemoteException;

    /**
     * vervangen door #getAllActiveGamesInfo()
     * @deprecated
     * @return
     * @throws RemoteException
     * @see #getAllActiveGamesInfo()
     */
	ArrayList<ActiveGame> getActiveGames() throws RemoteException;

    /**
     * Verwijdert de lokale activeGame op de applicatieserver
     * @param activeGame Te verwijderen activeGame
     * @throws RemoteException
     */
	void removeActiveGame(ActiveGame activeGame) throws RemoteException;

    /**
     * Verwijdert de lokale activeGame op de applicatieserver
     * @param creator id van te verwijderen game
     * @throws RemoteException
     */
	void removeactiveGameById(String creator) throws RemoteException;

    /**
     * Geeft de activeGame terug op basis van de id van de activeGame
     * @param id id van de activeGame
     * @return ActiveGame die hoort bij de id
     * @throws RemoteException
     */
	ActiveGame getActiveGame(String id) throws RemoteException;

    /**
     * Stuurt een update naar de database dat er een speler is bijgekomen in de game
     * @param gameId Game waar een speler is gejoined
     * @param bit Boolean die bij een volgende iteratie kan gebruikt worden om een decrese te doen i.p.v. een increase
     * @throws RemoteException
     */
	void increasePlayerCount(String gameId,boolean bit) throws RemoteException;

    /**
     * Methode opgeroepn door de client met de beurt. Draait de kaart bepaald door parameter x en y om en persist deze move naar alle deelnemers en spectators.
     * @param creator id van de activeGame
     * @param x x-coordinaat van de kaart
     * @param y y-coordinaat van de kaart
     * @throws RemoteException
     */
	void flipCard(String creator, int x, int y)throws RemoteException;

    /**
     * Voegt een speler toe aan de datastructuur van de activeGame en voegt de speler toe aan het scorebord
     * @param gameId id van het spel gejoined door de client
     * @param speler naam van de client
     * @throws RemoteException
     */
	void addPlayer(String gameId, String speler) throws RemoteException;

    /**
     *
     * @param gameId
     * @return
     * @throws RemoteException
     */
	ActiveGame getChangedActiveGame(String gameId) throws RemoteException;

    /**
     * Verhoogt de lokale score van de bepaalde speler in bepaalde de activeGame
     * @param gameId id van de activeGame
     * @param speler Naam van de speler die een punt verdient
     * @throws RemoteException
     */
	void increaseScore(String gameId, String speler) throws RemoteException;

    /**
     * Methode waarmee een client zich registreert bij een gekozen spel bij de gameserver
     * @param Gameid id van het spel
     * @param gci de interface van de client die de server kan aanspreken
     * @throws RemoteException
     */
	void addGameController(String Gameid,gameControllerInterface gci) throws RemoteException;

    /**
     * Hiermee geeft de client aan dat hij zijn beurt gespeeld heeft
     * @param Gameid id van het spel
     * @throws RemoteException
     */
	void endTurn(String Gameid) throws RemoteException;

    /**
     * Met deze methode meldt een spectator zich aan bij het spel. Hij geeft zijn interface mee om zo game updates te ontvangen.
     * @param gameId id van de activeGame die de spectator wil volgen
     * @param gameController de interface van de spectator
     * @throws RemoteException
     */
	void addSpectateController(String gameId, gameControllerInterface gameController) throws RemoteException;

    /**
     * Methode die kan opgeroepen worden om alle scores gelijk te stellen om bugs te voorkomen
     * @param activeGame activegame waarvan de scores moeten gerefreshed worden
     * @throws RemoteException
     */
	void refreshScore(ActiveGame activeGame) throws RemoteException;

    /**
     * Tussenmethode die de database om de leaderbord vraagt
     * @return De leaderbord
     * @throws RemoteException
     */
	List<Leaderbord> getLeaderBord()throws RemoteException;

    /**
     * Update voor de databank wanneer een speler een spel wint.
     * @param userName
     * @throws RemoteException
     */
	void increaseWin(String userName) throws RemoteException;

    /**
     * Wordt opgeroepen wanneer iemand een spel verlaat. Sluit het spel volledig af en wijst alle andere spelers door naar de lobby.
     * @param gameId id van de af te sluiten game
     * @throws RemoteException
     */
	void leaveGame(String gameId) throws RemoteException;

    /**
     * Tussenmethode die afbeeldingen cachet op de client
     * @param theme Gekozen thema van de afbeeldingen.
     * @return Bytelijst van alle afbeeldingen
     * @throws RemoteException
     */
	List<byte[]> getImagesByTheme(String theme) throws RemoteException;

    /**
     * Tussenmethode die de info van alle actieve games opvraagt aan de databank en deze teruggeeft aan de client.
     * @return info van alles activeGames
     * @throws RemoteException
     */
	ArrayList<ActiveGameInfo> getAllActiveGamesInfo() throws RemoteException;

    /**
     * Geeft aan de databank aan dat er een nieuw spel aangemaakt werd.
     * @param activeGameInfo Info over het spel
     * @throws RemoteException
     */
	void addActiveGameInfo(ActiveGameInfo activeGameInfo) throws RemoteException;

    /**
     * Getter
     * @return Serverid
     * @throws RemoteException
     */
    int getServerid() throws RemoteException;

    /**
     *Methode waarmee de dispatcher kan opvragen hoeveel activeGames een appServer op een bepaald moment host.
     * @return Aantal activeGames gehost op de server op dat moment
     * @throws RemoteException
     */
	int getActiveGamessize() throws RemoteException;
}
