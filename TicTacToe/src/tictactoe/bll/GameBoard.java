/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.bll;

import tictactoe.gui.controller.TicTacViewController;

import java.util.Arrays;

public class GameBoard implements IGameModel
{
    TicTacViewController ticTacViewController = new TicTacViewController();
    private String[][] playField = new String[3][3];
    private int currentPlayer = 1;
    String winner;

    public int getNextPlayer()
    {
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

                for (int r = 0; r < playField.length; r++){
                    for (int c = 0; c < playField[0].length; c++){
                        if (playField[r][c] == null){
                            System.out.print("_,");
                        }
                        else{
                            System.out.print(playField[r][c] + ",");
                        }
                    }
                    System.out.println();
                }
                System.out.println();

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

    public int getWinner()
    {
        if (isGameOver()){
            if (isBoardFull() && !checkHorizontally() && !checkVertically() && !checkTopLeftToBottomRight() && !checkBottomLeftToTopRight()){
                return -1;
            }
            else{
                return winner == "X" ? 0 : 1;
            }
        }
        else{
            return 0;
        }
    }

    public void newGame()
    {
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
                    new WinningFields(r,c,r,c+2);
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
                    new WinningFields(r,c,r+2,c);
                    winner = playField[r][c];
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkTopLeftToBottomRight(){
        if (playField[0][0] == playField[1][1] && playField[0][0] == playField[2][2] && playField[0][0] != null) {
            new WinningFields(0,0,2,2);
            winner = playField[0][0];
            return true;
        }
        return false;
    }

    private boolean checkBottomLeftToTopRight(){
        if (playField[0][2] == playField[1][1] && playField[0][2] == playField[2][0] && playField[0][2] != null) {
            new WinningFields(0,2,2,0);
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

    public void playAI(){
        int bestMoveRow;
        int bestMoveCol;
        for (int r = 0; r < playField.length; r++){
            for (int c = 0; c < playField[0].length; c++) {
                String[][] playFieldWithMove = playField;
                if (playFieldWithMove[r][c] == null){
                    playFieldWithMove[r][c] = "O";
                    if (isGameOver() && winner == "O"){

                    }
                }
            }
        }
    }

    private class WinningFields{
        double startR;
        double startC;
        double endR;
        double endC;

        public WinningFields(int startR, int startC, int endR, int endC){
            this.startR = startR*50;
            this.startC = startC*50;
            this.endR = endR*50;
            this.endC = endC*50;
            ticTacViewController.drawLine(this.startR, this.startC, this.endR, this.endC);
        }
    }
}

