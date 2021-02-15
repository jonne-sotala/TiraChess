package tirachess;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import tirachess.datastructures.MyArrayList;
import tirachess.domain.Position;

public class BoardTest {

    private Position p;

    @Before
    public void setUp() throws Exception {
        p = new Position();
    }

    @Test
    public void testBoardHasRightSize() {
        assertEquals(8, p.board.length);
        assertEquals(8, p.board[0].length);
    }

    @Test
    public void testWhiteStartsOnBoard() {
        assertEquals(true, p.whitesMove);
    }

    @Test
    public void testBoardStartingPositionIsCorrect() {
        for (int col = 0; col < Position.cols; col++) {
            assertTrue(p.board[col][1] == Position.WPawn);
            assertTrue(p.board[col][Position.rows - 2] == Position.BPawn);
            if (col == 0 || col == Position.cols - 1) {
                assertTrue(p.board[col][0] == Position.WRook);
                assertTrue(p.board[col][Position.rows - 1] == Position.BRook);
            } else if (col == 1 || col == Position.cols - 2) {
                assertTrue(p.board[col][0] == Position.WKnight);
                assertTrue(p.board[col][Position.rows - 1] == Position.BKnight);
            } else if (col == 2 || col == Position.cols - 3) {
                assertTrue(p.board[col][0] == Position.WBishop);
                assertTrue(p.board[col][Position.rows - 1] == Position.BBishop);
            } else if (col == 3) {
                assertTrue(p.board[col][0] == Position.WQueen);
                assertTrue(p.board[col][Position.rows - 1] == Position.BQueen);
            } else if (col == 4) {
                assertTrue(p.board[col][0] == Position.WKing);
                assertTrue(p.board[col][Position.rows - 1] == Position.BKing);
            }
        }
    }

    @Test
    public void testBoardGetMovesMethodGivesCorrectMovesAtStartForWhite() {
        MyArrayList<Position> moves = p.getMoves();
        assertEquals(20, moves.size());
    }

    @Test
    public void testBoardGetMovesMethodGivesCorrectMovesAtStartForBlack() {
        p.whitesMove = !p.whitesMove;
        MyArrayList<Position> moves = p.getMoves();
        assertEquals(20, moves.size());
    }

    @Test
    public void testGetKingMoves() {
        for (int i = 1; i < Position.cols - 1; i++) {
            if (i == 4) {
                continue;
            }
            p.board[i][0] = Position.Empty;
            p.board[i][Position.rows - 1] = Position.Empty;
        }
        MyArrayList<Position> moves = new MyArrayList<Position>();
        p.getKingMoves(0, 4, moves);
        assertEquals(4, moves.size());
        p.whitesMove = !p.whitesMove;
        moves.clear();
        p.getKingMoves(7, 4, moves);
        assertEquals(4, moves.size());
    }

    @Test
    public void testGetSpecialPawnMovesForWhite() {
        p.board[4][4] = Position.WPawn;
        p.board[2][4] = Position.WPawn;
        p.board[3][6] = Position.Empty;
        p.board[3][4] = Position.BPawn;

        p.board[0][6] = Position.WPawn;
        p.board[0][7] = Position.Empty;
        p.enPassantFile = 3;
        MyArrayList<Position> moves = new MyArrayList<>();
        p.getPawnMoves(6, 0, moves);
        p.getEnPassantMoves(moves);
        assertEquals(10, moves.size());
    }

    @Test
    public void testGetSpecialPawnMovesForBlack() {
        p.whitesMove = !p.whitesMove;
        p.board[4][3] = Position.BPawn;
        p.board[2][3] = Position.BPawn;
        p.board[3][1] = Position.Empty;
        p.board[3][3] = Position.WPawn;

        p.board[0][1] = Position.BPawn;
        p.board[0][0] = Position.Empty;
        p.enPassantFile = 3;
        MyArrayList<Position> moves = new MyArrayList<Position>();
        p.getPawnMoves(1, 0, moves);
        p.getEnPassantMoves(moves);
        assertEquals(10, moves.size());
    }

    @Test
    public void testIsOnBoardMethodGivesFalseWhenSquareOutside() {
        assertFalse(p.isOnBoard(10, 7));
        assertFalse(p.isOnBoard(-1, 2));
        assertFalse(p.isOnBoard(5, 11));
    }

    @Test
    public void testIsOnBoardMethodGivesTrueWhenSquareInside() {
        assertTrue(p.isOnBoard(5, 5));
        assertTrue(p.isOnBoard(1, 7));
        assertTrue(p.isOnBoard(4, 2));
    }

    @Test
    public void testIsEmptyWorksCorrectly() {
        assertTrue(p.isEmpty(p.board[4][4]));
        assertFalse(p.isEmpty(p.board[0][0]));
    }

    @Test
    public void testIsWhitePieceMethodWorksCorrectly() {
        assertTrue(p.isWhitePiece(p.board[5][0]));
        assertFalse(p.isWhitePiece(p.board[4][3]));
        assertFalse(p.isWhitePiece(p.board[4][7]));
    }

    @Test
    public void testIsBlackPieceMethodWorksCorrectly() {
        assertFalse(p.isBlackPiece(p.board[5][0]));
        assertFalse(p.isBlackPiece(p.board[4][3]));
        assertTrue(p.isBlackPiece(p.board[4][7]));
    }

    @Test
    public void testIsSameColorMethodWorksCorrectly() {
        assertTrue(p.isSameColor(p.board[2][0], p.board[5][1]));
        assertFalse(p.isSameColor(p.board[3][0], p.board[5][7]));
        assertFalse(p.isSameColor(p.board[2][0], p.board[5][3]));
    }

    @Test
    public void testIsCurrentPlayersPieceWorksCorrectly() {
        assertTrue(p.isCurrentPlayersPiece(p.board[1][1]));
        assertFalse(p.isCurrentPlayersPiece(p.board[1][3]));
        assertFalse(p.isCurrentPlayersPiece(p.board[1][7]));

        p.whitesMove = false;
        assertFalse(p.isCurrentPlayersPiece(p.board[1][1]));
        assertFalse(p.isCurrentPlayersPiece(p.board[1][3]));
        assertTrue(p.isCurrentPlayersPiece(p.board[1][7]));
    }

    @Test
    public void testPieceIsAttackedMethodWorksCorrectly() {
        p.board[4][1] = Position.WQueen;
        p.board[4][6] = Position.Empty;
        assertTrue(p.pieceIsAttacked(Position.BKing));
        assertFalse(p.pieceIsAttacked(Position.WKing));
    }

    @Test
    public void testGetCloneAndChangeTurnWorksCorrectly() {
        Position new_board = p.getCloneAndChangeTurn();
        assertTrue(new_board.whitesMove != p.whitesMove);
        for (int r = 0; r < Position.rows; r++) {
            for (int c = 0; c < Position.cols; c++) {
                assertTrue(p.board[c][r] == new_board.board[c][r]);
            }
        }
    }

    @Test
    public void testPrint() {
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        p.print();
        System.setOut(oldOut);
        String output = new String(baos.toByteArray());
        assertTrue(output.contains(("r n b q k b n r")));
        assertTrue(output.contains(("R N B Q K B N R")));
    }
}
