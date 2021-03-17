/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		for(int i=1; i<= nPlayers; i++) {
			display.printMessage(playerNames[i-1]+ "s turn! Click  \" Roll dice \"  button to roll the dice.");
			display.waitForPlayerToClickRoll(i);
		}

		rgen.setSeed(1);
		for(int j=0; j<=currentDice.length-1; j++){
			currentDice[j] =rgen.nextInt(1, 6);
		}

		display.displayDice(currentDice);
		display.printMessage("Select the dice you want to re-roll and click \" Roll Again\" ");
		display.waitForPlayerToSelectDice();


		for(int k=0; k<=currentDice.length-1; k++) {
			if (display.isDieSelected(k)) {
				currentDice[k] = rgen.nextInt(1, 6);
			}
			display.displayDice(currentDice);
			display.printMessage("Select the dice you want to re-roll and click \" Roll Again\" ");
		}


		for(int k=0; k<=currentDice.length-1; k++){
			if(display.isDieSelected(k)) {
				currentDice[k] = rgen.nextInt(1,6);
			}
			display.displayDice(currentDice);
		}
		display.printMessage("Select a category for this roll ");
		display.waitForPlayerToSelectCategory();



	}

		
/* Private instance variables */
	private int[] currentDice = new int[N_DICE];
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
