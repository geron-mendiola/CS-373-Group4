package com.tictactoe.tictactoeproject;

import javafx.scene.control.ListView;

import java.util.Comparator;
import java.util.LinkedList;

class GameEntry implements Comparable<GameEntry> {
    private String playerName;
    private int wins;

    public GameEntry(String playerName) {
        this.playerName = playerName;
        this.wins = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getWins() {
        return wins;
    }

    public void increaseScore() {
        wins++;
    }

    @Override
    public int compareTo(GameEntry other) {
        return Integer.compare(other.wins, this.wins);  // Sort in descending order of wins
    }

    @Override
    public String toString() {
        return playerName + ": " + wins + " wins";
    }
}
