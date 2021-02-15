package tirachess.domain;

import tirachess.datastructures.MyArrayList;

/**
 * Class that takes care of calculating the correct moves in a chess game.
 */
public class Evaluator {

    /**
     * This method is an implementation of minimax algorithm with alphabeta pruning.
     * 
     * @param p     The board state that needs to be evaluated.
     * @param depth The depth that the minimax algorithm will search.
     * @param alpha Lower Bound while pruning
     * @param beta  Upper bound while pruning
     * @return double Returns the evaluation for the board state b.
     */
    public double alphabeta(Position p, int depth, double alpha, double beta) {
        if (p.halfMoveCounter >= 100 || p.threeFoldRepetition) {
            return 0;
        }

        if (p.whitesMove) {
            MyArrayList<Position> moves = p.getMoves();
            if (moves.isEmpty()) {
                if (p.pieceIsAttacked(Position.WKing)) {
                    return Double.NEGATIVE_INFINITY;
                } else {
                    return 0;
                }
            }
            if (depth == 0) {
                return this.evaluate(p);
            }
            Double eval = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < moves.size(); i++) {
                Position move = moves.get(i);
                eval = Math.max(eval, this.alphabeta(move, depth - 1, alpha, beta));
                alpha = Math.max(alpha, eval);
                if (alpha >= beta) {
                    return eval;
                }
            }
            return eval;
        } else {
            MyArrayList<Position> moves = p.getMoves();
            if (moves.isEmpty()) {
                if (p.pieceIsAttacked(Position.BKing)) {
                    return Double.POSITIVE_INFINITY;
                } else {
                    return 0;
                }
            }
            if (depth == 0) {
                return this.evaluate(p);
            }
            Double eval = Double.POSITIVE_INFINITY;
            for (int i = 0; i < moves.size(); i++) {
                Position move = moves.get(i);
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
    public double evaluate(Position b) {
        double eval = 0;
        for (int r = 0; r < Position.rows; r++) {
            for (int c = 0; c < Position.cols; c++) {
                if (b.board[c][r] == Position.Empty) {
                    continue;
                } else if (b.board[c][r] == Position.WQueen) {
                    eval += 9;
                } else if (b.board[c][r] == Position.WRook) {
                    eval += 5;
                } else if (b.board[c][r] == Position.WBishop) {
                    eval += 3.1;
                } else if (b.board[c][r] == Position.WKnight) {
                    eval += 3;
                } else if (b.board[c][r] == Position.WPawn) {
                    eval += 1;
                } else if (b.board[c][r] == Position.BQueen) {
                    eval -= 9;
                } else if (b.board[c][r] == Position.BRook) {
                    eval -= 5;
                } else if (b.board[c][r] == Position.BBishop) {
                    eval -= 3.1;
                } else if (b.board[c][r] == Position.BKnight) {
                    eval -= 3;
                } else if (b.board[c][r] == Position.BPawn) {
                    eval -= 1;
                }
            }
        }
        return eval;
    }
}
