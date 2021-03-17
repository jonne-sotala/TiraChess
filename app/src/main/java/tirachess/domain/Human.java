package tirachess.domain;

import java.util.Scanner;

import tirachess.datastructures.MyArrayList;
import tirachess.datastructures.MyHashMap;

/**
 * A class that implements the interface Player. It represents the human player in a chess game. 
 * It also takes care of getting the moves from the human player and giving them to the program. 
 */
public class Human implements Player {
    private MyHashMap<Character, Integer> coordinates;
    private Scanner scanner;

    /**
     * The constructor of the Human class.
     * 
     * @param scanner Scanner object that is used to read the human player's moves. 
     */
    public Human(Scanner scanner) {
        this.scanner = scanner;
        coordinates = new MyHashMap<>();
        coordinates.put('a', 0);
        coordinates.put('b', 1);
        coordinates.put('c', 2);
        coordinates.put('d', 3);
        coordinates.put('e', 4);
        coordinates.put('f', 5);
        coordinates.put('g', 6);
        coordinates.put('h', 7);
    }


    /**
     * A method that takes a position and then returns a new Position where a move
     * has been played by the human player. The player gives his/her move through
     * the terminal interface.  
     * 
     * @param p The current position in a game. 
     * @return Position The next position after the chosen move. 
     */
    public Position play(Position p) {
        MyArrayList<Position> moves = p.getMoves();
        String move;
        while (true) {
            System.out.print("Your move: ");
            move = scanner.next();
            if (!move.matches("[a-h][1-8][a-h][1-8]|[a-h][7][a-h]8[qrbn]")) {
                System.out.println("The move needs to be written like e2e4 or e7e8q (promoting).");
                continue;
            }
            int col1 = coordinates.get(move.charAt(0));
            int row1 = Character.getNumericValue(move.charAt(1)) - 1;
            int col2 = coordinates.get(move.charAt(2));
            int row2 = Character.getNumericValue(move.charAt(3)) - 1;
            int piece = p.board[col1][row1];
            if (col1 == col2 && row1 == row2) {
                System.out.println("You cannot move to the same square where the piece is.");
                continue;
            }
            if (!p.isCurrentPlayersPiece(piece)) {
                System.out.println("That's not your piece.");
            }
            if (piece == Position.WQueen || piece == Position.BQueen 
                    || piece == Position.WRook || piece == Position.BRook
                    || piece == Position.WBishop || piece == Position.BBishop
                    || piece == Position.WKnight || piece == Position.BKnight) {
                Position newPosition = p.getCloneAndChangeTurn();
                newPosition.board[col2][row2] = newPosition.board[col1][row1];
                newPosition.board[col1][row1] = Position.Empty;
                if (piece == Position.WRook || piece == Position.BRook) {
                    if (row1 == 0 && col1 == 0) {
                        newPosition.whiteQueenSideCastlingAllowed = false;
                    } else if (row1 == 0 && col1 == 7) {
                        newPosition.whiteKingSideCastlingAllowed = false;
                    } else if (row1 == 7 && col1 == 0) {
                        newPosition.blackQueenSideCastlingAllowed = false;
                    } else if (row1 == 7 && col1 == 7) {
                        newPosition.blackKingSideCastlingAllowed = false;
                    }
                }
                if (moves.contains(newPosition)) {
                    return newPosition;
                }
            } else if (piece == Position.WPawn || piece == Position.BPawn) {
                Position newPosition = p.getCloneAndChangeTurn();
                newPosition.board[col2][row2] = newPosition.board[col1][row1];
                newPosition.board[col1][row1] = Position.Empty;
                if (Math.abs(row2 - row1) == 2) {
                    newPosition.enPassantFile = col1;
                } 
                if (p.whitesMove && row1 == 4 && col2 == p.enPassantFile) {
                    newPosition.board[col2][row2 - 1] = Position.Empty;
                }
                if (!p.whitesMove && row1 == 3 && col2 == p.enPassantFile) {
                    newPosition.board[col2][row2 + 1] = Position.Empty;
                }
                if (p.whitesMove && row2 == 7) {
                    if (move.length() < 5) {
                        System.out.println("You have to tell how to promote the pawn (ex. e7e8q)");
                        continue;
                    }
                    char promotion = move.charAt(4);
                    if (promotion == 'q') {
                        newPosition.board[col2][row2] = Position.WQueen;
                    } else if (promotion == 'r') {
                        newPosition.board[col2][row2] = Position.WRook;
                    } else if (promotion == 'b') {
                        newPosition.board[col2][row2] = Position.WBishop;
                    } else if (promotion == 'n') {
                        newPosition.board[col2][row2] = Position.WKnight;
                    } else {
                        System.out.println("Invalid promotion");
                        continue;
                    }
                }
                if (p.whitesMove && row2 == 0) {
                    if (move.length() < 5) {
                        System.out.println("You have to tell how to promote the pawn (ex. e2e1q)");
                        continue;
                    }
                    char promotion = move.charAt(4);
                    if (promotion == 'q') {
                        newPosition.board[col2][row2] = Position.BQueen;
                    } else if (promotion == 'r') {
                        newPosition.board[col2][row2] = Position.BRook;
                    } else if (promotion == 'b') {
                        newPosition.board[col2][row2] = Position.BBishop;
                    } else if (promotion == 'n') {
                        newPosition.board[col2][row2] = Position.BKnight;
                    } else {
                        System.out.println("Invalid promotion");
                        continue;
                    }
                }
                if (moves.contains(newPosition)) {
                    return newPosition;
                }
            } else if (piece == Position.WKing || piece == Position.BKing) {
                Position newPosition = p.getCloneAndChangeTurn();
                newPosition.board[col2][row2] = newPosition.board[col1][row1];
                newPosition.board[col1][row1] = Position.Empty;
                if (col1 == 4 && row1 == 7 && col2 == 2 && row2 == 7) {
                    newPosition.blackQueenSideCastlingAllowed = false;
                    newPosition.blackKingSideCastlingAllowed = false;
                    newPosition.board[3][7] = newPosition.board[0][7];
                    newPosition.board[0][7] = Position.Empty;
                } else if (col1 == 4 && row1 == 7 && col2 == 6 && row2 == 7) {
                    newPosition.blackQueenSideCastlingAllowed = false;
                    newPosition.blackKingSideCastlingAllowed = false;
                    newPosition.board[5][7] = newPosition.board[7][7];
                    newPosition.board[7][7] = Position.Empty;
                } else if (col1 == 4 && row1 == 0 && col2 == 2 && row2 == 0) {
                    newPosition.whiteQueenSideCastlingAllowed = false;
                    newPosition.whiteKingSideCastlingAllowed = false;
                    newPosition.board[3][0] = newPosition.board[0][0];
                    newPosition.board[0][0] = Position.Empty;
                } else if (col1 == 4 && row1 == 0 && col2 == 6 && row2 == 0) {
                    newPosition.whiteQueenSideCastlingAllowed = false;
                    newPosition.whiteKingSideCastlingAllowed = false;
                    newPosition.board[5][0] = newPosition.board[7][0];
                    newPosition.board[7][0] = Position.Empty;
                }
                newPosition.print();
                if (moves.contains(newPosition)) {
                    return newPosition;
                }
            }
            System.out.println("Illegal move.");
        }
    }
}
