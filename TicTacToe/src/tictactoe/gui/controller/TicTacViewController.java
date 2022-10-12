package tictactoe.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import tictactoe.bll.GameBoard;
import tictactoe.bll.IGameModel;

import static javafx.scene.shape.StrokeLineCap.ROUND;

/**
 *
 * @author Stegger
 */
public class TicTacViewController implements Initializable {
    @FXML
    private Label lblPlayer;
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane anchorPane;
    private static final String TXT_PLAYER = "Player: ";
    private IGameModel game;
    private int player;
    private Line winningLine;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            player = game.getNextPlayer();
            Integer row = GridPane.getRowIndex((Node) event.getSource());
            Integer col = GridPane.getColumnIndex((Node) event.getSource());
            int r = (row == null) ? 0 : row;
            int c = (col == null) ? 0 : col;

            if (!game.isGameOver()) {
                if (game.play(r, c)) {
                    Button btn = (Button) event.getSource();
                    String colour = player == 0 ? "#ED2A1D" : "#2642A6";
                    btn.setTextFill(Paint.valueOf(colour));
                    String xOrO = player == 0 ? "X" : "O";
                    btn.setText(xOrO);
                    setPlayer();
                }
            }

            int winner = game.getWinner();
            displayWinner(winner);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleNewGame(ActionEvent event) {
        game.newGame();
        clearBoard();
        player = 0;
        setPlayer();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        game = new GameBoard();
        game.getCurrentPlayer();
        setPlayer();
    }

    private void setPlayer() {
        int playerDisplay = game.getNextPlayer();
        String xOrO = playerDisplay == 0 ? "X" : "O";
        lblPlayer.setText(TXT_PLAYER + playerDisplay + " (" + xOrO + ")");
    }

    private void displayWinner(int winner) {
        if (game.isGameOver()) {
            String message = "";
            switch (winner) {
                case -1:
                    message = "It's a draw :-(";
                    break;
                default:
                    message = "Player " + winner + " wins!!!";
                    Integer[] winningFields = game.getWinningFields();
                    winningLine = new Line(winningFields[0], winningFields[1], winningFields[2], winningFields[3]);
                    winningLine.setStrokeWidth(5);
                    winningLine.setStrokeLineCap(ROUND);
                    anchorPane.getChildren().add(anchorPane.getChildren().size(), winningLine);
                    break;
            }
            lblPlayer.setText(message);
        }
    }

    private void clearBoard() {
        for (Node n : gridPane.getChildren()) {
            Button btn = (Button) n;
            btn.setText("");
        }
        anchorPane.getChildren().remove(winningLine);
    }

    public Node getNodeByRowColumnIndex(int row, int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            Integer getR = GridPane.getRowIndex(node);
            Integer getC = GridPane.getColumnIndex(node);

            if (getR == null) {
                getR = 0;
            }

            if (getC == null) {
                getC = 0;
            }

            if (getR == row && getC == column) {
                result = node;
            }
        }
        return result;
    }

    public void setModel(GameBoard game){
        this.game = game;
    }

    public void switchToIntroScreen(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/IntroScreen.fxml"));
        Parent root = loader.load();

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Tic Tac Toe");
        stage.centerOnScreen();
        stage.show();
    }

    public GridPane getGridPane(){
        return gridPane;
    }

    public int getLabelHeight(){
        return (int) lblPlayer.getHeight();
    }

}