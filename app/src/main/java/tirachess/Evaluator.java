package tirachess;

import java.util.ArrayList;

/**
 * Class that takes care of calculating the correct moves in a chess game.
 */
public class Evaluator {

    /**
     * This method is an implementation of minimax algorithm with alphabeta pruning.
     * 
     * @param b     The board state that needs to be evaluated.
     * @param depth The depth that the minimax algorithm will search.
     * @param alpha Lower Bound while pruning
     * @param beta  Upper bound while pruning
     * @return double Returns the evaluation for the board state b.
     */
    public double alphabeta(Board b, int depth, double alpha, double beta) {
        if (b.whitesMove) {
            ArrayList<Board> moves = b.getMoves();
            if (moves.isEmpty()) {
                if (b.pieceIsAttacked(Board.WKing)) {
                    return Double.NEGATIVE_INFINITY;
                } else {
                    return 0;
                }
            }
            if (depth == 0) {
                return this.evaluate(b);
            }
            Double eval = Double.NEGATIVE_INFINITY;
            for (Board move : moves) {
                eval = Math.max(eval, this.alphabeta(move, depth - 1, alpha, beta));
                alpha = Math.max(alpha, eval);
                if (alpha >= beta) {
                    return eval;
                }
            }
            return eval;
        } else {
            ArrayList<Board> moves = b.getMoves();
            if (moves.isEmpty()) {
                if (b.pieceIsAttacked(Board.BKing)) {
                    return Double.POSITIVE_INFINITY;
                } else {
                    return 0;
                }
            }
            if (depth == 0) {
                return this.evaluate(b);
            }
            Double eval = Double.POSITIVE_INFINITY;
            for (Board move : moves) {
                eval = Math.min(eval, this.alphabeta(move, depth - 1, alpha, beta));
                beta = Math.min(beta, eval);
                if (alpha >= beta) {
                    return eval;
                }
            }
            return eval;

        }
    }

    /**
     * This method evaluates a game board state.
     * 
     * @param b The board state that will be evaluated
     * @return double This returns a evaluation value where positive is advantages
     *         for white and negative is advantages for black.
     */
    public double evaluate(Board b) {
        double eval = 0;
        for (int r = 0; r < Board.rows; r++) {
            for (int c = 0; c < Board.cols; c++) {
                if (b.board[c][r] == Board.Empty) {
                    continue;
                } else if (b.board[c][r] == Board.WQueen) {
                    eval += 9;
                } else if (b.board[c][r] == Board.WRook) {
                    eval += 5;
                } else if (b.board[c][r] == Board.WBishop) {
                    eval += 3.1;
                } else if (b.board[c][r] == Board.WKnight) {
                    eval += 3;
                } else if (b.board[c][r] == Board.WPawn) {
                    eval += 1;
                } else if (b.board[c][r] == Board.BQueen) {
                    eval -= 9;
                } else if (b.board[c][r] == Board.BRook) {
                    eval -= 5;
                } else if (b.board[c][r] == Board.BBishop) {
                    eval -= 3.1;
                } else if (b.board[c][r] == Board.BKnight) {
                    eval -= 3;
                } else if (b.board[c][r] == Board.BPawn) {
                    eval -= 1;
                }
            }
        }
        return eval;
    }
}
