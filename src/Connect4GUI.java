import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Connect4GUI extends Application {


	private static int TILE_SIZE = 80;
	private static int columns;
	private static int rows;
	private Connect4Presenter presenter;
	private boolean redMove = true;
	private Disc[][] grid;
	private Pane discRoot;


	public Connect4GUI(Connect4Presenter presenter){
		this.presenter = presenter;
		columns = presenter.getColumns();
		rows = presenter.getRows();
		grid = new Disc[columns][rows];
		discRoot = new Pane();
	}    

	private Parent createContent() {
		Pane root = new Pane();
		root.getChildren().add(discRoot);
		Shape gridShape = makeGrid();
		root.getChildren().add(gridShape);
		root.getChildren().addAll(makeColumns());
		return root;
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
					else
						System.out.println(c);
				}
			});
			list.add(rect);
		}
		return list;
	}

	private void gameOver(String c) {
		if (!c.equals("Draw"))
			System.out.println(c + " has won the game.");
		else
			System.out.println("The game ended in DRAW");
		System.exit(0);
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