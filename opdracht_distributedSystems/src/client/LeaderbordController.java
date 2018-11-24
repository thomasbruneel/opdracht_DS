package client;

import static client.ClientMain.asi;
import static client.ClientMain.gameId;
import static client.ClientMain.openUIScreen;
import static client.ClientMain.spectateMode;
import static client.ClientMain.userName;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import applicationServer.ActiveGame;
import applicationServer.Leaderbord;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LeaderbordController {
	
	//leaderbord tabel view
    @FXML
    private TableView<Leaderbord> uiTabel;
    
    @FXML
    private TableColumn<Leaderbord, String> uiTabelUser;

    @FXML
    private TableColumn<Leaderbord, Integer> uiTabelWins;
    
    
    @FXML
    public void initialize() throws RemoteException{
    	List<Leaderbord>leaderbordList=new ArrayList<Leaderbord>();
    	
    	leaderbordList=asi.getLeaderBord();
        uiTabel.getItems().setAll(leaderbordList);
        uiTabelUser.setCellValueFactory(new PropertyValueFactory<>("userName"));
        uiTabelWins.setCellValueFactory(new PropertyValueFactory<>("wins"));

    }
    
	public void backToLobby() throws RemoteException{
		uiTabel.getScene().getWindow().hide();
    	openUIScreen("lobbyUI.fxml");
    }
    

}
