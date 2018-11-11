package client;

import static client.ClientMain.*;

import java.rmi.RemoteException;

import applicationServer.ActiveGame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    public void initialize() throws RemoteException{

        

    }
    
    public void createNewGame() throws RemoteException{
    	Game game=new Game(Integer.parseInt(uiSizeBoard.getText()));
    	String size=uiSizeBoard.getText()+" x "+uiSizeBoard.getText();
    	ActiveGame activeGame=new ActiveGame(userName,1,Integer.parseInt(uiNumberplayers.getText()),size,game);
    	asi.addActiveGame(activeGame);
    	openGameUI();
    	uiBackToLobby.getScene().getWindow().hide();
    }
    
    public void backToLobby(){
    	openLobbyUI();
    	uiBackToLobby.getScene().getWindow().hide();
    	 
    }
    
    

}