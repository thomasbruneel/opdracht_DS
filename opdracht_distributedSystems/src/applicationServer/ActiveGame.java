package applicationServer;

import java.io.Serializable;

public class ActiveGame implements Serializable {
	
	private String creator;
	private int numberPlayers;
	private int size;
	
	
	//constructor
	public ActiveGame(String creator, int numberPlayers, int size) {
		this.creator = creator;
		this.numberPlayers = numberPlayers;
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
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
	
	

}
