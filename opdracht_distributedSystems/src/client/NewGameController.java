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
    	if(radioButton2Players.isSelected()==true){
    		players=2;
    	}
    	else if(radioButton3Players.isSelected()==true){
    		players=3;
    	}
    	else if(radioButton4Players.isSelected()==true){
    		players=4;
    	}
    	
    	int size = 0;
    	if(radioButton4x4.isSelected()==true){
    		size=4;
    	}
    	else if(radioButton6x6.isSelected()==true){
    		size=6;
    	}
    	
    	boolean theme = false;
    	if(radioButtonBatman.isSelected()==true){
    		theme=true;
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
    	if(bit){
	    	afbeeldingen.put(0,"client/images/batman/0.jpg");
	    	afbeeldingen.put(1, "client/images/batman/1.jpg");
	    	afbeeldingen.put(2, "client/images/batman/2.jpg");
	    	afbeeldingen.put(3, "client/images/batman/3.jpg");
	    	afbeeldingen.put(4, "client/images/batman/4.jpg");
	    	afbeeldingen.put(5, "client/images/batman/5.jpg");
	    	afbeeldingen.put(6, "client/images/batman/6.jpg");
	    	afbeeldingen.put(7, "client/images/batman/7.jpg");
	    	afbeeldingen.put(8, "client/images/batman/8.jpg");
	    	afbeeldingen.put(9, "client/images/batman/9.jpg");
	    	afbeeldingen.put(10, "client/images/batman/10.jpg");
	    	afbeeldingen.put(11, "client/images/batman/11.jpg");
	    	afbeeldingen.put(12, "client/images/batman/12.jpg");
	    	afbeeldingen.put(13, "client/images/batman/13.jpg");
	    	afbeeldingen.put(14, "client/images/batman/14.jpg");
	    	afbeeldingen.put(15, "client/images/batman/15.jpg");
	    	afbeeldingen.put(16, "client/images/batman/16.jpg");
	    	afbeeldingen.put(17, "client/images/batman/17.jpg");
    	}
		
	}
    
    

}