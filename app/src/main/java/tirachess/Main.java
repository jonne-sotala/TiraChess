
package tirachess;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.checkerframework.checker.units.qual.A;

import tirachess.datastructures.MyArrayList;
import tirachess.datastructures.MyHashMap;
import tirachess.domain.Evaluator;
import tirachess.domain.Position;

/**
 * The main class.
 */
public class Main {

    /**
     * The main method. It playes a chess game and finishes. Currently here just for
     * testing purposes.
     */
    public static void main(String[] args) {
        // Scanner scanner1 = new Scanner(System.in);
        // while (true) {
        //     String string = scanner1.next();
        //     if (string.equals("hello")) {
        //         break;
        //     }
        //     System.out.println(string.matches("[a-h][1-8][a-h][1-8]"));
        // }
        // scanner1.close();



        Position p = new Position();
        System.out.println("Hello! Please choose an option (1 or 2):");
        System.out.println("1) Play versus the computer.");
        System.out.println("2) Let the computer play with itself.");
        Scanner scanner = new Scanner(System.in);
        int num;
        do {
            System.out.print("Your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Please choose 1 or 2: ");
                scanner.next();
            }
            num = scanner.nextInt();
        } while (!(num == 1 || num == 2));

        MyHashMap<Character, Integer> coordinates = new MyHashMap<>();
        coordinates.put('a', 0);
        coordinates.put('b', 1);
        coordinates.put('c', 2);
        coordinates.put('d', 3);
        coordinates.put('e', 4);
        coordinates.put('f', 5);
        coordinates.put('g', 6);
        coordinates.put('h', 7);
        if (num == 1) {
            Random r = new Random();
            boolean playerIsWhite = r.nextBoolean();
            String playerColor = playerIsWhite ? "white" : "black";
            System.out.println("Game starts... You are " + playerColor + ".");
            p.print();
            Evaluator evaluator = new Evaluator();
            MyArrayList<Position> moves = p.getMoves();
            while (!moves.isEmpty()) {
                if (p.whitesMove == playerIsWhite) {
                    String move;
                    while (true) {
                        System.out.print("Your move: ");
                        move = scanner.next();
                        if (!move.matches("[a-h][1-8][a-h][1-8]")) {
                            System.out.println("The move needs to be written like e2e4.");
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
                            if (moves.contains(newPosition)) {
                                p = newPosition;
                                p.print();
                                System.out.println();
                                break;
                            }
                        } else if (piece == Position.WPawn || piece == Position.BPawn) {
                            Position newPosition = p.getCloneAndChangeTurn();
                            newPosition.board[col2][row2] = newPosition.board[col1][row1];
                            newPosition.board[col1][row1] = Position.Empty;
                            if (Math.abs(row2 - row1) == 2) {
                                newPosition.enPassantFile = col1;
                            } 
                            if (moves.contains(newPosition)) {
                                p = newPosition;
                                p.print();
                                System.out.println();
                                break;
                            }
                        } else if (piece == Position.WKing || piece == Position.BKing) {
                            Position newPosition = p.getCloneAndChangeTurn();
                            newPosition.board[col2][row2] = newPosition.board[col1][row1];
                            newPosition.board[col1][row1] = Position.Empty;
                            if (col1 == 4 && row1 == 7 && col2 == 2 && row2 == 7) {
                                newPosition.blackQueenSideCastlingAllowed = false;
                                newPosition.board[3][7] = newPosition.board[0][7];
                                newPosition.board[0][7] = Position.Empty;
                            } else if (col1 == 4 && row1 == 7 && col2 == 6 && row2 == 7) {
                                newPosition.blackKingSideCastlingAllowed = false;
                                newPosition.board[5][7] = newPosition.board[7][7];
                                newPosition.board[7][7] = Position.Empty;
                            } else if (col1 == 4 && row1 == 0 && col2 == 2 && row2 == 0) {
                                newPosition.whiteQueenSideCastlingAllowed = false;
                                newPosition.board[3][0] = newPosition.board[0][0];
                                newPosition.board[0][0] = Position.Empty;
                            } else if (col1 == 4 && row1 == 0 && col2 == 6 && row2 == 0) {
                                newPosition.whiteKingSideCastlingAllowed = false;
                                newPosition.board[5][0] = newPosition.board[7][0];
                                newPosition.board[7][0] = Position.Empty;
                            }
                            newPosition.print();
                            if (moves.contains(newPosition)) {
                                p = newPosition;
                                p.print();
                                System.out.println();
                                break;
                            }
                        }
                        System.out.println("Illegal move.");

                    }
                } else {
                    Position bestMove = moves.get(0);
                    Double bestEval = evaluator.alphabeta(bestMove, 2, Double.NEGATIVE_INFINITY, 
                                                                    Double.POSITIVE_INFINITY);
                    for (int i = 0; i < moves.size(); i++) {
                        Position move = moves.get(i);
                        Double moveEval = evaluator.alphabeta(move, 2, Double.NEGATIVE_INFINITY, 
                                                                    Double.POSITIVE_INFINITY);
                        if (p.whitesMove && moveEval > bestEval) {
                            bestMove = move;
                            bestEval = moveEval;
                        } else if (!p.whitesMove && moveEval < bestEval) {
                            bestMove = move;
                            bestEval = moveEval;
                        }
                    }
                    // if (p.fullMoveCounter % 10 == 1) {
                    //     System.out.println(p.fullMoveCounter + "st move");
                    // } else if (p.fullMoveCounter % 10 == 2) {
                    //     System.out.println(p.fullMoveCounter + "nd move");
                    // } else {
                    //     System.out.println(p.fullMoveCounter + "th move");
                    // }
                    // if (p.whitesMove) {
                    //     System.out.println("White's evaluation: " + bestEval / 100);
                    // } else {
                    //     System.out.println("Blacks's evaluation: " + bestEval / 100);
                    // }
                    p = bestMove;
                    p.print();
                    System.out.println();
                }
                moves = p.getMoves();
            }
            if (p.whitesMove && p.pieceIsAttacked(Position.WKing)) {
                System.out.println("Black won.");
            } else if (!p.whitesMove && p.pieceIsAttacked(Position.BKing)) {
                System.out.println("White won.");
            } else {
                System.out.println("Draw.");
            }
        } else {
            System.out.println("The game starts...");
            p.print();
            Evaluator evaluator = new Evaluator();

            MyArrayList<Position> moves = p.getMoves();
            while (!moves.isEmpty()) {
                Position bestMove = moves.get(0);
                Double bestEval = evaluator.alphabeta(bestMove, 2, Double.NEGATIVE_INFINITY, 
                                                                Double.POSITIVE_INFINITY);
                for (int i = 0; i < moves.size(); i++) {
                    Position move = moves.get(i);
                    Double moveEval = evaluator.alphabeta(move, 2, Double.NEGATIVE_INFINITY, 
                                                                Double.POSITIVE_INFINITY);
                    if (p.whitesMove && moveEval > bestEval) {
                        bestMove = move;
                        bestEval = moveEval;
                    } else if (!p.whitesMove && moveEval < bestEval) {
                        bestMove = move;
                        bestEval = moveEval;
                    }
                }
                if (p.fullMoveCounter % 10 == 1) {
                    System.out.println(p.fullMoveCounter + "st move");
                } else if (p.fullMoveCounter % 10 == 2) {
                    System.out.println(p.fullMoveCounter + "nd move");
                } else {
                    System.out.println(p.fullMoveCounter + "th move");
                }
                if (p.whitesMove) {
                    System.out.println("White's evaluation: " + bestEval / 100);
                } else {
                    System.out.println("Blacks's evaluation: " + bestEval / 100);
                }
                p = bestMove;
                p.print();
                System.out.println();
                moves = p.getMoves();
            }
            if (p.whitesMove && p.pieceIsAttacked(Position.WKing)) {
                System.out.println("Black won.");
            } else if (!p.whitesMove && p.pieceIsAttacked(Position.BKing)) {
                System.out.println("White won.");
            } else {
                System.out.println("Draw.");
            }
        }
        scanner.close();

        
    }

}
