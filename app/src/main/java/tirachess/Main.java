
package tirachess;

import tirachess.datastructures.MyArrayList;
import tirachess.datastructures.MyHashMap;
import tirachess.domain.Position;
import tirachess.domain.Evaluator;

/**
 * The main class.
 */
public class Main {

    /**
     * The main method. It playes a chess game and finishes. Currently here just for
     * testing purposes.
     */
    public static void main(String[] args) {
        Position p = new Position();
        System.out.println("GAME START!");
        p.print();
        Evaluator evaluator = new Evaluator();

        MyArrayList<Position> moves = p.getMoves();
        while (!moves.isEmpty()) {
            Position bestMove = moves.get(0);
            Double bestEval = evaluator.alphabeta(bestMove, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            for (int i = 0; i < moves.size(); i++) {
                Position move = moves.get(i);
                Double moveEval = evaluator.alphabeta(move, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
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
                System.out.println("White's evaluation: " + bestEval/100);
            } else {
                System.out.println("Blacks's evaluation: " + bestEval/100);
            }
            p = bestMove;
            p.print();
            System.out.println();
            moves = p.getMoves();
        }
        System.out.println("GAME ENDED!");
    }

}
