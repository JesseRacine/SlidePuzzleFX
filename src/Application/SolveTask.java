package Application;

import javafx.concurrent.Task;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;
/**
 * Created by Jesse on 12/29/2015.
 */
public class SolveTask extends Task<Stack<BoardSet>> {

    private BoardSet startingSet;
    public SolveTask(Board b){
        startingSet = new BoardSet(b);
        startingSet.count = 0;
        startingSet.root = true;
    }

    @Override protected Stack<BoardSet> call() {
        HashMap<String, BoardSet> inactHash = new HashMap<String, BoardSet>();
        PriorityQueue<BoardSet> pri = new PriorityQueue<BoardSet>();

        pri.add(startingSet);
        BoardSet set = null;
        while (true) {
            if (isCancelled())
                return null;
            set = pri.poll();
            if (set.manhattan == 0)
                break;
            Board[] b = new Board[4];
            int count = 0;
            count = findMoves(b, set.board);
            for (int i = 0; i < count; i++) {
                if (inactHash.containsKey(b[i].toString())) {
                    BoardSet newBoard = new BoardSet(b[i]);
                    newBoard.count = set.count+1;
                    BoardSet oldBoard = inactHash.get(b[i].toString());

                    if (newBoard.compareTo(oldBoard) < 0) {
                        inactHash.remove(b[i].toString());
                        newBoard.link = set;
                        inactHash.put(b[i].toString(), newBoard);
                        pri.add(newBoard);
                    }

                } else {
                    BoardSet temp = new BoardSet(b[i]);
                    temp.count = set.count + 1;
                    temp.link = set;
                    pri.add(temp);
                    inactHash.put(b[i].toString(), temp);
                }
            }
        }
        Stack<BoardSet> stack = new Stack<BoardSet>();
        while (set != null) {
            stack.push(set);
            set = set.link;
        }


        return stack;
    }

    private int findMoves(Board arr[], Board state)
    {
        // Find how many branches of moves (four total) can be made
        // from current Board state.  Store the branch moves in the array arr
        int count = 0;

        Board b = state.moveUp();

        if( b != null)
            arr[count++] = b;

        b = state.moveDown();

        if( b != null )
            arr[count++] = b;

        b = state.moveLeft();

        if( b != null )
            arr[count++] = b;

        b = state.moveRight();

        if( b != null )
            arr[count++] = b;

        return count;

    }
}
