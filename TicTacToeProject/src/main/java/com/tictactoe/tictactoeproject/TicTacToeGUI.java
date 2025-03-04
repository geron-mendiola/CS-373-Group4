package com.tictactoe.tictactoeproject;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;

public class TicTacToeGUI extends Application {
    private Button[][] buttons = new Button[3][3];
    private Tic_Tac_Toe1 game = new Tic_Tac_Toe1();
    private boolean isXTurn = true;
    private Button startButton, resetButton, returnButton, scoreButton;
    private Label turnLabel;
    private GridPane grid;
    private String playerXName, playerOName;
    private Stage mainStage;
    private Scene welcomeScene, gameScene;

    private LinkedList<GameEntry> playerList = new LinkedList<>();
    private ListView<String> listView = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;

        VBox welcomeLayout = new VBox(20);
        welcomeLayout.setAlignment(Pos.CENTER);
        Label welcomeLabel = new Label("Welcome to Tic-Tac-Toe!");
        welcomeLabel.setFont(new Font("Arial", 20));

        Button playButton = new Button("Play");
        playButton.setOnAction(e -> showGameScene());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> primaryStage.close());

        Button siteButton = new Button("Visit the UOG website");
        siteButton.setOnAction(e -> {
            try {
                visitSite();
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        welcomeLayout.getChildren().addAll(welcomeLabel, playButton, exitButton, siteButton);
        welcomeScene = new Scene(welcomeLayout, 400, 300);

        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }

    private void showGameScene() {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        initializeBoard();

        startButton = new Button("Start Game");
        startButton.setOnAction(e -> startGame(true));

        resetButton = new Button("Reset Game");
        resetButton.setOnAction(e -> resetGame());
        resetButton.setDisable(true);

        returnButton = new Button("Return to Main Menu");
        returnButton.setOnAction(e -> mainStage.setScene(welcomeScene));

        scoreButton = new Button("Score");
        scoreButton.setOnAction(e ->showScore());

        turnLabel = new Label("Press Start to Begin");
        turnLabel.setFont(new Font("Arial", 16));

        VBox vbox = new VBox(10, startButton, resetButton, returnButton, scoreButton,turnLabel, grid);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #ffb6c1;");

        gameScene = new Scene(vbox, 500, 600);
        mainStage.setScene(gameScene);
    }

    private void addName(String nameX, String nameO) {
        GameEntry gameEntry = new GameEntry(nameX, nameO);
        playerList.add(gameEntry);
    }


    private void startGame(boolean askNames) {
        if (askNames) {
            playerXName = getPlayerName("Enter Player X's Name:");
            String nameX = playerXName;
            if (playerXName == null) return;

            playerOName = getPlayerName("Enter Player O's Name:");
            String nameO = playerOName;
            if (playerOName == null) return;

            GameEntry newEntry = new GameEntry(playerXName, playerOName);
            playerList.add(newEntry);
        }
        System.out.println(playerList);

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

    private String getPlayerName(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Player Name");
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        return dialog.showAndWait().orElse(null);
    }

    private void showScore() {
        for(GameEntry entry: playerList) {
            System.out.println(entry);
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

    private void checkStatus() {
        int winner = game.winner();

        //prints the score only when there is a winner
        if (winner != 0) {
            for (GameEntry entry : playerList) {
                //updates player names in the list if new players are added
                if (entry.getPlayerX().equals(playerXName) && entry.getPlayerO().equals(playerOName)) {
                    if (winner == Tic_Tac_Toe1.X) {
                        entry.increaseScore(true);
                    } else if (winner == Tic_Tac_Toe1.O) {
                        entry.increaseScore(false);
                    }
                    System.out.println(entry);
                    break;
                }
            }
            showAlert((winner == Tic_Tac_Toe1.X ? playerXName : playerOName) + " wins!");
            turnLabel.setText((winner == Tic_Tac_Toe1.X ? playerXName : playerOName) + " wins!");
            Replay();

        } else if (isBoardFull()) {
            showAlert("It's a tie!");
            turnLabel.setText("It's a tie!");
            Replay();
        }
    }

    private void Replay() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Play Again?");
        alert.setHeaderText(null);
        alert.setContentText("Would the same players like to continue playing?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(response -> {
            startGame(response != yesButton);
        });
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button(" ");
                buttons[i][j].setFont(new Font("Arial", 40));
                buttons[i][j].setMinSize(120, 120);
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
            buttons[row][col].setDisable(true);
            buttons[row][col].setStyle("-fx-opacity: 0.5;");
            isXTurn = !isXTurn;

            turnLabel.setText((isXTurn ? playerXName : playerOName) + "'s Turn");
            checkStatus();
        } catch (IllegalArgumentException ex) {
            showAlert(ex.getMessage());
        }
    }

    private boolean isBoardFull() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                if (button.getText().equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tic-Tac-Toe");
        alert.setHeaderText("Game Result");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void visitSite() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.uog.edu"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}