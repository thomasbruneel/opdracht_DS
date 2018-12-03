package dispatcher;

import java.io.Serializable;

import interfaces.AppServerInterface;
import interfaces.DatabankServerInterface;

public class DatabankServer implements Serializable {
	private int ID;
	private String ipAdres;
	private int poortnummer;
	private DatabankServerInterface dsi;
	
	
	public DatabankServer(int iD, String ipAdres, int poortnummer, DatabankServerInterface dsi) {
		this.ID = iD;
		this.ipAdres = ipAdres;
		this.poortnummer = poortnummer;
		this.dsi = dsi;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		this.ID = iD;
	}
	public String getIpAdres() {
		return ipAdres;
	}
	public void setIpAdres(String ipAdres) {
		this.ipAdres = ipAdres;
	}
	public int getPoortnummer() {
		return poortnummer;
	}
	public void setPoortnummer(int poortnummer) {
		this.poortnummer = poortnummer;
	}
	public DatabankServerInterface getDsi() {
		return dsi;
	}
	public void setDsi(DatabankServerInterface dsi) {
		this.dsi = dsi;
	}
	
	
	
	

}
