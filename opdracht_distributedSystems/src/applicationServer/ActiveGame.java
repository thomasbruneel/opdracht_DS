package applicationServer;

import java.io.Serializable;

import memoryGame.Game;

public class ActiveGame implements Serializable {
	private int gameId;
	private String creator;
	private int numberPlayers;
	private int maxPlayers;
	private String size;
	private Game game;
	
	
	//constructor
	public ActiveGame(int gameId,String creator, int numberPlayers,int maxPlayers, String size,Game game) {
		this.gameId=gameId;
		this.creator = creator;
		this.numberPlayers = numberPlayers;
		this.maxPlayers=maxPlayers;
		this.size = size;
		this.game=game;
	}
	
	// getters en setters
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public int getNumberPlayers() {
		return numberPlayers;
	}
	public void setNumberPlayers(int numberPlayers) {
		this.numberPlayers = numberPlayers;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}



	
	
	
	
	
	
	
	

}
