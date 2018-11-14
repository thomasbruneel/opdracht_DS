package client;

import static client.ClientMain.asi;

import java.util.List;

import applicationServer.ActiveGame;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;

public class LobbyRefreshTask extends Task {
	private TableView<ActiveGame> uiTabel;
	private LobbyController lc;
	
	public LobbyRefreshTask(TableView<ActiveGame> uiTabel,LobbyController lc){
		this.uiTabel=uiTabel;
		this.lc=lc;
		
	}

	@Override
	protected Object call() throws Exception {

		while(!isCancelled()){
			List<ActiveGame> newList = asi.getActiveGames();
			lc.refresh(newList);
			
		}
		return null;
	}


}
