package memoryGame;

public class Kaart {
	
	private boolean omgedraaid;
	private int waarde;
	
	public Kaart(){
		omgedraaid=false;
	}
	
	public Kaart(int waarde){
		omgedraaid=false;
		this.waarde=waarde;
	}

	public boolean isOmgedraaid() {
		return omgedraaid;
	}

	public void setOmgedraaid(boolean omgedraaid) {
		this.omgedraaid = omgedraaid;
	}

	public int getWaarde() {
		return waarde;
	}

	public void setWaarde(int waarde) {
		this.waarde = waarde;
	}
	
	
	
	

}
