package client.Tasks;

import static client.ClientMain.asi;

import java.util.ArrayList;
import java.util.List;

import applicationServer.ActiveGame;
import client.LobbyController;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;

public class LobbyRefreshTask extends Task {
	private TableView<ActiveGame> uiTabel;
	private LobbyController lc;
	private boolean running=true;
	
	public LobbyRefreshTask(TableView<ActiveGame> uiTabel,LobbyController lc){
		this.uiTabel=uiTabel;
		this.lc=lc;
		
	}

	@Override
	protected synchronized Object call() throws Exception {

		while(!isCancelled()){
			ArrayList<ActiveGame> newList = asi.getActiveGames();
			wait(3000);
			lc.refresh(newList);
			
		}
		return null;
	}

	public boolean getRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	


}
