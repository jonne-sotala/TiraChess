package tirachess;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {

    private Board b;

    @Before
    public void setUp() throws Exception {
        b = new Board();
        b.setUpBoard();
    }

    @Test
    public void testBoardHasRightSize() {
        assertEquals(8, b.board.length);
        assertEquals(8, b.board[0].length);
    }

    @Test
    public void testWhiteStartsOnBoard() {
        assertEquals(true, b.whitesMove);
    }

    @Test
    public void testBoardStartingPositionIsCorrect() {
        for (int col = 0; col < Board.cols; col++) {
            assertTrue(b.board[col][1] == Board.WPawn);
            assertTrue(b.board[col][Board.rows - 2] == Board.BPawn);
            if (col == 0 || col == Board.cols - 1) {
                assertTrue(b.board[col][0] == Board.WRook);
                assertTrue(b.board[col][Board.rows - 1] == Board.BRook);
            } else if (col == 1 || col == Board.cols - 2) {
                assertTrue(b.board[col][0] == Board.WKnight);
                assertTrue(b.board[col][Board.rows - 1] == Board.BKnight);
            } else if (col == 2 || col == Board.cols - 3) {
                assertTrue(b.board[col][0] == Board.WBishop);
                assertTrue(b.board[col][Board.rows - 1] == Board.BBishop);
            } else if (col == 3) {
                assertTrue(b.board[col][0] == Board.WQueen);
                assertTrue(b.board[col][Board.rows - 1] == Board.BQueen);
            } else if (col == 4) {
                assertTrue(b.board[col][0] == Board.WKing);
                assertTrue(b.board[col][Board.rows - 1] == Board.BKing);
            }
        }

    }

}
