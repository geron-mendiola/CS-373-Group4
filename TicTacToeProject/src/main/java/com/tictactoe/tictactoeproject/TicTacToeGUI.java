package com.tictactoe.tictactoeproject;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import javax.net.ssl.SSLContext;
import java.util.Collections;
import java.util.LinkedList;

public class TicTacToeGUI extends Application {

    private Button[][] buttons = new Button[3][3];
    private Tic_Tac_Toe1 game = new Tic_Tac_Toe1();
    private boolean isXTurn = true;
    private Button startButton;
    private Button resetButton;
    private Label turnLabel, scoreLabel;
    private GridPane grid;
    private String playerXName, playerOName;
    private Stage mainStage;
    private Scene welcomeScene, gameScene, scoreScene;

    private LinkedList<GameEntry> playerList = new LinkedList<>();
    private ListView<String> listView = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;

        VBox welcomeLayout = new VBox(50);
        welcomeLayout.setAlignment(Pos.CENTER);
        Label welcomeLabel = new Label("Welcome to Tic-Tac-Toe!");
        welcomeLabel.setFont(new Font("Arial", 20));
        welcomeLayout.setStyle("-fx-background-color: #ffb6c1;");

        Button playButton = new Button("Play");
        playButton.setOnAction(e -> showGameScene());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> primaryStage.close());

        welcomeLayout.getChildren().addAll(welcomeLabel, playButton, exitButton);
        welcomeScene = new Scene(welcomeLayout, 400, 300);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
        primaryStage.centerOnScreen();
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

        Button returnMenuButton = new Button("Return to Main Menu");
        returnMenuButton.setOnAction(e -> mainStage.setScene(welcomeScene));

        Button backButton = new Button("Return to Game");
        backButton.setOnAction(e -> mainStage.setScene(gameScene));


        Button scoreButton = new Button("Score");
        scoreButton.setOnAction(e -> showScore());
        scoreLabel = new Label("");
        scoreLabel.setFont(new Font("Arial", 24));

        Label Title = new Label("Scores:");
        Title.setFont(new Font("Arial", 20));

        turnLabel = new Label("Press Start to Begin");
        turnLabel.setFont(new Font("Arial", 16));

        VBox vbox = new VBox(10, startButton, resetButton, returnMenuButton, scoreButton, turnLabel, scoreLabel, grid);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #ffb6c1;");

        VBox scoreBox = new VBox(10,Title ,scoreLabel, backButton);
        scoreBox.setAlignment(Pos.TOP_CENTER);
        scoreBox.setStyle("-fx-background-color: #ADD8E6;");
        scoreScene = new Scene(scoreBox, 500, 600);

        gameScene = new Scene(vbox, 500, 600);
        mainStage.setScene(gameScene);


    }

    private void addName(String nameX, String nameO) {
        GameEntry newEntryX = new GameEntry(nameX);
        GameEntry newEntryO = new GameEntry(nameO);
        playerList.add(newEntryX);
        playerList.add(newEntryO);

    }

    private void sortScores() {
        Collections.sort(playerList);
        if (playerList.size() > 5) {
            playerList = new LinkedList<>(playerList.subList(0, 5));  // Keep only top 5 players
        }
    }


    private void startGame(boolean askNames) {
        if (askNames) {
            playerXName = getPlayerName("Enter Player X's Name:");
            if (playerXName == null) return;

            playerOName = getPlayerName("Enter Player O's Name:");
            if (playerOName == null) return;

            addName(playerXName, playerOName);
        }

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
        mainStage.setScene(scoreScene);
        sortScores();
        StringBuilder scores = new StringBuilder();
        for(GameEntry entry: playerList) {
           scores.append(entry.toString()).append("\n");
           }
        scoreLabel.setText(scores.toString());
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
        if (winner != 0) {
            for (GameEntry entry : playerList) {
                if (entry.getPlayerName().equals(playerXName) && winner == Tic_Tac_Toe1.X) {
                    entry.increaseScore();
                    break;
                } else if (entry.getPlayerName().equals(playerOName) && winner == Tic_Tac_Toe1.O) {
                    entry.increaseScore();
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

    public static void main(String[] args) {
        launch(args);
    }
}