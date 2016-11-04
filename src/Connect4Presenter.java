/**
 * Sean Schlaefli
 * seanschlaefli@yahoo.com
 * CS151 Homework 5
 * Connect4Presenter.java
 * handle communication between the GUI and the game logic
 * compiles
 * unfinished
 */

public class Connect4Presenter{

	private GameLogic game;
	private Connect4GUI view;
	private char flow;
	
	public Connect4Presenter(GameLogic game){
		// temporary
		// instantiate game here?
		this.game = game;
	}


	/**
	 * Attach the GUI to the view
	 * @param Connect4GUI view
	 */
	public void attachView(Connect4GUI view){
		this.view = view;
	}


	public int getRows(){
		return game.getRows();
	}

	public int getColumns(){
		return game.getColumns();
	}

	
		
	// remaining functions to handle the communication below
	public boolean makeMove(int column){
		// verify the column is valid
		// if it's valid, then update the board in logic
		// and call a GUI method on view to draw the disc
		// now
		return game.makeMove(column);

		/*
	if(game.makeMove(column)){
	    view.updateGUI();
	    if(game.checkForWinner()){
		view.displayWinner();
	    } else{
		view.updateTurn();
		game.updateTurn();
	    }
	} 
		 */
	}


	/**
	 * check the game logic for a winner, if true return to the GUI 
	 * to display the winner, otherwise update the color in the GUI
	 */
	public String isOver(){
		flow = game.isOver();
		if (flow == 'c')
			return "c";
		else if (flow == 'R')
			return "Red";
		else if (flow == 'Y')
			return "Yellow";
		else
			return "Draw";
	}

}