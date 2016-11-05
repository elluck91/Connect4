/**
 * Lukasz Juraszek
 * Sean Schlaefli
 * CS151 Homework 5
 * Connect4Presenter.java
 * handle communication between the GUI and the game logic
 * compiles
 * working/tested
 */

public class Connect4Presenter{

	private GameLogic game;
	private Connect4GUI view;
	
	public Connect4Presenter(GameLogic game){
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
		return game.makeMove(column);
	}


	/**
	 * check the game logic for a winner, if true return to the GUI 
	 * to display the winner, otherwise update the color in the GUI
	 */
	public String isOver(){	    
	    char flow = game.isOver();
	    // System.out.println(flow);
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