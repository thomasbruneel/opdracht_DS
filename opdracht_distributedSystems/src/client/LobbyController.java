package client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import applicationServer.ActiveGame;
import applicationServer.TokenGenerator;
import client.Tasks.LobbyRefreshTask;
import interfaces.LobbyControllerInterface;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import static client.ClientMain.*;

public class LobbyController extends UnicastRemoteObject implements LobbyControllerInterface,Serializable {
	
    public LobbyController() throws RemoteException {
        
    }


	@FXML
    Label uiWelcomeUser;
    
    @FXML
    Button uiLogoutButton;
    
    @FXML
    Button uiCreateNewGameButton;
    
    @FXML
    Label uiErrorMessage;
    
    
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
    
   // private LobbyRefreshTask task;
    
    //private boolean running=true;
    private String idController;
    
    @FXML
    public void initialize() throws RemoteException{
    	//uiTabel.getItems().remove(true);
    	gameId=null;
    	idController=userName;
    	uiErrorMessage.setVisible(false);
        uiWelcomeUser.setText(userName);
        listActiveGames=asi.getActiveGames();
        uiTabel.getItems().setAll(listActiveGames);
        uiTabelUser.setCellValueFactory(new PropertyValueFactory<>("creator"));
        uiTabelPlayers.setCellValueFactory(new PropertyValueFactory<>("numberPlayers"));
        uiTabelMaxPlayers.setCellValueFactory(new PropertyValueFactory<>("maxPlayers"));
        uiTabelSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        //startCheckThread();
        //task=new LobbyRefreshTask(uiTabel,this);
        //new Thread(task).start();
        
        asi.addLobbyController(this);
        asi.updateLobby();
    }
    /*
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
	*/

	public void logout(){
    	logoutNow(); // in class ClientMain
    	uiLogoutButton.getScene().getWindow().hide();
    	//task.cancel();
    }
    
    public void createNewGame(){
    	try {
			asi.removeLobbyController(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	openUIScreen("newGameUI.fxml");
    	uiLogoutButton.getScene().getWindow().hide();
    	//task.cancel();
    }
    
    public void Join(){
    	spectateMode=false;
    	ActiveGame activeGame=uiTabel.getSelectionModel().getSelectedItem();
    	if(activeGame!=null&&activeGame.getNumberPlayers()<activeGame.getMaxPlayers()){
    		System.out.println(activeGame.getCreator());
    		gameId=activeGame.getCreator();
        	openUIScreen("gameUI.fxml");
        	uiLogoutButton.getScene().getWindow().hide();
        	try {
				asi.removeLobbyController(this);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else{
    		uiErrorMessage.setText("LOBBY ZIT VOL");
    		uiErrorMessage.setVisible(true);
    	}
    }
    
    public void spectate(){
    	spectateMode=true;
    	ActiveGame activeGame=uiTabel.getSelectionModel().getSelectedItem();
    	gameId=activeGame.getCreator();
        openUIScreen("gameUI.fxml");
        uiLogoutButton.getScene().getWindow().hide();

    }
    
	public ArrayList<ActiveGame> getListActiveGames() {
		return listActiveGames;
	}

	public void setListActiveGames(ArrayList<ActiveGame> listActiveGames) {
		this.listActiveGames = listActiveGames;
	}

	public synchronized void refresh(ArrayList<ActiveGame> newList) {
		if( (newList!=null) ){

			listActiveGames=newList;
			uiTabel.getItems().setAll(listActiveGames);
        }
		
	}

	@Override
	public void updateLobby(ArrayList<ActiveGame> games) throws RemoteException {
		listActiveGames=games;
		uiTabel.getItems().setAll(games);
		
	}
	
	public void goToLeaderbord(){
		openUIScreen("leaderbordUI.fxml");
		uiLogoutButton.getScene().getWindow().hide();
	}
	@Override
	public String getIdController() throws RemoteException{
		return idController;
	}

	public void setIdController(String idController) {
		this.idController = idController;
	}
	
	
    

}
