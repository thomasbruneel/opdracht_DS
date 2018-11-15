package client;

import static client.ClientMain.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
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
    RadioButton radioButton4x4;
	
	@FXML
    RadioButton radioButton6x6;
    
    ToggleGroup sizeGroup=new ToggleGroup();
    
    
    @FXML
    public void initialize() throws RemoteException{
    	radioButton4x4.setToggleGroup(sizeGroup);
    	radioButton6x6.setToggleGroup(sizeGroup);
        

    }
    
    public void createNewGame() throws RemoteException{
    	String size = null;
    	if(radioButton4x4.isSelected()==true){
    		size="4";
    	}
    	else if(radioButton6x6.isSelected()==true){
    		size="6";
    	}
    	Game game=new Game(Integer.parseInt(size));

    	ArrayList<String>spelers=new ArrayList<String>();
    	ActiveGame activeGame=new ActiveGame(userName,0,Integer.parseInt(uiNumberplayers.getText()),size,game,spelers);  //(String creator, int numberPlayers,int maxPlayers, String size,Game game,ArrayList<String>spelers)
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