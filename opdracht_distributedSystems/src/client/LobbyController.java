package client;

import java.rmi.RemoteException;
import java.util.ArrayList;

import applicationServer.ActiveGame;
import applicationServer.TokenGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import static client.ClientMain.*;

public class LobbyController {
    @FXML
    Label uiWelcomeUser;
    
    @FXML
    Button uiLogoutButton;
    
    @FXML
    Button uiCreateNewGameButton;
    
    
	//activegames tabel view
    @FXML
    private TableView<ActiveGame> uiTabel;
    
    @FXML
    private TableColumn<ActiveGame, String> uiTabelUser;

    @FXML
    private TableColumn<ActiveGame, Integer> uiTabelPlayers;
    
    @FXML
    private TableColumn<ActiveGame, Integer> uiTabelMaxPlayers;

    @FXML
    private TableColumn<ActiveGame, String> uiTabelSize;
    
    
    @FXML
    public void initialize() throws RemoteException{
    	System.out.println("username "+userName);
        uiWelcomeUser.setText(userName);
        ArrayList<ActiveGame> lijst=asi.getActiveGames();
        if(lijst!=null){
        	for(ActiveGame ag:lijst){
        		uiTabel.getItems().add(ag);
        	}
        }
        uiTabelUser.setCellValueFactory(new PropertyValueFactory<>("creator"));
        uiTabelPlayers.setCellValueFactory(new PropertyValueFactory<>("numberPlayers"));
        uiTabelMaxPlayers.setCellValueFactory(new PropertyValueFactory<>("maxPlayers"));
        uiTabelSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        
        

    }
    
    public void chacktoken(){
    	TokenGenerator.CheckExpiration(token);
    }
    
    public void createNewGame(){
    	openNewGameUI();
    	uiLogoutButton.getScene().getWindow().hide();
    }

}
