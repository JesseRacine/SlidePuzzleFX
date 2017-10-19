package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;


public class SceneController {

	 @FXML
	 private Pane PuzzleCanvas;
	 private CanvasController cc;

	@FXML
	private Button scrambleButton;

	@FXML
	private Button solveButton1;

	@FXML
	private Button stopButton1;

	@FXML
	private Label statusLabel;

	@FXML
	private Slider solveSlider;
	 
	 public SceneController(){		 
	 }

	@FXML
	protected void initialize(){
		cc = new CanvasController(PuzzleCanvas, statusLabel);

	}

	@FXML
	void scramblePressed(ActionEvent event) {
		cc.scramble();
	}

	@FXML
	void solvePressed(ActionEvent event) {
		cc.solve(solveSlider.getValue());

	}

	@FXML
	void stopPressed(ActionEvent event) {
		cc.stop();

	}

}
