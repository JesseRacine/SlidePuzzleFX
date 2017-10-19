package Application;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class NumSquare {
	
public static final int SIZE = 75;
	
	private int value;
	
	private int x, y;	
	
	private Pane cc;

	private Rectangle rect;
    private Text text;
	
	public NumSquare(Pane cc, int val, int xx, int yy)
	{
		x = xx;
		y = yy;
		value = val;
		this.cc = cc;
		rect = new Rectangle(x,y,SIZE,SIZE);
		rect.setFill(Color.LIGHTGRAY);
		rect.setStroke(Color.BLACK);
		rect.setVisible(true);
        cc.getChildren().add(rect);

        int tx = 0;
        int ty = 0; // text x and y
        if( val  < 10)
            tx = x+(SIZE/2) - 5;
        else
            tx = x+(SIZE/2) - 10;
        ty  = y+(SIZE/2) + 7;



        text = new Text();
        text.setFont(Font.font("Times", FontWeight.BOLD, 20.0));
        text.setFill(Color.BLACK);
        text.setText(""+value);
        text.setX( tx);
        text.setY(ty);
        cc.getChildren().add(text);


	}		
	

	
	public int getValue(){ return value; }
	
	public int getX() { return x; }
	
	public int getY(){ return y; }
	
	public void setX(int x){ this.x = x; }
	public void setY(int y){ this.y = y; }	

}
