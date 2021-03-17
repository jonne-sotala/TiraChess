
package tirachess;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tirachess.domain.Evaluator;
import tirachess.domain.Position;


public class EvaluatorTest {

    Evaluator evaluator;
    Position position;

    @Before
    public void setUp() throws Exception {
        evaluator = new Evaluator();
        position = new Position();
    }

    @Test
    public void testEvaluateMethod() {
        assertEquals(0, this.evaluator.evaluate(position), 0.1);
    }

    @Test
    public void testAlphaBetaAndMinMax1() {
        double alphabeta = this.evaluator.alphabeta(position, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        double minmax = this.evaluator.minmax(position, 3);
        assertTrue(alphabeta == minmax);
    }

    @Test
    public void testAlphaBetaAndMinMax2() {
        String fen = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -";
        position.importFEN(fen);

        double alphabeta = this.evaluator.alphabeta(position, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        double minmax = this.evaluator.minmax(position, 3);
        assertTrue(alphabeta == minmax);
    }

    @Test
    public void testAlphaBetaAndMinMax3() {
        String fen = "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8";
        position.importFEN(fen);

        double alphabeta = this.evaluator.alphabeta(position, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        double minmax = this.evaluator.minmax(position, 3);
        assertTrue(alphabeta == minmax);
    }

    @Test
    public void testAlphaBetaAndMinMax4() {
        String fen = "r2qkb1r/pp2nppp/3p4/2pNN1B1/2BnP3/3P4/PPP2PPP/R2bK2R w KQkq - 1 0";
        position.importFEN(fen);

        double alphabeta = this.evaluator.alphabeta(position, 3, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        double minmax = this.evaluator.minmax(position, 3);
        assertTrue(alphabeta == minmax);
    }
}