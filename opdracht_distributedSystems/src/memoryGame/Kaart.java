package memoryGame;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.Serializable;

public class Kaart extends StackPane implements Serializable{

    private int x,y;
	private boolean omgedraaid;

	private Text text = new Text();
	private Rectangle border;
	
	public Kaart(){
		omgedraaid=false;
	}
	
	public Kaart(int x, int y, int waarde){
	    this.x = x;
	    this.y = y;
		omgedraaid=false;
		text.setText(String.valueOf(waarde));
	}

	public boolean isOmgedraaid() {
		return omgedraaid;
	}

	public void setOmgedraaid(boolean omgedraaid) {
		this.omgedraaid = omgedraaid;
	}
	
	public void constructRectangle(int size){
	    border = new Rectangle(size-1,size-1);
	    getChildren().addAll(border,text);
	    setTranslateX(x*size);
	    setTranslateY(y*size);
    }


    public int getWaarde() {
	    return Integer.parseInt(text.getText());
    }
}
