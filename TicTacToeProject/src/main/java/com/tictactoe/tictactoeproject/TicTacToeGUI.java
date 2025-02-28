package com.tictactoe.tictactoeproject;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.control.Alert.AlertType;

import java.util.LinkedList;

public class TicTacToeGUI extends Application {
    private Button[][] buttons = new Button[3][3];
    private Tic_Tac_Toe1 game = new Tic_Tac_Toe1();
    private boolean isXTurn = true;
    private Button startButton, resetButton, addButton;
    private Label turnLabel;
    private GridPane grid;
    private TextField playerXNameField, playerONameField;

    private LinkedList<GameEntry> playerList = new LinkedList<>();
    private ListView<String> listView = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        initializeBoard();

        playerXNameField = new TextField();
        playerXNameField.setPromptText("Enter Player X's Name");

        playerONameField = new TextField();
        playerONameField.setPromptText("Enter Player O's Name");

        startButton = new Button("Start Game");
        startButton.setOnAction(e -> startGame());

        resetButton = new Button("Reset Game");
        resetButton.setOnAction(e -> resetGame());
        resetButton.setDisable(true);

        turnLabel = new Label("Enter Names to Begin");
        turnLabel.setFont(new Font("Arial", 16));

        turnLabel = new Label("Press Start to Begin");
        turnLabel.setFont(new Font("Arial", 16));

        VBox vbox = new VBox(10, playerXNameField, playerONameField, startButton, resetButton, turnLabel, grid);
        vbox.setAlignment(Pos.CENTER); // Ensure everything is centered
        vbox.setStyle("-fx-background-color: #ffb6c1;");

        Scene scene = new Scene(vbox, 500, 600);
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void updateListview(){
    listView.refresh();

    }
    private void startGame() {
        String nameX = playerXNameField.getText();
        String nameO= playerONameField.getText();

        if (nameX.isEmpty() || nameO.isEmpty()) {
            showAlert("Please enter both player names!");
            return;
        }
        else if(!n.isEmpty() || !nameX.isEmpty()){
            GameEntry gameEntry = new GameEntry(nameX, nameO);
            playerList.add(gameEntry);
            updateListview();
            playerXNameField.clear();
            playerONameField.clear();
        })

        isXTurn = true;
        game.clearBoard();
        resetButton.setDisable(false);
        startButton.setDisable(true);

        turnLabel.setText(playerXName + "'s Turn");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(" ");
                buttons[i][j].setDisable(false);
                buttons[i][j].setStyle("-fx-opacity: 1;");
            }
        }
    }

    private void resetGame() {
        game.clearBoard();
        isXTurn = true;
        startButton.setDisable(false);
        resetButton.setDisable(true);
        turnLabel.setText("Press Start to Begin");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(" ");
                buttons[i][j].setDisable(true);
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Tic-Tac-Toe");
        alert.setHeaderText("Game Result");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void checkStatus() {
        int winner = game.winner();
        if (winner != 0) {
            showAlert((winner == Tic_Tac_Toe1.X ? "X" : "O") + " wins!");
            turnLabel.setText((winner == Tic_Tac_Toe1.X ? "X" : "O") + " wins!");
            resetGame();
        } else if (isBoardFull()) {
            showAlert("It's a tie!");
            turnLabel.setText("It's a tie!");
            resetGame();
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button(" ");
                buttons[i][j].setFont(new Font("Arial", 40));
                buttons[i][j].setMinSize(100, 100);
                buttons[i][j].setDisable(true);
                final int row = i, col = j;
                buttons[i][j].setOnAction(e -> place(row, col));
                grid.add(buttons[i][j], j, i);
            }
        }
    }


    private void place(int row, int col) {
        try {
            game.putMark(row, col);
            buttons[row][col].setText(isXTurn ? "X" : "O");
            buttons[row][col].setDisable(true);  // Disable button after a move
            buttons[row][col].setStyle("-fx-opacity: 0.5;");  // Change opacity of disabled button
            isXTurn = !isXTurn;  // Switch player turn

            // Get player names from the fields to display the current turn
            String playerName = isXTurn ? playerXNameField.getText() : playerONameField.getText();
            turnLabel.setText(playerName + "'s Turn");

            checkStatus();
        }

        catch (IllegalArgumentException ex) {
            showAlert(ex.getMessage());
        }
    }


    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}


