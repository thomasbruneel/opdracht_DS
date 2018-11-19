package client.Tasks;

import client.GameController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.layout.GridPane;
import static client.ClientMain.*;

import applicationServer.ActiveGame;

public class GameRefreshTask extends Task{
	
	private GameController gameController;
	
	
	public GameRefreshTask(GameController gc){
		this.gameController=gc;
		
	}

	@Override
	protected synchronized Object call() throws Exception {
		while(!isCancelled()){
			//System.out.println("thread");
			ActiveGame activeGame=asi.getChangedActiveGame(gameId);
			if (activeGame!=null) Platform.runLater(() -> gameController.refreshBord(activeGame));
            wait(2000);
		}
		return null;
	}

	@Override
	protected void cancelled() {
		System.out.println("Gestopt");
		super.cancelled();
	}
}
