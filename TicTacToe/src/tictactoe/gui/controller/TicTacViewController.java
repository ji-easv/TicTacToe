/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import tictactoe.bll.GameBoard;
import tictactoe.bll.IGameModel;

/**
 *
 * @author Stegger
 */
public class TicTacViewController implements Initializable
{
    @FXML
    private Label lblPlayer;

    @FXML
    private GridPane gridPane;
    
    private static final String TXT_PLAYER = "Player: ";
    private IGameModel game;
    private int player;

    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        try
        {
            player = game.getNextPlayer();
            Integer row = GridPane.getRowIndex((Node) event.getSource());
            Integer col = GridPane.getColumnIndex((Node) event.getSource());
            int r = (row == null) ? 0 : row;
            int c = (col == null) ? 0 : col;

            if(!game.isGameOver()){
                if (game.play(r, c)){
                    Button btn = (Button) event.getSource();
                    String xOrO = player == 0 ? "X" : "O";
                    btn.setText(xOrO);

                    player = game.getNextPlayer();
                    setPlayer();
                }
            }

            int winner = game.getWinner();
            displayWinner(winner);
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleNewGame(ActionEvent event)
    {
        game.newGame();
        clearBoard();;
        setPlayer();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        game = new GameBoard();
        game.getCurrentPlayer();
        setPlayer();
    }

    private void setPlayer()
    {
        String xOrO = player == 0 ? "X" : "O";
        lblPlayer.setText(TXT_PLAYER + player + " (" + xOrO + ")");
    }

    private void displayWinner(int winner)
    {
        if (game.isGameOver()){
            String message = "";
            switch (winner)
            {
                case -1:
                    message = "It's a draw :-(";
                    break;
                default:
                    message = "Player " + winner + " wins!!!";
                    break;
            }
            lblPlayer.setText(message);
        }
    }

    private void clearBoard()
    {
        for(Node n : gridPane.getChildren())
        {
            Button btn = (Button) n;
            btn.setText("");
        }
    }
}
