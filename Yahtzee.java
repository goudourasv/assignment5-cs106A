/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

import java.util.HashMap;

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
//        for(int i=1; i<= nPlayers; i++){
//            player =i;
//            playTurn(i);
//        }
        playTurn(1);
    }

    private void playTurn(int player) {
        display.printMessage(playerNames[player - 1] + "s turn! Click  \" Roll dice \"  button to roll the dice.");
        display.waitForPlayerToClickRoll(1);


        rgen.setSeed(1);
        for (int j = 0; j <= currentDice.length - 1; j++) {
            currentDice[j] = rgen.nextInt(1, 6);
        }

        display.displayDice(currentDice);
        display.printMessage("Select the dice you want to re-roll and click \" Roll Again\" ");
        display.waitForPlayerToSelectDice();

        for (int k = 0; k <= currentDice.length - 1; k++) {
            if (display.isDieSelected(k)) {
                currentDice[k] = rgen.nextInt(1, 6);
            }
            display.displayDice(currentDice);
        }
        display.printMessage("Select the dice you want to re-roll and click \" Roll Again\" ");
        display.waitForPlayerToSelectDice();

        for (int k = 0; k <= currentDice.length - 1; k++) {
            if (display.isDieSelected(k)) {
                currentDice[k] = rgen.nextInt(1, 6);
            }
            display.displayDice(currentDice);
        }

        display.printMessage("Select a category for this roll ");
        int selectedCategory = display.waitForPlayerToSelectCategory();
        int score = setPointsPerCategory(selectedCategory, currentDice);
        boolean matchCategory = YahtzeeMagicStub.checkCategory(currentDice, selectedCategory);

        setScore(matchCategory, selectedCategory,score);
        //TODO check category method
        display.updateScorecard(selectedCategory, 1, playerScore[selectedCategory]);

    }

    private void setScore(boolean matchCategory, int selectedCategory, int score) {
        if (matchCategory == true) {
            playerScore[selectedCategory] = score;
        } else {
            playerScore[selectedCategory] = 0;
        }
    }

    private int setPointsPerCategory(int selectedCategory, int[] dice) {
        int points = 0;
        if (selectedCategory == ONES) {
            points = sumOfNumber(dice,1);

        } else if (selectedCategory == TWOS) {
            points = sumOfNumber(dice,2);

        } else if (selectedCategory == THREES) {
            points = sumOfNumber(dice,3);

        } else if (selectedCategory == FOURS) {
            points = sumOfNumber(dice,4);

        } else if (selectedCategory == FIVES) {
            points = sumOfNumber(dice,5);

        } else if (selectedCategory == SIXES) {
            points = sumOfNumber(dice,6);

        } else if (selectedCategory == THREE_OF_A_KIND) {
            points = sumOfAllTheDice(dice);

        } else if (selectedCategory == FOUR_OF_A_KIND) {
            points = sumOfAllTheDice(dice);

        } else if (selectedCategory == FULL_HOUSE) {
            points = 25;

        } else if (selectedCategory == SMALL_STRAIGHT) {
            points = 30;

        } else if (selectedCategory == LARGE_STRAIGHT) {
            points = 40;

        } else if (selectedCategory == YAHTZEE) {
            points = 50;

        } else if (selectedCategory == CHANCE) {
            points = sumOfAllTheDice(dice);
        }
        return points;
    }

    private int sumOfNumber(int[] dice,int seat){
        int sum=0;
        for (int i = 0; i <= dice.length-1; i++) {
            if(dice[i]== seat){
                sum += dice[i];
            }
        }
        return sum;
    }

    private int sumOfAllTheDice(int[] dice) {
        int sum = 0;
        for (int i = 0; i <= dice.length-1; i++) {
            sum += dice[i];
        }
        return sum;
    }

    /* Private instance variables */

    private Integer[] playerScore = new Integer[N_CATEGORIES];
    private int[] currentDice = new int[N_DICE];
    private int nPlayers;
    private int player;
    private String[] playerNames;
    private YahtzeeDisplay display;
    private RandomGenerator rgen = new RandomGenerator();

}
