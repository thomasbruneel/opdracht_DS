package client;

import static client.ClientMain.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import applicationServer.ActiveGame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import memoryGame.Game;

public class NewGameController {
    @FXML
    TextField uiNumberplayers;
    
    @FXML
    TextField uiSizeBoard;
    
    
    @FXML
    Button uiCreateNewGameButton;
    
    @FXML
    Button uiBackToLobby;
    
	@FXML
    RadioButton radioButton2Players;
	
	@FXML
    RadioButton radioButton3Players;
	
	@FXML
    RadioButton radioButton4Players;
	
	@FXML
    RadioButton radioButton4x4;
	
	@FXML
    RadioButton radioButton6x6;
	
	@FXML
    RadioButton radioButtonBatman;
	
	@FXML
    RadioButton radioButtonPokemon;
    
	ToggleGroup playersGroup=new ToggleGroup();
    ToggleGroup sizeGroup=new ToggleGroup();
    ToggleGroup themeGroup=new ToggleGroup();
    
    
    @FXML
    public void initialize() throws RemoteException{
    	radioButton4x4.setToggleGroup(sizeGroup);
    	radioButton6x6.setToggleGroup(sizeGroup);
    	
    	radioButton2Players.setToggleGroup(playersGroup);
    	radioButton3Players.setToggleGroup(playersGroup);
    	radioButton4Players.setToggleGroup(playersGroup);
    	
    	radioButtonBatman.setToggleGroup(themeGroup);
    	radioButtonPokemon.setToggleGroup(themeGroup);

        

    }
    
    public void createNewGame() throws RemoteException{
    	int players = 0;
    	if(radioButton2Players.isSelected()){
    		players=2;
    	}
    	else if(radioButton3Players.isSelected()){
    		players=3;
    	}
    	else if(radioButton4Players.isSelected()){
    		players=4;
    	}
    	
    	int size = 0;
    	if(radioButton4x4.isSelected()){
    		size=4;
    	}
    	else if(radioButton6x6.isSelected()){
    		size=6;
    	}
    	
    	boolean theme = false;
    	if(radioButtonBatman.isSelected()==true){
    		theme=true;
    	}
    	if(radioButtonPokemon.isSelected()==true){
    		theme=false;
    	}
    	voegAfbeeldingenToe(theme);
    	
    	Game game=new Game(size);

    	ArrayList<String>spelers=new ArrayList<String>();
    	Map <String,Integer> score=new HashMap<String,Integer>();
    	ActiveGame activeGame=new ActiveGame(userName,0,players,size,game,spelers,score,theme);  //(String creator, int numberPlayers,int maxPlayers, String size,Game game,ArrayList<String>spelers)
    	asi.addActiveGame(activeGame);

    	gameId=userName;
    	openUIScreen("gameUI.fxml");
    	uiBackToLobby.getScene().getWindow().hide();
    }
    
    public void backToLobby(){
    	openUIScreen("lobbyUI.fxml");
    	uiBackToLobby.getScene().getWindow().hide();
    	 
    }
    
    private void voegAfbeeldingenToe(boolean bit) {
    	String theme=null;
    	String file=null;
    	if(bit){
    		theme="batman";
    		file=".jpg";
    	}
    	else{
    		theme="pokemon";
    		file=".png";
    	}
 
	    afbeeldingen.put(0,"client/images/"+theme+"/0"+file);
	    afbeeldingen.put(1, "client/images/"+theme+"/1"+file);
	    afbeeldingen.put(2, "client/images/"+theme+"/2"+file);
	    afbeeldingen.put(3, "client/images/"+theme+"/3"+file);
	    afbeeldingen.put(4, "client/images/"+theme+"/4"+file);
	    afbeeldingen.put(5, "client/images/"+theme+"/5"+file);
	    afbeeldingen.put(6, "client/images/"+theme+"/6"+file);
	    afbeeldingen.put(7, "client/images/"+theme+"/7"+file);
	    afbeeldingen.put(8, "client/images/"+theme+"/8"+file);
	    afbeeldingen.put(9, "client/images/"+theme+"/9"+file);
	    afbeeldingen.put(10, "client/images/"+theme+"/10"+file);
	    afbeeldingen.put(11, "client/images/"+theme+"/11"+file);
	    afbeeldingen.put(12, "client/images/"+theme+"/12"+file);
	    afbeeldingen.put(13, "client/images/"+theme+"/13"+file);
	    afbeeldingen.put(14, "client/images/"+theme+"/14"+file);
	    afbeeldingen.put(15, "client/images/"+theme+"/15"+file);
	    afbeeldingen.put(16, "client/images/"+theme+"/16"+file);
	    afbeeldingen.put(17, "client/images/"+theme+"/17"+file);
	    
	    afbeeldingen.put(18, "client/images/"+theme+"/back"+file);

		
	}
    
    

}