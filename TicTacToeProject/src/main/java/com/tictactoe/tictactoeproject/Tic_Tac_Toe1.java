package com.tictactoe.tictactoeproject;

import java.util.Scanner;

public class Tic_Tac_Toe1 {
    public static final int X = 1, O = -1; // players
    public static final int EMPTY = 0; // empty cell
    private int board[ ][ ] = new int[3][3]; // game board
    private int player; // current player


    public Tic_Tac_Toe1( ) {
        clearBoard( );
    }

    public void clearBoard( ) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = EMPTY; // every cell should be empty
        player = X; // the first player is 'X'
    }

    public void putMark(int i, int j) throws IllegalArgumentException {
        if ((i < 0) || (i > 2) || (j < 0) || (j > 2))
            throw new IllegalArgumentException("Invalid board position");
        if (board[i][j] != EMPTY)
            throw new IllegalArgumentException("Board position occupied");
        board[i][j] = player; // place the mark for the current player
        player = - player; // switch players (uses fact that O = - X)
    }

    public boolean isWin(int mark) {
        return ((board[0][0] + board[0][1] + board[0][2] == mark*3) // row 0
                || (board[1][0] + board[1][1] + board[1][2] == mark*3) // row 1
                || (board[2][0] + board[2][1] + board[2][2] == mark*3) // row  2
                || (board[0][0] + board[1][0] + board[2][0] == mark*3) // column 0
                || (board[0][1] + board[1][1] + board[2][1] == mark*3) // column 1
                || (board[0][2] + board[1][2] + board[2][2] == mark*3) // column 2
                || (board[0][0] + board[1][1] + board[2][2] == mark*3) // diagonal
                || (board[2][0] + board[1][1] + board[0][2] == mark*3)); // rev diag
    }

    public int winner( ) {
        if (isWin(X)) {
            return (X);
        }
        else if (isWin(O))
            return(O);
        else
            return(0);
    }

    public String toString( ) {
        StringBuilder sb = new StringBuilder( );
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                switch (board[i][j]) {
                    case X: sb.append("X"); break;
                    case O: sb.append("O"); break;
                    case EMPTY: sb.append(" "); break;
                }
                if (j < 2) sb.append("|"); // column boundary
            }
            if (i < 2) sb.append("\n-----\n"); // row boundary
        }
        return sb.toString( );
    }

    public static void main(String[ ] args) {
        Tic_Tac_Toe1 game = new Tic_Tac_Toe1( );
        Scanner input = new Scanner(System.in);
        for(int k=0; k<9; k++) {
            System.out.print("Enter X value: ");
            int x_value = input.nextInt();

            System.out.print("Enter Y value: ");
            int y_value = input.nextInt();

            game.putMark(x_value, y_value);
            System.out.print(game);
        }

        System.out.println(game);
        int winningPlayer = game.winner( );
        String[ ] outcome = {"O wins", "Tie", "X wins"}; // rely on ordering
        System.out.println(outcome[1 + winningPlayer]);
    }
}
