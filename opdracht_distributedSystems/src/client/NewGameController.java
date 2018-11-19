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
    
	ToggleGroup playersGroup=new ToggleGroup();
    ToggleGroup sizeGroup=new ToggleGroup();
    
    
    @FXML
    public void initialize() throws RemoteException{
    	radioButton4x4.setToggleGroup(sizeGroup);
    	radioButton6x6.setToggleGroup(sizeGroup);
    	
    	radioButton2Players.setToggleGroup(playersGroup);
    	radioButton3Players.setToggleGroup(playersGroup);
    	radioButton4Players.setToggleGroup(playersGroup);
        

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
    	Game game=new Game(size);

    	ArrayList<String>spelers=new ArrayList<String>();
    	Map <String,Integer> score=new HashMap<String,Integer>();
    	ActiveGame activeGame=new ActiveGame(userName,0,players,size,game,spelers,score);  //(String creator, int numberPlayers,int maxPlayers, String size,Game game,ArrayList<String>spelers)
    	asi.addActiveGame(activeGame);
    	
    	gameId=userName;
    	openUIScreen("gameUI.fxml");
    	uiBackToLobby.getScene().getWindow().hide();
    }
    
    public void backToLobby(){
    	openUIScreen("lobbyUI.fxml");
    	uiBackToLobby.getScene().getWindow().hide();
    	 
    }
    
    

}