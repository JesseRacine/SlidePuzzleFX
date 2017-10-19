package Application;

import javafx.animation.TranslateTransition;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Stack;

public class CanvasController {
	
	private Pane pane;
	private Board board;
    private Rectangle backgroundRect;
	public static final int SIZE = 75;
    private Group[] numberSquares;

    private Label statusLabel;
    private SolveTask solver;
    private Stack<BoardSet> solveStack;


	public CanvasController(Pane cc, Label lab){
		this.pane = cc;
        statusLabel = lab;
        solver = null;
        solveStack = null;
        backgroundRect = new Rectangle(0,0,300,300);
        backgroundRect.setFill(Color.ORANGE);
        pane.getChildren().add(backgroundRect);

		board = Board.solvedBoard();

        numberSquares = new Group[15];
		int x = 0, y = 0;

		for( int i = 0; i < 15; i++)
		{
			x = (i % 4 ) * SIZE;
			y = (i / 4 ) * SIZE;

			Group g = new Group();
			Rectangle r = new Rectangle();
			Text t = new Text();
			setNumberSquareProperties(r,t,i+1,SIZE);
			g.getChildren().add(r);
			g.getChildren().add(t);
			g.setTranslateX(x);
            g.setTranslateY(y);
            //g.setLayoutX(x);
           // g.setLayoutY(y);
			pane.getChildren().add(g);
            g.setUserData(new Integer(i+1));
            g.setOnMouseClicked(e -> numberSquareClicked(e) );
            numberSquares[i] = g;

		}
	}

    private void numberSquareClicked(MouseEvent e){
        Group g = (Group) e.getSource(); // gets the group ID that was clicked on
        int i = (Integer) g.getUserData(); // gets the user data that was set
        System.out.println("Clicked " + i);
        System.out.println("coords " + g.getTranslateX() + " " + g.getTranslateY());


        Board b = board.moveTile(i);
        if (b != null){
            // get location of blank space
            int blankPos = board.getBlank();
            int blankX = (blankPos % 4 ) * SIZE;
            int blankY = (blankPos / 4 ) * SIZE;

            doAnimation(g, blankX, blankY, false);
            board = b;
        }

    }

    private void doAnimation(Group g, int x , int y, boolean solutionAnimate){
        TranslateTransition tt = new TranslateTransition(Duration.millis(250),g);
        tt.setCycleCount(1);
        tt.setToX(x);
        tt.setToY(y);
        if( solutionAnimate)
            tt.setOnFinished(e -> animationDone(e));

        tt.play();
    }

	private void setNumberSquareProperties(Rectangle rect, Text text, int value,int size){
		rect.setX(0);
		rect.setY(0);
		rect.setHeight(size);
		rect.setWidth(size);
		rect.setFill(Color.LIGHTGRAY);
		rect.setStroke(Color.BLACK);
		rect.setVisible(true);


		int tx = 0;
		int ty = 0; // text x and y
		if( value  < 10)
			tx = (size/2) - 5;
		else
			tx = (size/2) - 10;
		ty  = (size/2) + 7;


		text.setFont(Font.font("Times", FontWeight.BOLD, 20.0));
		text.setFill(Color.BLACK);
		text.setText(""+value);
		text.setX( tx);
		text.setY(ty);
	}

    public void scramble() {
        Board b = Board.scrambledBoard();
        int[] bc = b.getConfig();
        for(int i = 0; i < 16; i++){
            if( bc[i] != 0) {// if not a blank position
                int id = bc[i] - 1;
                int x = (i % 4 ) * SIZE;
                int y = (i / 4 ) * SIZE;
                numberSquares[id].setTranslateX(x);
                numberSquares[id].setTranslateY(y);
            }
        }

        board = b;
        System.out.println(board);
    }


    public void solve(double solveLevel){
        if( solver != null)
            return;
        statusLabel.setText("Solving...");
        BoardSet.weight = solveLevel / 10.0;
        solver  = new SolveTask(board);
        solver.setOnSucceeded( e -> solveFinished(e) );
        solver.setOnCancelled( e -> solveCancelled(e));
        Thread th = new Thread(solver);
        th.setDaemon(true);
        th.start();

    }

    private void solveFinished(WorkerStateEvent value){
        System.out.println("Solve finished");
        solveStack = solver.getValue();
        int size = solveStack.size() - 1;
        solver = null;
        statusLabel.setText("Solve Finished. " + size + " moves left");
        solveStack.pop();
        startSolveAnimation();

    }

    private void startSolveAnimation(){
        BoardSet s;
        if (solveStack != null && solveStack.size() != 0){
            s = solveStack.pop();
        }
        else{
            solveStack = null;
            statusLabel.setText("Done");
            return;
        }

        int from = s.board.getLastTileMoved();
        int pos = s.board.getTilePosition(from);
        int posX = (pos % 4) * SIZE;
        int posY = (pos / 4) * SIZE;

        board = s.board;
        doAnimation(numberSquares[from-1], posX, posY, true);
    }


    private void solveCancelled(WorkerStateEvent value){
        if( solver == null)
            return;
        solver = null;
        statusLabel.setText("Solve Stopped" );
    }

    public void stop(){
        if (solver != null){
            solver.cancel();
        }
        if(solveStack != null)
            solveStack = null;
    }

    private void animationDone(ActionEvent e){
        if( solveStack != null) {
            statusLabel.setText("Solve Finished. " + (solveStack.size()) + " moves left");
            startSolveAnimation();
        }
        else{
            statusLabel.setText("Stopped");
        }
    }
}
