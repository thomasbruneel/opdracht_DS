package client.Tasks;

import client.GameController;
import javafx.concurrent.Task;
import javafx.scene.layout.GridPane;

public class GameRefreshTask extends Task{
	
	private GridPane gridPane;
	private GameController gameController;
	
	
	public GameRefreshTask(GridPane gp,GameController gc){
		this.gridPane=gp;
		this.gameController=gc;
		
	}

	@Override
	protected Object call() throws Exception {

		return null;
	}
	

}
