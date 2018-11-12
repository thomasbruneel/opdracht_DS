package client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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
    
    
    private ArrayList<ActiveGame> listActiveGames;
    
    private boolean running;
    
    @FXML
    public void initialize() throws RemoteException{
    	running=true;
    	//uiTabel.getItems().remove(true);
        uiWelcomeUser.setText(userName);
        listActiveGames=asi.getActiveGames();
        uiTabel.getItems().setAll(listActiveGames);
        uiTabelUser.setCellValueFactory(new PropertyValueFactory<>("creator"));
        uiTabelPlayers.setCellValueFactory(new PropertyValueFactory<>("numberPlayers"));
        uiTabelMaxPlayers.setCellValueFactory(new PropertyValueFactory<>("maxPlayers"));
        uiTabelSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        startCheckThread();

    }
    
    private void startCheckThread() {
    		new Thread(){
    			public synchronized void run(){
    				while (running){
    					List<ActiveGame> newList=null;
						try {
							newList = asi.getActiveGames();
							if(newList!=null&&newList.size()!=listActiveGames.size()){
								listActiveGames=(ArrayList<ActiveGame>) newList;
								uiTabel.getItems().setAll(newList);
	    						
	    					}
						} catch (RemoteException e) {
							e.printStackTrace();
						}
    					
    				}
    			}


    		}.start();
		
	}

	public void chacktoken(){
    	TokenGenerator.CheckExpiration(token);
    }
    
    public void createNewGame(){
    	openUIScreen("newGameUI.fxml");
    	running=false;
    	uiLogoutButton.getScene().getWindow().hide();
    }
    
    public void Join(){
    	ActiveGame activeGame=uiTabel.getSelectionModel().getSelectedItem();
    	if(activeGame!=null){
    		System.out.println(activeGame.getCreator());
    		gameId=activeGame.getCreator();
        	openUIScreen("gameUI.fxml");
        	uiLogoutButton.getScene().getWindow().hide();
    	}
    }
    
	public ArrayList<ActiveGame> getListActiveGames() {
		return listActiveGames;
	}

	public void setListActiveGames(ArrayList<ActiveGame> listActiveGames) {
		this.listActiveGames = listActiveGames;
	}
    
    

}
