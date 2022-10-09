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
    private TicTacViewController ticTacViewController;
    private String[][] playField = new String[3][3];
    private int currentPlayer;

    public int getNextPlayer()
    {
        if (currentPlayer == 0){
            return 1;
        }
        return 0;
    }

    public void setCurrentPlayer(int player){
        this.currentPlayer = player;
    }

    public boolean play(int row, int col)
    {
        if (isGameOver()){
            return false;
        }
        else {
            if (playField[row][col] == null){
                String xOrO = currentPlayer == 0 ? "X" : "O";
                updatePlayField(row,col,xOrO);
                //currentPlayer = getNextPlayer();
                return true;
            }
            else {
                return false;
            }
        }
    }

    public boolean isGameOver() {
        if (checkHorizontally() || checkVertically() || checkTopLeftToBottomRight() || checkBottomLeftToTopRight() || isBoardFull()){
            return true;
        }
        else {
            return false;
        }
    }

    public int getWinner()
    {
        if (isBoardFull() && !checkHorizontally() && !checkVertically() && !checkTopLeftToBottomRight() && !checkBottomLeftToTopRight()){
            return -1;
        }
        else{
            return getNextPlayer();
        }
    }

    public void newGame()
    {
        playField = new String[3][3];
    }

    public void updatePlayField(int row, int col, String symbol){
        playField[row][col] = symbol;
    }

    public String[][] getPlayField(){
        return playField;
    }

    private boolean checkHorizontally(){
        for (int r = 0; r < playField.length; r++) {
            for (int c = 0; c < 1; c++) {
                if (playField[r][c] == playField[r][c + 1] && playField[r][c] == playField[r][c + 2] && playField[r][c] != null) {
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
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkTopLeftToBottomRight(){
        if (playField[0][0] == playField[1][1] && playField[0][0] == playField[2][2] && playField[0][0] != null) {
            return true;
        }
        return false;
    }

    private boolean checkBottomLeftToTopRight(){
        if (playField[0][2] == playField[1][1] && playField[0][2] == playField[2][0] && playField[0][2] != null) {
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
}

