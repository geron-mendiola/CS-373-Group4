package com.tictactoe.tictactoeproject;

public class GameEntry {
    private String playerX;
    private String playerO;
    private int score;

    public GameEntry(String playerX, String playerO){
    this.playerX = playerX;
    this.playerO = playerO;
    this.score = 0;
    }

    public void setScore() {
        this.score = score;
    }

    public String getPlayerX(){
        return playerX;
    }

    public String getPlayerO(){
        return playerO;
    }

    public int getScore(){
        return score;
    }

    @Override
    public String toString() {
        return playerX + " " + playerO + " - Score" + score;
    }
}

