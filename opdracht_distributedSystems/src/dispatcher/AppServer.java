package dispatcher;

import java.io.Serializable;

import interfaces.AppServerInterface;

public class AppServer implements Serializable {
	private String ipAdres;
	private int poortnummer;
	
	private DatabankServer dbserver;
	private int DBportnummer;
	
	
	public AppServer(String ipAdres, int poortnummer, DatabankServer dbserver) {
		this.ipAdres = ipAdres;
		this.poortnummer = poortnummer;
		this.dbserver=dbserver;
		this.DBportnummer=dbserver.getPoortnummer();
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


	public DatabankServer getDbserver() {
		return dbserver;
	}


	public void setDbserver(DatabankServer dbserver) {
		this.dbserver = dbserver;
	}


	public int getDBportnummer() {
		return DBportnummer;
	}


	public void setDBportnummer(int dBportnummer) {
		DBportnummer = dBportnummer;
	}
	
	
	
	
	
	

	
	
	
	
	
	
	

}
