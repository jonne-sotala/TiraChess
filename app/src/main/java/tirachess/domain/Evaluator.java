package tirachess.domain;

import tirachess.datastructures.MyArrayList;

/**
 * Class that takes care of calculating the correct moves in a chess game.
 */
public class Evaluator {

    public static final int[][] WPawn = { {  0,  0,  0,  0,  0,  0,  0,  0 },
                                          {  5, 10, 10,-20,-20, 10, 10,  5 },
                                          {  5, -5,-10,  0,  0,-10, -5,  5 },
                                          {  0,  0,  0, 20, 20,  0,  0,  0 },
                                          {  5,  5, 10, 25, 25, 10,  5,  5 }, 
                                          { 10, 10, 20, 30, 30, 20, 10, 10 },
                                          { 50, 50, 50, 50, 50, 50, 50, 50 }, 
                                          {  0,  0,  0,  0,  0,  0,  0,  0 }};

    public static final int[][] WKnight = { {-50,-40,-30,-30,-30,-30,-40,-50 },
                                            {-40,-20,  0,  5,  5,  0,-20,-40 },
                                            {-30, -5, 10, 15, 15, 10,  5,-30 },
                                            {-30,  0, 15, 20, 20, 15,  0,-30 },
                                            {-30,  5, 15, 20, 20, 15,  5,-30 }, 
                                            {-30,  0, 10, 15, 15, 10,  0,-30 },
                                            {-40,-20,  0,  0,  0,  0,-20,-40 }, 
                                            {-50,-40,-30,-30,-30,-30,-40,-50 }};

    public static final int[][] WBishop = { {-20,-10,-10,-10,-10,-10,-10,-20 },
                                            {-10,  5,  0,  0,  0,  0,  5,-10 },
                                            {-10, 10, 10, 10, 10, 10, 10,-10 },
                                            {-10,  0, 10, 10, 10, 10,  0,-10 },
                                            {-10,  5,  5, 10, 10,  5,  5,-10 }, 
                                            {-10,  0,  5, 10, 10,  5,  0,-10 },
                                            {-10,  0,  0,  0,  0,  0,  0,-10 }, 
                                            {-20,-10,-10,-10,-10,-10,-10,-20 }};

    public static final int[][] WRook = { {  0,  0,  0,  5,  5,  0,  0,  0 },
                                          { -5,  0,  0,  0,  0,  0,  0, -5 }, 
                                          { -5,  0,  0,  0,  0,  0,  0, -5 },
                                          { -5,  0,  0,  0,  0,  0,  0, -5 },
                                          { -5,  0,  0,  0,  0,  0,  0, -5 },
                                          { -5,  0,  0,  0,  0,  0,  0, -5 },
                                          { -5, 10, 10, 10, 10, 10, 10, -5 }, 
                                          {  0,  0,  0,  0,  0,  0,  0,  0 }};

    public static final int[][] WQueen = { {-20,-10,-10, -5, -5,-10,-10,-20 },
                                           {-10,  0,  5,  0,  0,  0,  0,-10 },
                                           {-10,  5,  5,  5,  5,  5,  0,-10 },
                                           {  0,  0,  5,  5,  5,  5,  0, -5 },
                                           { -5,  0,  5,  5,  5,  5,  0, -5 }, 
                                           {-10,  0,  5,  5,  5,  5,  0,-10 },
                                           {-10,  0,  0,  0,  0,  0,  0,-10 }, 
                                           {-20,-10,-10, -5, -5,-10,-10,-20 }};

    public static final int[][] WKing = { { 20, 30, 10,  0,  0, 10, 30, 20 },
                                          { 20, 20,  0,  0,  0,  0, 20, 20 },
                                          {-10,-20,-20,-20,-20,-20,-20,-10 },
                                          {-20,-30,-30,-40,-40,-30,-30,-20 },
                                          {-30,-40,-40,-50,-50,-40,-40,-30 }, 
                                          {-30,-40,-40,-50,-50,-40,-40,-30 },
                                          {-30,-40,-40,-50,-50,-40,-40,-30 }, 
                                          {-30,-40,-40,-50,-50,-40,-40,-30 }};


    public static final int[][] BPawn = { {  0,  0,  0,  0,  0,  0,  0,  0 },
                                          { 50, 50, 50, 50, 50, 50, 50, 50 }, 
                                          { 10, 10, 20, 30, 30, 20, 10, 10 },
                                          {  5,  5, 10, 25, 25, 10,  5,  5 }, 
                                          {  0,  0,  0, 20, 20,  0,  0,  0 },
                                          {  5, -5,-10,  0,  0,-10, -5,  5 },
                                          {  5, 10, 10,-20,-20, 10, 10,  5 },
                                          {  0,  0,  0,  0,  0,  0,  0,  0 }};

    public static final int[][] BKnight = { {-50,-40,-30,-30,-30,-30,-40,-50 },
                                            {-40,-20,  0,  0,  0,  0,-20,-40 }, 
                                            {-30,  0, 10, 15, 15, 10,  0,-30 },
                                            {-30,  5, 15, 20, 20, 15,  5,-30 }, 
                                            {-30,  0, 15, 20, 20, 15,  0,-30 },
                                            {-30, -5, 10, 15, 15, 10,  5,-30 },
                                            {-40,-20,  0,  5,  5,  0,-20,-40 },
                                            {-50,-40,-30,-30,-30,-30,-40,-50 }};

    public static final int[][] BBishop = { {-20,-10,-10,-10,-10,-10,-10,-20 },
                                            {-10,  0,  0,  0,  0,  0,  0,-10 }, 
                                            {-10,  0,  5, 10, 10,  5,  0,-10 },
                                            {-10,  5,  5, 10, 10,  5,  5,-10 }, 
                                            {-10,  0, 10, 10, 10, 10,  0,-10 },
                                            {-10, 10, 10, 10, 10, 10, 10,-10 },
                                            {-10,  5,  0,  0,  0,  0,  5,-10 },
                                            {-20,-10,-10,-10,-10,-10,-10,-20 }};

    public static final int[][] BRook = { {  0,  0,  0,  0,  0,  0,  0,  0 },
                                          { -5, 10, 10, 10, 10, 10, 10, -5 }, 
                                          { -5,  0,  0,  0,  0,  0,  0, -5 },
                                          { -5,  0,  0,  0,  0,  0,  0, -5 }, 
                                          { -5,  0,  0,  0,  0,  0,  0, -5 },
                                          { -5,  0,  0,  0,  0,  0,  0, -5 },
                                          { -5,  0,  0,  0,  0,  0,  0, -5 },
                                          {  0,  0,  0,  5,  5,  0,  0,  0 }};
                                           
    public static final int[][] BQueen = { {-20,-10,-10, -5, -5,-10,-10,-20 },
                                           {-10,  0,  0,  0,  0,  0,  0,-10 }, 
                                           {-10,  0,  5,  5,  5,  5,  0,-10 },
                                           { -5,  0,  5,  5,  5,  5,  0, -5 }, 
                                           {  0,  0,  5,  5,  5,  5,  0, -5 },
                                           {-10,  5,  5,  5,  5,  5,  0,-10 },
                                           {-10,  0,  5,  0,  0,  0,  0,-10 },
                                           {-20,-10,-10, -5, -5,-10,-10,-20 }};

    public static final int[][] BKing = { {-30,-40,-40,-50,-50,-40,-40,-30 },
                                          {-30,-40,-40,-50,-50,-40,-40,-30 }, 
                                          {-30,-40,-40,-50,-50,-40,-40,-30 },
                                          {-30,-40,-40,-50,-50,-40,-40,-30 }, 
                                          {-20,-30,-30,-40,-40,-30,-30,-20 },
                                          {-10,-20,-20,-20,-20,-20,-20,-10 },
                                          { 20, 20,  0,  0,  0,  0, 20, 20 },
                                          { 20, 30, 10,  0,  0, 10, 30, 20 }};

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
        double eval = 0.0;
        for (int r = 0; r < Position.rows; r++) {
            for (int c = 0; c < Position.cols; c++) {
                if (b.board[c][r] == Position.Empty) {
                    continue;
                } else if (b.board[c][r] == Position.WKing) {
                    eval += 20000;
                    eval += Evaluator.WKing[r][c];
                } else if (b.board[c][r] == Position.WQueen) {
                    eval += 900;
                    eval += Evaluator.WQueen[r][c];
                } else if (b.board[c][r] == Position.WRook) {
                    eval += 500;
                    eval += Evaluator.WRook[r][c];
                } else if (b.board[c][r] == Position.WBishop) {
                    eval += 330;
                    eval += Evaluator.WBishop[r][c];
                } else if (b.board[c][r] == Position.WKnight) {
                    eval += 320;
                    eval += Evaluator.WKnight[r][c];
                } else if (b.board[c][r] == Position.WPawn) {
                    eval += 100;
                    eval += Evaluator.WPawn[r][c];
                } else if (b.board[c][r] == Position.BKing) {
                    eval -= 20000;
                    eval -= Evaluator.BKing[r][c];
                } else if (b.board[c][r] == Position.BQueen) {
                    eval -= 900;
                    eval -= Evaluator.BQueen[r][c];
                } else if (b.board[c][r] == Position.BRook) {
                    eval -= 500;
                    eval -= Evaluator.BRook[r][c];
                } else if (b.board[c][r] == Position.BBishop) {
                    eval -= 330;
                    eval -= Evaluator.BBishop[r][c];
                } else if (b.board[c][r] == Position.BKnight) {
                    eval -= 320;
                    eval -= Evaluator.BKnight[r][c];
                } else if (b.board[c][r] == Position.BPawn) {
                    eval -= 100;
                    eval -= Evaluator.BPawn[r][c];
                }
            }
        }
        return eval;
    }
}
