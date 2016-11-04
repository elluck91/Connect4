/**
 * Sean Schlaefli
 * seanschlaefli@yahoo.com
 * CS151 Assignment 5
 * Connect4.java
 * Create the main GUI and launch the application
 * compiles
 * unfinished
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class Connect4 extends Application{

	public static int boardSize;
	public static int winSize;

	public static void main(String [] args){
		if (true) {
			boardSize = 7;
			winSize = 4;
			launch(args);
		

		} else {
			boardSize = Integer.parseInt(args[0]);
			winSize = Integer.parseInt(args[1]);
			launch(args);
		}	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GameLogic g = new GameLogic(boardSize, winSize);
		Connect4GUI app = new Connect4GUI(new Connect4Presenter(g));
		app.start(primaryStage);
	}

}
