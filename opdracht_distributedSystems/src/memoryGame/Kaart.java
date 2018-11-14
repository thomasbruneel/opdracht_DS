package memoryGame;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.Serializable;

public class Kaart extends StackPane implements Serializable{

    private int x,y;
	private boolean omgedraaid;

	private SText text = new SText();
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public SText getText() {
		return text;
	}

	public void setText(SText text) {
		this.text = text;
	}

	public void setBorder(Rectangle border) {
		this.border = border;
	}

	public void constructRectangle(int size){
	    border = new Rectangle(size-1,size-1);
	    getChildren().addAll(border,text);
	    setTranslateX(x*size);
	    setTranslateY(y*size);
    }


    public int getWaarde() {
	    if (text != null) {
	        if (!text.getText().equals(""))
            return Integer.parseInt(text.getText());
        }
        return -666;
    }
}
