package com.tictactoe.tictactoeproject;

public class GameEntry {
    private String playerX;
    private String playerO;
    public int scoreX;
    public int scoreO;

    public GameEntry(String playerX, String playerO){
    this.playerX = playerX;
    this.playerO = playerO;
    this.scoreX = 0;
    this.scoreO = 0;
    }

    public void setScoreX() {
        this.scoreX = scoreX;
    }

    public void setScoreO() {
        this.scoreO = scoreO;
    }

    public String getPlayerX(){
        return playerX;
    }

    public String getPlayerO(){
        return playerO;
    }

    public int getScoreX(){
        return scoreX;
    }

    public int getScoreO(){
        return scoreO;
    }

    public void increaseScore(boolean winX) {
        if (winX) {
            this.scoreX++;
        } else if (!winX){
            this.scoreO++;
        }
    }

    @Override
    public String toString() {
        return (playerX + "'s Score: " + scoreX + ", " + playerO + "'s Score: " + scoreO);
    }
}

