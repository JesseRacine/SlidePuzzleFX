package Application;

import java.util.Date;
import java.util.Random;

// An immutable representation of the 15 numbered tiles in the slide puzzle game

public class Board
{
	private int[] board;
	private int blank;

	private int lastTileMoved;  // the number (1 - 15) of the last tile moved


	private Board(  )	{	}
	
	public Board( Board b)
	{
		board = new int[16];
		for( int i = 0; i < 16; i++)
			board[i] = b.board[i];
		
		blank = b.blank;
		lastTileMoved = b.lastTileMoved;
	}
	
	public static Board solvedBoard()
	{
		Board b = new Board();
		b.board = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
		b.blank = 15;
		b.lastTileMoved = 0;
		return b;

	}
	
	public static Board scrambledBoard()
	{		
		Date d = new Date();
		long t = d.getTime();
		Random rand = new Random(t);		
		
		int moveCount = 0;
		
		Board b = Board.solvedBoard();
		
		while( moveCount < 200)
		{
			int m = rand.nextInt(4);
			Board temp = null;
			
			switch(m)
			{
				case 0:
					temp = b.moveUp();
					if( temp != null)
						b = temp;
				break;
					
				case 1:
					temp = b.moveDown();
					if( temp != null)
						b = temp;
				break;
				
				case 2:
					temp = b.moveLeft();
					if( temp != null)
						b = temp;
				break;
					
				case 3:		
					temp = b.moveRight();
					if( temp != null)
						b = temp;
				break;				
			}
			
			if( temp != null)
				moveCount++;			
		}	

		b.lastTileMoved = 0;
		return b;		
	}
	
	public Board moveUp()
	{
		// Move the blank 'up' which really moves the tile above the blank down
		if( blank / 4 == 0)
		return null;   // There is no tile above the blank spot		
		
		Board b = new Board(this);
		b.swap(blank, blank - 4);
		b.blank = blank - 4;

		b.lastTileMoved = board[blank-4];
		return b;
	}
	
	public Board moveDown()
	{
		// Moves the blank 'down' which really moves the tile below it up
		
		if( blank / 4 == 3)
			return null;
		
		Board b = new Board(this);
		b.swap(blank, blank + 4);
		b.blank = blank + 4;
		b.lastTileMoved = board[blank+4];
		
		return b;
	}
	
	public Board moveLeft()
	{
		// Moves the blank 'left'
		
		if( blank % 4 == 0)
			return null;
		
		Board b = new Board(this);
		b.swap(blank, blank -1);
		b.blank = blank - 1;
		b.lastTileMoved = board[blank-1];
		return b;			
	}
	
	public Board moveRight()
	{
		// Moves the blank 'right
		
		if( blank % 4 == 3)
			return null;
		
		Board b = new Board(this);
		b.swap(blank, blank + 1);
		b.blank = blank + 1;
		b.lastTileMoved = board[blank+1];
		return b;			
	}
	
	private void swap( int i, int j)
	{
		int temp = board[i];
		board[i] = board[j];
		board[j] = temp;
	}
	
	public Board moveTile( int t)
	{
		// takes the number tile that needs to be moved as a parameter
		// first figure out the position of the number tile on the board
		int i;
		for( i = 0; board[i] != t; i++)
			;


		if( blank-1 == i)
			return moveLeft();
		
		if( blank+1 == i)
			return moveRight();
		
		if( blank + 4 == i)
			return moveDown();
		
		if( blank - 4 == i)
			return moveUp();		
		
		return null;
	}
	
	public int getBlank(){ return blank; }
	
	public int getManhattanSum()
	{
		// Returns the Manhattan sum of this board configuration
		
		int manTotal = 0;
		for( int i = 0; i < 16; i++)
		{
			if( board[i] != 0)
			{
				int j = board[i] - 1;
				manTotal += manhattan(j, i);
			}				
		}		
		
		return manTotal;		
	}
	
	private int manhattan( int from, int to)
	{		
		// Returns the manhattan value of square from to square to
		int x, xx, y, yy;
		
		x = from % 4;
		y = from / 4;
		
		xx = to % 4;
		yy = to / 4;
		
		return ( Math.abs(x-xx) + Math.abs(y-yy));			
	}
	
	public boolean isSolution()
	{		
		for( int i = 0; i < 15; i++)
		{
			if( board[i] != i+1)
				return false;
		}
		
		return true;
	}
	
	public String toString()
	{
		String s = "{ ";
		for( int i = 0; i < 16; i++)
			s += board[i] + " ";
		
		s += "}";
		
		return s;
		
	}
	

	
	public int[] getConfig()
	{
		int[] b = new int[16];
		
		for( int i = 0; i < 16; i++)					
			b[i] = board[i];	
		
		return b;
	}
	
	
	public int getTilePosition(int tile){
		for( int i = 0; i < 16; i++){
			if (board[i] == tile)
				return i;
		}
		return -1;
	}

	public int getLastTileMoved(){
		return lastTileMoved;
	}

}
