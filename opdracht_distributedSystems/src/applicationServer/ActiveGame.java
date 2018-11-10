package applicationServer;

import java.io.Serializable;

public class ActiveGame implements Serializable {
	
	private String creator;
	private int numberPlayers;
	private int maxPlayers;
	private String size;
	
	
	//constructor
	public ActiveGame(String creator, int numberPlayers,int maxPlayers, String size) {
		this.creator = creator;
		this.numberPlayers = numberPlayers;
		this.maxPlayers=maxPlayers;
		this.size = size;
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
	
	
	
	

}
