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
    	ActiveGame activeGame=new ActiveGame(userName,Integer.parseInt(uiNumberplayers.getText()),Integer.parseInt(uiSizeBoard.getText()));
    	asi.addActiveGame(activeGame);
    }
    
    public void backToLobby(){
    	openLobbyUI();
    	uiBackToLobby.getScene().getWindow().hide();
    	 
    }
    
    

}