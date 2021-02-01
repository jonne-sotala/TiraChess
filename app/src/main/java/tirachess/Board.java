
package tirachess;

import java.util.ArrayList;

/**
 * Board is a class that represents a chess board and keeps track of the
 * different positions in a chess game.
 */
public class Board {
    int[][] board;
    static final int rows = 8;
    static final int cols = 8;

    static final int Empty = 0;
    static final int WKing = 1;
    static final int WQueen = 2;
    static final int WRook = 3;
    static final int WBishop = 4;
    static final int WKnight = 5;
    static final int WPawn = 6;
    static final int BKing = 7;
    static final int BQueen = 8;
    static final int BRook = 9;
    static final int BBishop = 10;
    static final int BKnight = 11;
    static final int BPawn = 12;

    boolean whitesMove;

    /**
     * This is the constructor that creates the board, sets it up, and sets white to
     * be the first to move.
     */
    public Board() {
        this.board = new int[cols][rows];
        this.setUpBoard();
    }

    /**
     * A method that sets up the pieces on the board when the game starts. Also sets
     * white to be the starting player.
     */
    public void setUpBoard() {
        this.whitesMove = true;
        for (int col = 0; col < cols; col++) {
            this.board[col][1] = WPawn;
            this.board[col][rows - 2] = BPawn;
            if (col == 0 || col == cols - 1) {
                this.board[col][0] = WRook;
                this.board[col][rows - 1] = BRook;
            } else if (col == 1 || col == cols - 2) {
                this.board[col][0] = WKnight;
                this.board[col][rows - 1] = BKnight;
            } else if (col == 2 || col == cols - 3) {
                this.board[col][0] = WBishop;
                this.board[col][rows - 1] = BBishop;
            } else if (col == 3) {
                this.board[col][0] = WQueen;
                this.board[col][rows - 1] = BQueen;
            } else if (col == 4) {
                this.board[col][0] = WKing;
                this.board[col][rows - 1] = BKing;
            }
        }
    }

    /**
     * A method that prints the board position.
     */
    public void print() {
        for (int r = rows - 1; r >= 0; r--) {
            for (int c = 0; c < cols; c++) {
                int v = this.board[c][r];
                if (v == Empty) {
                    System.out.print(".");
                }
                if (v == WKing) {
                    System.out.print("K");
                }
                if (v == WQueen) {
                    System.out.print("Q");
                }
                if (v == WRook) {
                    System.out.print("R");
                }
                if (v == WBishop) {
                    System.out.print("B");
                }
                if (v == WKnight) {
                    System.out.print("N");
                }
                if (v == WPawn) {
                    System.out.print("P");
                }
                if (v == BKing) {
                    System.out.print("k");
                }
                if (v == BQueen) {
                    System.out.print("q");
                }
                if (v == BRook) {
                    System.out.print("r");
                }
                if (v == BBishop) {
                    System.out.print("b");
                }
                if (v == BKnight) {
                    System.out.print("n");
                }
                if (v == BPawn) {
                    System.out.print("p");
                }
            }
            System.out.println();
        }
    }

    /**
     * A method that calculates all the possible next moves.
     * 
     * @return all the next possible board states
     */
    public ArrayList<Board> getMoves() {
        ArrayList<Board> moves = new ArrayList<>();

        return moves;
    }

}
