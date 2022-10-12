package tictactoe.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tictactoe.bll.GameBoard;
import tictactoe.bll.IGameModel;

import java.io.IOException;

public class IntroScreenController {
    IGameModel game;
    @FXML
    Button btnSinglePlayer;
    @FXML
    Button btnMultiPlayer;

    public void initialize(){
        game = new GameBoard();
    }

    public void switchToGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/TicTacView.fxml"));
        Parent root = loader.load();

        TicTacViewController ticTacViewController = loader.getController();
        ticTacViewController.setModel((GameBoard) game);
        game.setController(ticTacViewController);

        Button selected = (Button) actionEvent.getSource();
        if (selected.getId() == btnSinglePlayer.getId()) {
            game.updateIsSinglePlayer(true);
        }
        else {
            game.updateIsSinglePlayer(false);
        }

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Tic Tac Toe");
        stage.centerOnScreen();
        stage.show();
    }
}
