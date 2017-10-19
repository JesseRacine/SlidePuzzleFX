package Application;

import javafx.scene.Group;

public class Position {
private int x, y;
	
	private Group g;
	
	public static final int SIZE = 75;
	
	public Position(int x, int y, Group g)
	{
		this.x = x;
		this.y = y;
		this.g = g;
	}

	
	public boolean isInside( int xx, int yy )
	{
		return ( xx >= x && xx <= x+SIZE && yy >= y && yy <= y+SIZE);		
	}

    /*
	public void assignSquare(NumSquare n) { num = n ; }
	
	public NumSquare popSquare()
	{
		NumSquare temp = num;
		num = null;
		return temp;
	}
	
	public int getNum()
	{ 
		if( num != null)
			return num.getValue(); 
		
		return 0;
	}

	*/
	public int getX(){ return x; }
	
	public int getY(){ return y; }
}
