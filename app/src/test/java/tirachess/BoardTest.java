package tirachess;

import static org.junit.Assert.*;

import java.util.ArrayList;

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

    @Test
    public void testBoardGetMovesMethodGivesCorrectMovesAtStart() {
        ArrayList<Board> moves = b.getMoves();
        assertEquals(20, moves.size());
    }

    @Test
    public void testIsOnBoardMethodGivesFalseWhenSquareOutside() {
        assertFalse(b.isOnBoard(10, 7));
        assertFalse(b.isOnBoard(-1, 2));
        assertFalse(b.isOnBoard(5, 11));
    }

    @Test
    public void testIsOnBoardMethodGivesTrueWhenSquareInside() {
        assertTrue(b.isOnBoard(5, 5));
        assertTrue(b.isOnBoard(1, 7));
        assertTrue(b.isOnBoard(4, 2));
    }

    @Test
    public void testIsEmptyWorksCorrectly() {
        assertTrue(b.isEmpty(b.board[4][4]));
        assertFalse(b.isEmpty(b.board[0][0]));
    }

    @Test
    public void testIsWhitePieceMethodWorksCorrectly() {
        assertTrue(b.isWhitePiece(b.board[5][0]));
        assertFalse(b.isWhitePiece(b.board[4][3]));
        assertFalse(b.isWhitePiece(b.board[4][7]));
    }

    @Test
    public void testIsBlackPieceMethodWorksCorrectly() {
        assertFalse(b.isBlackPiece(b.board[5][0]));
        assertFalse(b.isBlackPiece(b.board[4][3]));
        assertTrue(b.isBlackPiece(b.board[4][7]));
    }

    @Test
    public void testIsSameColorMethodWorksCorrectly() {
        assertTrue(b.isSameColor(b.board[2][0], b.board[5][1]));
        assertFalse(b.isSameColor(b.board[3][0], b.board[5][7]));
        assertFalse(b.isSameColor(b.board[2][0], b.board[5][3]));
    }

    @Test
    public void testIsCurrentPlayersPieceWorksCorrectly() {
        assertTrue(b.isCurrentPlayersPiece(b.board[1][1]));
        assertFalse(b.isCurrentPlayersPiece(b.board[1][3]));
        assertFalse(b.isCurrentPlayersPiece(b.board[1][7]));

        b.whitesMove = false;
        assertFalse(b.isCurrentPlayersPiece(b.board[1][1]));
        assertFalse(b.isCurrentPlayersPiece(b.board[1][3]));
        assertTrue(b.isCurrentPlayersPiece(b.board[1][7]));
    }

    @Test
    public void testPieceIsAttackedMethodWorksCorrectly() {
        b.board[4][1] = Board.WQueen;
        b.board[4][6] = Board.Empty;
        assertTrue(b.pieceIsAttacked(Board.BKing));
        assertFalse(b.pieceIsAttacked(Board.WKing));
    }

    @Test
    public void testGetCloneAndChangeTurnWorksCorrectly() {
        Board new_board = b.getCloneAndChangeTurn();
        assertTrue(new_board.whitesMove != b.whitesMove);
        for (int r = 0; r < Board.rows; r++) {
            for (int c = 0; c < Board.cols; c++) {
                assertTrue(b.board[c][r] == new_board.board[c][r]);
            }
        }
    }
}
