package client;

import static client.ClientMain.*;

import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    
    public void createNewGame(){
 
    }
    
    public void backToLobby(){
    	System.out.println("fffffffffffffddddddddddddd");
    	openLobbyUI();
    	uiBackToLobby.getScene().getWindow().hide();
    	 
    }
    
    

}