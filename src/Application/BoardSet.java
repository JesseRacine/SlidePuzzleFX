package Application;

/**
 * Created by Jesse on 12/29/2015.
 */
public class BoardSet implements Comparable
{
    // Class that contains algorithm data for the A* system

    public int manhattan = 0; // The A* heuristic for this board
    public int count;
    public Board board;
    public BoardSet link;
    public boolean root;  /* Turn this on to represent the root BoardSet
							   that only represents the starting case */
    public static double weight;

    static{
        weight = 0.0;

    }

    public BoardSet(Board b)
    {
        board = b;
        root = false;
        calcPriority();
    }

    private void calcPriority()
    {
        // The manhattan distances must be figured out
        manhattan = board.getManhattanSum();
    }

    public int compareTo(Object o)
    {
        // Smaller is better
        BoardSet b = (BoardSet) o;

        double pri, pri2;

        pri = weight * count + manhattan;
        pri2 = weight * b.count + b.manhattan;




        if( pri2 > pri)
            return -2;
        if( pri2 == pri)
            return 0;

        return 2;
    }

    public boolean isSolution()
    {
        return board.isSolution();
    }
}
