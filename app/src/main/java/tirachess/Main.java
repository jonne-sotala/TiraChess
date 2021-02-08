
package tirachess;

import java.util.ArrayList;

/**
 * The main class.
 */
public class Main {

    /**
     * The main method. It playes a chess game and finishes. Currently here just for
     * testing purposes.
     */
    public static void main(String[] args) {
        Board board = new Board();
        System.out.println("GAME START!");
        board.print();
        Evaluator evaluator = new Evaluator();
        ArrayList<Board> moves = board.getMoves();
        while (!moves.isEmpty()) {
            Board bestMove = moves.get(0);
            Double bestEval = evaluator.alphabeta(bestMove, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            for (Board move : moves) {
                Double moveEval = evaluator.alphabeta(move, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                if (board.whitesMove && moveEval > bestEval) {
                    bestMove = move;
                    bestEval = moveEval;
                } else if (!board.whitesMove && moveEval < bestEval) {
                    bestMove = move;
                    bestEval = moveEval;
                }
            }
            if (board.whitesMove) {
                System.out.println("White's evaluation: " + bestEval);
            } else {
                System.out.println("Blacks's evaluation: " + bestEval);
            }
            board.print();
            System.out.println();
            board = bestMove;
            moves = board.getMoves();
        }
        System.out.println("GAME ENDED!");
    }

}
