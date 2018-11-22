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
    
    @FXML
    public void initialize() throws RemoteException{
    	//uiTabel.getItems().remove(true);
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
        	voegAfbeeldingenToe(activeGame.getTheme());
        	openUIScreen("gameUI.fxml");
        	uiLogoutButton.getScene().getWindow().hide();
        	//task.cancel();
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
        voegAfbeeldingenToe(activeGame.getTheme());
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

	@Override
	public void updateLobby(ArrayList<ActiveGame> games) throws RemoteException {
		listActiveGames=games;
		uiTabel.getItems().setAll(games);
		
	}
    

}
