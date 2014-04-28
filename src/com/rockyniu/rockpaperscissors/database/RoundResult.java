package com.rockyniu.rockpaperscissors.database;

public class RoundResult {
	static final int RESULT_ERROR = -100;

	public enum Choice {
		ROCK(0), 
		PAPER(1), 
		SCISSORS(2),
		ERROR(-1);
		private final int choice;

		private Choice(int choice) {
			this.choice = choice;
		}

		public int getStatus() {
			return this.choice;
		}
		
		public static Choice setChoice(int index){
			switch (index){
			case 0: 
				return Choice.ROCK;
			case 1:
				return Choice.PAPER;
			case 2:
				return Choice.SCISSORS;
			default:
				return Choice.ERROR;
			}
		}		
	}

	/**
	 * get result of two choices: c1 and c2
	 * @param c1, a Choice
	 * @param c2, a Choice
	 * @return 0: draw; 1: c1 win; -1: c1 lose, or RESULT_ERROR.
	 */
	public static int getResult(Choice c1, Choice c2) {
		if (!(c2.equals(Choice.ROCK) || c2.equals(Choice.PAPER) || c2
				.equals(Choice.SCISSORS))) {
			return RESULT_ERROR;
		}
		if (c1.equals(c2))
			return 0;
		switch (c1) {
		case ROCK:
			return c2.equals(Choice.SCISSORS) ? 1 : -1;
		case PAPER:
			return c2.equals(Choice.ROCK) ? 1 : -1;
		case SCISSORS:
			return c2.equals(Choice.PAPER) ? 1 : -1;
		default:
			return RESULT_ERROR;
		}
	}
}
