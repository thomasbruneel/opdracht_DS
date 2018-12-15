package dispatcher;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import applicationServer.ActiveGame;
import applicationServer.TokenGenerator;
import client.Tasks.LobbyRefreshTask;
import interfaces.DispatcherInterface;
import interfaces.LobbyControllerInterface;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import static client.ClientMain.*;

public class DispatcherController extends UnicastRemoteObject implements Serializable {
	Dispatcher dispatcher;
	
    public DispatcherController( ) throws RemoteException {
    	dispatcher=new Dispatcher();
    }
    
    @FXML
    private TableView<DatabankServer> uiTabelDB;
    
    @FXML
    private TableColumn<DatabankServer, String> uiTabelDBIP;

    @FXML
    private TableColumn<DatabankServer, Integer> uiTabelDBPort;
    
    @FXML
    private TableColumn<DatabankServer, Boolean> uiTabelDBOnline;
    
    
    @FXML
    private TableView<AppServer> uiTabelAPP;
    
    @FXML
    private TableColumn<AppServer, String> uiTabelAPPIP;

    @FXML
    private TableColumn<AppServer, Integer> uiTabelAPPPort;
    
    @FXML
    private TableColumn<AppServer, Integer> uiTabelAPPConnectDB;
    

    
    @FXML
    public void initialize() throws Exception{
    		dispatcher.initDispatcher();
    		updateTables();

    }



	private void updateTables() {

        uiTabelDB.getItems().setAll(dispatcher.getDatabankServers());
        uiTabelDBIP.setCellValueFactory(new PropertyValueFactory<>("ipAdres"));
        uiTabelDBPort.setCellValueFactory(new PropertyValueFactory<>("poortnummer"));
        uiTabelDBOnline.setCellValueFactory(new PropertyValueFactory<>("online"));
        
        uiTabelAPP.getItems().setAll(dispatcher.getAppServers());
        uiTabelAPPIP.setCellValueFactory(new PropertyValueFactory<>("ipAdres"));
        uiTabelAPPPort.setCellValueFactory(new PropertyValueFactory<>("poortnummer"));
        uiTabelAPPConnectDB.setCellValueFactory(new PropertyValueFactory<>("DBportnummer"));
		
	}

   
	
	public void putDBOffline(){
		DatabankServer ds=uiTabelDB.getSelectionModel().getSelectedItem();
		ds.setOnline(!ds.isOnline());
		updateTables();
		
	}
	
	
	
	public void refreshen(){
		updateTables();
	}


}
