/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.bll;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import tictactoe.gui.controller.TicTacViewController;

import java.lang.reflect.Array;
import java.util.Arrays;

public class GameBoard implements IGameModel
{
    TicTacViewController ticTacViewController;
    GridPane gridPane;
    private String[][] playField = new String[3][3];
    private int currentPlayer = 1;
    String winner;
    private boolean singlePlayer = false;

    public int getNextPlayer() {
        if (currentPlayer == 0){
            return 1;
        }
        return 0;
    }

    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public boolean play(int row, int col)
    {
        if (isGameOver()){
            return false;
        }
        else {
            if (playField[row][col] == null){
                currentPlayer = getNextPlayer();
                String xOrO = currentPlayer == 0 ? "X" : "O";
                updatePlayField(row,col,xOrO);

                if (singlePlayer){
                    currentPlayer = getNextPlayer();
                    chooseAIMove();
                }
                return true;
            }
            else {
                return false;
            }
        }
    }

    public boolean isGameOver() {
        return checkHorizontally() || checkVertically() || checkTopLeftToBottomRight() || checkBottomLeftToTopRight() || isBoardFull();
    }

    public int getWinner() {
        if (isGameOver()){
            if (isBoardFull() && !checkHorizontally() && !checkVertically() && !checkTopLeftToBottomRight() && !checkBottomLeftToTopRight()){
                return -1;
            }
            else{
                return winner == "X" ? 0 : 1;
            }
        }
        else{
            return 2;
        }
    }

    public void newGame() {
        playField = new String[3][3];
        currentPlayer = 1;
    }

    public void updatePlayField(int row, int col, String symbol){
        playField[row][col] = symbol;
    }

    private boolean checkHorizontally(){
        for (int r = 0; r < playField.length; r++) {
            for (int c = 0; c < 1; c++) {
                if (playField[r][c] == playField[r][c + 1] && playField[r][c] == playField[r][c + 2] && playField[r][c] != null) {
                    winner = playField[r][c];
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVertically(){
        for (int r = 0; r < 1; r++) {
            for (int c = 0; c < playField.length; c++) {
                if (playField[r][c] == playField[r + 1][c] && playField[r][c] == playField[r + 2][c] && playField[r][c] != null) {
                    winner = playField[r][c];
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkTopLeftToBottomRight(){
        if (playField[0][0] == playField[1][1] && playField[0][0] == playField[2][2] && playField[0][0] != null) {
            winner = playField[0][0];
            return true;
        }
        return false;
    }

    private boolean checkBottomLeftToTopRight(){
        if (playField[0][2] == playField[1][1] && playField[0][2] == playField[2][0] && playField[0][2] != null) {
            winner = playField[0][2];
            return true;
        }
        return false;
    }

    private boolean isBoardFull(){
        int filledCount = 0;
        for (int r = 0; r < playField.length; r++) {
            for (int c = 0; c < playField[0].length; c++){
                if (playField[r][c] == "X" || playField[r][c] == "O"){
                    filledCount++;
                    if (filledCount == 9){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Integer[] chooseAIMove() {
        float bestScore = Float.NEGATIVE_INFINITY;
        Integer[] bestMove = new Integer[2];
        int score;

        for (int r = 0; r < playField.length; r++) {
            for (int c = 0; c < playField[0].length; c++) {
                if (playField[r][c] == null) {
                    playField[r][c] = "O";
                    score = minimax(0, false);
                    playField[r][c] = null;

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = r;
                        bestMove[1] = c;
                    }
                }
            }
        }
        if(!isGameOver()){
            updatePlayField(bestMove[0],bestMove[1], "O");
            Button btn = (Button) ticTacViewController.getNodeByRowColumnIndex(bestMove[0], bestMove[1], ticTacViewController.getGridPane());
            btn.setTextFill(Paint.valueOf("#2642A6"));
            btn.setText("O");
        }

        return bestMove;
    }

    public int minimax(int depth, boolean isMaximizing) {
        int result = getWinner();
        int score = 50;

        //If the winner isn't empty, this will be the last move
        if (result != 2) {
            switch (result) {
                case -1:
                    score = 0;
                    break;
                case 0:
                    score = -10;
                    break;
                case 1:
                    score = 10;
                    break;
            }
            return score;
        }

        if (isMaximizing) {
            float bestScore = Float.NEGATIVE_INFINITY;
            for (int r = 0; r < playField.length; r++) {
                for (int c = 0; c < playField[0].length; c++) {
                    if (playField[r][c] == null) {
                        playField[r][c] = "O";
                        score = minimax(depth+1, false);
                        playField[r][c] = null;
                        bestScore = Integer.max(score, Float.floatToIntBits(bestScore));
                    }
                }
            }
            return Float.floatToIntBits(bestScore);
        }

        else{
            float bestScore = Float.POSITIVE_INFINITY;
            for (int r = 0; r < playField.length; r++) {
                for (int c = 0; c < playField[0].length; c++) {
                    if (playField[r][c] == null) {
                        playField[r][c] = "X";
                        score = minimax(depth+1, true);
                        playField[r][c] = null;
                        bestScore = Integer.min(score, Float.floatToIntBits(bestScore));
                    }
                }
            }
            return Float.floatToIntBits(bestScore);
        }
    }

    public void updateIsSinglePlayer(boolean value){
        this.singlePlayer = value;
    }

    public void setController(TicTacViewController ticTacViewController){
        this.ticTacViewController = ticTacViewController;
    }

}

