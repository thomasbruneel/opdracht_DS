package memoryGame;

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import interfaces.DatabankServerInterface;

public class Bord implements Serializable {
	
	private Kaart [][] matrix;
	private int grootte;
	
	
	public Bord(int grootte) {
		this.matrix = new Kaart[grootte][grootte];
		this.grootte = grootte;
		

		BordSetup();
	}


	private void BordSetup() {
		int aantal=(grootte*grootte)/2;
		ArrayList<Integer>getallen=new ArrayList<>();
		int getal=0;
		for(int i=0;i<aantal;i++){
			getallen.add(getal);
			getallen.add(getal);
			getal++;
			
		
		}
		
		Collections.shuffle(getallen);
		int index=0;
		for(int i=0;i<grootte;i++){
			for(int j=0;j<grootte;j++){
				
				matrix[i][j]=new Kaart(getallen.get(index));
				index++;
				
				
			}
		}
		
	}


	public void print() {
		for(int i=0;i<grootte;i++){
			for(int j=0;j<grootte;j++){
				
				System.out.print(matrix[i][j].getWaarde()+" ");
				
				
			}
			System.out.println();
		}
		
	}


	public Kaart[][] getMatrix() {
		return matrix;
	}


	public void setMatrix(Kaart[][] matrix) {
		this.matrix = matrix;
	}


	public int getGrootte() {
		return grootte;
	}


	public void setGrootte(int grootte) {
		this.grootte = grootte;
	}

}
