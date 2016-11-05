/**
 * Lukasz Juraszek
 * Sean Schlaefli
 * CS151 Homework 5
 * Connect4.java
 * Connect4 game
 * compiles
 * working/tested
 */


import javafx.application.Application;
import javafx.stage.Stage;

public class Connect4 extends Application{

    public static int boardSize;
    public static int winSize;
    
	public static void main(String [] args){
		if (args.length == 0) {
			boardSize = 7;
			winSize = 5;
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
