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
        playerScore = new Integer[N_CATEGORIES][nPlayers];
        display = new YahtzeeDisplay(getGCanvas(), playerNames);
        playGame();
    }

    private void playGame() {
        for(int i =0; i<=12; i++) {
            for(int j=1; j<= nPlayers; j++){
                playTurn(j);
            }
        }
        for(int i=1; i<=nPlayers; i++) {
            calculateScores(true,i);
            displayFinalPlayerScore(i);
        }
    }

    private void playTurn(int player) {
        display.printMessage(playerNames[player - 1] + "'s turn! Click  \" Roll dice \"  button to roll the dice.");
        display.waitForPlayerToClickRoll(player);

//        rgen.setSeed(1);
        for (int i = 0; i <= currentDice.length - 1; i++) {
            currentDice[i] = rgen.nextInt(1, 6);
        }
        display.displayDice(currentDice);
        display.printMessage("Select the dice you want to re-roll and click \" Roll Again\" ");
        display.waitForPlayerToSelectDice();


        for (int i = 0; i <= currentDice.length - 1; i++) {
            if (display.isDieSelected(i)==true) {
                currentDice[i] = rgen.nextInt(1, 6);
            }
        }
        display.displayDice(currentDice);
        display.printMessage("Select the dice you want to re-roll and click \" Roll Again\" ");
        display.waitForPlayerToSelectDice();


        for (int i = 0; i <= currentDice.length - 1; i++) {
            if (display.isDieSelected(i)==true) {
                currentDice[i] = rgen.nextInt(1, 6);
            }
        }
        display.displayDice(currentDice);
        display.printMessage("Select a category for this roll ");


        int selectedCategory = display.waitForPlayerToSelectCategory();
        int score = setPointsPerCategory(selectedCategory, currentDice);
        boolean matchCategory = YahtzeeMagicStub.checkCategory(currentDice, selectedCategory);
        //TODO check category method

        setScore(matchCategory, selectedCategory, score,player);

        display.updateScorecard(selectedCategory, player, playerScore[selectedCategory-1][player-1]);
        calculateScores(false,player);
        displayTotalPlayerScore(player);
    }

    private int calculateUpperScore(int player) {
        int upperScore= 0;
        for(int i=0; i<=5; i++) {
            if(playerScore[i][player-1]!=null) {
                upperScore += playerScore[i][player-1];
            }
        }
        return upperScore;
    }

    private int checkUpperScoreBonus(int upperScore) {
        int upperBonus = 0;
        if (upperScore >= 63) {
            upperBonus = 35;
        }
        return upperBonus;
    }

    private int calculateLowerScore(int player) {
        int lowerScore = 0;
        for(int i=8; i<=14; i++) {
            if (playerScore[i][player-1] != null) {
                lowerScore += playerScore[i][player-1];
            }
        }
        return lowerScore;
    }

    private int calculateTotalPlayerScore(int lowerScore,int upperScore,int upperBonus ) {
        int total =upperScore + lowerScore + upperBonus;
        return total;
    }

    private void updateSumsInScore(int upperScore,int lowerScore,int upperBonus,int total,int player) {
        playerScore[UPPER_SCORE-1][player-1] = upperScore;
        playerScore[LOWER_SCORE-1][player-1] = lowerScore;
        playerScore[UPPER_BONUS-1][player-1] = upperBonus;
        playerScore[TOTAL-1][player-1] = total;

    }

    private void calculateScores(boolean includeBonus,int player){
        int upperBonus =0;
        int upperScore = calculateUpperScore(player);
        int lowerScore = calculateLowerScore(player);
        if (includeBonus){
            upperBonus = checkUpperScoreBonus(upperScore);
        }
        int total = calculateTotalPlayerScore(lowerScore,upperScore,upperBonus);

        updateSumsInScore(upperScore,lowerScore,upperBonus,total,player);
    }

    private void displayTotalPlayerScore(int player) {
        display.updateScorecard(TOTAL, player, playerScore[TOTAL-1][player-1]);
    }

    private void displayFinalPlayerScore(int player) {
        display.updateScorecard( UPPER_SCORE, player, playerScore[UPPER_SCORE-1][player-1]);
        display.updateScorecard( LOWER_SCORE, player, playerScore[LOWER_SCORE-1][player-1]);
        display.updateScorecard( UPPER_BONUS, player, playerScore[UPPER_BONUS-1][player-1]);
        display.updateScorecard( TOTAL, player, playerScore[TOTAL-1][player-1]);
    }


    private void setScore(boolean matchCategory, int selectedCategory, int score,int player) {
        if (matchCategory == true) {
            playerScore[selectedCategory-1][player-1] = score;

        } else {
            playerScore[selectedCategory-1][player-1] = 0;
        }
    }

    private int setPointsPerCategory(int selectedCategory, int[] dice) {
        int points = 0;
        if (selectedCategory == ONES) {
            points = sumOfNumber(dice, 1);

        } else if (selectedCategory == TWOS) {
            points = sumOfNumber(dice, 2);

        } else if (selectedCategory == THREES) {
            points = sumOfNumber(dice, 3);

        } else if (selectedCategory == FOURS) {
            points = sumOfNumber(dice, 4);

        } else if (selectedCategory == FIVES) {
            points = sumOfNumber(dice, 5);

        } else if (selectedCategory == SIXES) {
            points = sumOfNumber(dice, 6);

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

    private int sumOfNumber(int[] dice, int seat) {
        int sum = 0;
        for (int i = 0; i <= dice.length - 1; i++) {
            if (dice[i] == seat) {
                sum += dice[i];
            }
        }
        return sum;
    }

    private int sumOfAllTheDice(int[] dice) {
        int sum = 0;
        for (int i = 0; i <= dice.length - 1; i++) {
            sum += dice[i];
        }
        return sum;
    }

    /* Private instance variables */

    private Integer[][] playerScore;
    private int[] currentDice = new int[N_DICE];
    private int nPlayers;
    private String[] playerNames;
    private YahtzeeDisplay display;
    private RandomGenerator rgen = new RandomGenerator();

}
