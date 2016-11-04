
public class Connect4 {

	public static void main(String[] args) {
		Connect4GUI app;
		Connect4Presenter presenter;
		Logic logic;
		if (args.length == 0) {
			logic = new Logic("7", 4);
			presenter = new Presenter(logic);
			app = new Connect4GUI(presenter);
		}
		else {
			logic = new Logic(args[0], args[1]);
			presenter = new Presenter(logic);
			app = new Connect4GUI(presenter);
		}
	}

}
