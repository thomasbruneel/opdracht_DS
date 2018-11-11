package memoryGame;

import java.io.Serializable;

public class Game implements Serializable {
	
	private Bord bord;

	public Game(int size){
		System.out.println("size: "+size);
		this.bord=new Bord(size);
	}

	public Bord getBord() {
		return bord;
	}

	public void setBord(Bord bord) {
		this.bord = bord;
	}
	

	
	
	
	
	
	
	

}
