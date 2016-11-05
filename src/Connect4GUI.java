/**
 * Lukasz Juraszek
 * Sean Schlaefli
 * CS151 Homework 5
 * Connect4GUI.java
 * GUI representing the Connect4 game
 * compiles
 * working/tested
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Label;

public class Connect4GUI extends Application {


    private static final int TILE_SIZE = 80;
    private static int columns;
    private static int rows;
    private Connect4Presenter presenter;
    private Disc[][] grid;
    private Pane discRoot;
    private boolean redMove;

	public Connect4GUI(Connect4Presenter presenter){
		this.presenter = presenter;
		columns = presenter.getColumns();
		rows = presenter.getRows();
		redMove = true;
		grid = new Disc[columns][rows];
		discRoot = new Pane();
	}    

	private Parent createContent() {
	    BorderPane ui = new BorderPane();
	    Pane root = new Pane();	    
	    root.getChildren().add(discRoot);
	    Shape gridShape = makeGrid();
	    root.getChildren().add(gridShape);
	    root.getChildren().addAll(makeColumns());
	    ui.setCenter(root);
	    ui.setLeft(makePlayerBox("Player 1", Color.RED));
	    ui.setRight(makePlayerBox("Player 2", Color.YELLOW));
	    return ui;
	}

    private Shape makeGrid() {
	Shape shape = new Rectangle((columns + 1) * TILE_SIZE, (rows + 1) * TILE_SIZE);
	Circle circle;
	for (int y = 0; y < rows; y++) {
	    for (int x = 0; x < columns; x++) {
		circle = new Circle(TILE_SIZE / 2);
		circle.setCenterX(TILE_SIZE / 2);
		circle.setCenterY(TILE_SIZE / 2);
		
		circle.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
		circle.setTranslateY(y * (TILE_SIZE + 5) + TILE_SIZE / 4);
		
		shape = Shape.subtract(shape,  circle);
	    }
	}
	
	Light.Distant light = new Light.Distant();
	light.setAzimuth(45.0);
	light.setElevation(30.0);
	
	Lighting lighting = new Lighting();
	lighting.setLight(light);
	lighting.setSurfaceScale(5.0);
	
	shape.setFill(Color.BLUE);
	shape.setEffect(lighting);
	return shape;
    }

    private List<Rectangle> makeColumns() {
	List<Rectangle> list = new ArrayList<>();
	for (int x = 0; x < columns; x++) {
	    Rectangle rect = new Rectangle(TILE_SIZE, (rows + 1) * TILE_SIZE);
	    rect.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
	    rect.setFill(Color.TRANSPARENT);
	    
	    rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 50, 0.3)));
	    rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));
	    final int column = x;
	    rect.setOnMouseClicked(e -> {
		    if(presenter.makeMove(column)){
			placeDisc(new Disc(redMove), column);
			String c = presenter.isOver();
			
			if (!c.equals("c"))
			    gameOver(c);
		    }
		});
	    list.add(rect);
	}
	return list;
    }


    private Pane makePlayerBox(String label, Color color){
	VBox display = new VBox();
	display.setPrefWidth(100);
	display.setAlignment(Pos.BASELINE_CENTER);
	Text player = new Text(label);
	player.setFont(new Font(20));
	player.setFill(color);
	BackgroundFill fill = new BackgroundFill(Color.BLACK, null, null);
	display.setBackground(new Background(fill));
	display.getChildren().add(player);
	return display;	    
    }

    private Text generateResult(String outcome, Color color){
	Text result = new Text();
	result.setText(outcome);
	result.setFill(color);
	result.setFont(new Font(20));
	return result;
	    
    }

    private void gameOver(String c) {
	final Stage gameOver = new Stage();
	// blocks all events to other windows
	gameOver.initModality(Modality.APPLICATION_MODAL);
	Text result = new Text();
	if (!c.equals("Draw")){
	    if(c.equals("Red")){	
		result = generateResult("Player 1 has won the game.", Color.RED);
	    }
	    else{
		result = generateResult("Player 2 has won the game.", Color.YELLOW);
	    }
	} else{
	    result = generateResult("The game ended in a " + c + ".", Color.WHITE);
	}
	VBox dialog = new VBox();
	dialog.setAlignment(Pos.CENTER);
	dialog.getChildren().add(result);
	BackgroundFill fill = new BackgroundFill(Color.BLACK, null, null);
	dialog.setBackground(new Background(fill));
	Scene dialogScene = new Scene(dialog, 300, 200);
	gameOver.setScene(dialogScene);
	gameOver.setOnCloseRequest(e -> System.exit(0));
	gameOver.show();	
    }

    
    private void placeDisc(Disc disc, int column) {
	int row = rows - 1;
	do {
	    if (!getDisc(column, row).isPresent())
		break;
	    row--;
	} while (row >= 0);
	
	if (row < 0)
	    return;
	
	grid[column][row] = disc;
	discRoot.getChildren().add(disc);
	redMove = !redMove;
	disc.setTranslateX(column * (TILE_SIZE + 5) + TILE_SIZE / 4);
	TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), disc);
	animation.setToY(row * (TILE_SIZE + 5) + TILE_SIZE / 4);
	animation.play();
    }
    
    private Optional<Disc> getDisc(int inputColumn, int inputRow) {
	if (inputColumn < 0 || inputColumn >= columns
	    || inputRow < 0 || inputRow >= rows)
	    return Optional.empty();
	
	return Optional.ofNullable(grid[inputColumn][inputRow]);
    }
    
    private static class Disc extends Circle {
	private final boolean red;
	
	public Disc(boolean red) {
	    super(TILE_SIZE / 2, red ? Color.RED : Color.YELLOW);
	    this.red = red;
	    
	    setCenterX(TILE_SIZE / 2);
	    setCenterY(TILE_SIZE / 2);
	}
    }
    @Override
    public void start(Stage stage) throws Exception {
	stage.setScene(new Scene(createContent()));
	stage.setTitle("Connect4");
	stage.show();
    }
    
}