package tirachess;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import tirachess.datastructures.MyArrayList;
import tirachess.domain.Position;

/**
 * The tests for the class Position. The FEN positions have been taken from 
 * https://www.chessprogramming.org/Perft_Results. I used the first, the second
 * and the fifth position for my tests. 
 */
public class PositionTest {

    private Position position;

    @Before
    public void setUp() throws Exception {
        position = new Position();
    }

    private int countMoves(Position p, int depth) {
        if (depth == 0) {
            return 1;
        }
        int numOfMoves = 0;
        MyArrayList<Position> moves = p.getMoves();
        for (int i = 0; i < moves.size(); i++) {
            numOfMoves += countMoves(moves.get(i), depth - 1);
        }
        
        return numOfMoves;

    }

    @Test
    public void testMoveGenerationPosition1Depth1() {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        position.importFEN(fen);
        assertEquals(20, countMoves(position, 1));
    }

    @Test
    public void testMoveGenerationPosition1Depth2() {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        position.importFEN(fen);
        assertEquals(400, countMoves(position, 2));
    }

    @Test
    public void testMoveGenerationPosition1Depth3() {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        position.importFEN(fen);
        assertEquals(8902, countMoves(position, 3));
    }

    @Test
    public void testMoveGenerationPosition1Depth4() {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        position.importFEN(fen);
        assertEquals(197281, countMoves(position, 4));
    }

    @Test
    public void testMoveGenerationPosition2Depth1() {
        String fen = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -";
        position.importFEN(fen);
        assertEquals(48, countMoves(position, 1));
    }

    @Test
    public void testMoveGenerationPosition2Depth2() {
        String fen = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -";
        position.importFEN(fen);
        assertEquals(2039, countMoves(position, 2));
    }

    @Test
    public void testMoveGenerationPosition2Depth3() {
        String fen = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -";
        position.importFEN(fen);
        assertEquals(97862, countMoves(position, 3));
    }

    @Test
    public void testMoveGenerationPosition5Depth3() {
        String fen = "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8";
        position.importFEN(fen);
        assertEquals(62379, countMoves(position, 3));
    }

    @Test
    public void testBoardHasRightSize() {
        assertEquals(8, position.board.length);
        assertEquals(8, position.board[0].length);
    }

    @Test
    public void testWhiteStartsOnBoard() {
        assertEquals(true, position.whitesMove);
    }

    @Test
    public void testBoardStartingPositionIsCorrect() {
        for (int col = 0; col < Position.cols; col++) {
            assertTrue(position.board[col][1] == Position.WPawn);
            assertTrue(position.board[col][Position.rows - 2] == Position.BPawn);
            if (col == 0 || col == Position.cols - 1) {
                assertTrue(position.board[col][0] == Position.WRook);
                assertTrue(position.board[col][Position.rows - 1] == Position.BRook);
            } else if (col == 1 || col == Position.cols - 2) {
                assertTrue(position.board[col][0] == Position.WKnight);
                assertTrue(position.board[col][Position.rows - 1] == Position.BKnight);
            } else if (col == 2 || col == Position.cols - 3) {
                assertTrue(position.board[col][0] == Position.WBishop);
                assertTrue(position.board[col][Position.rows - 1] == Position.BBishop);
            } else if (col == 3) {
                assertTrue(position.board[col][0] == Position.WQueen);
                assertTrue(position.board[col][Position.rows - 1] == Position.BQueen);
            } else if (col == 4) {
                assertTrue(position.board[col][0] == Position.WKing);
                assertTrue(position.board[col][Position.rows - 1] == Position.BKing);
            }
        }
    }

    @Test
    public void testBoardGetMovesMethodGivesCorrectMovesAtStartForWhite() {
        MyArrayList<Position> moves = position.getMoves();
        assertEquals(20, moves.size());
    }

    @Test
    public void testBoardGetMovesMethodGivesCorrectMovesAtStartForBlack() {
        position.whitesMove = !position.whitesMove;
        MyArrayList<Position> moves = position.getMoves();
        assertEquals(20, moves.size());
    }

    @Test
    public void testGetKingMoves() {
        for (int i = 1; i < Position.cols - 1; i++) {
            if (i == 4) {
                continue;
            }
            position.board[i][0] = Position.Empty;
            position.board[i][Position.rows - 1] = Position.Empty;
        }
        MyArrayList<Position> moves = new MyArrayList<Position>();
        position.getKingMoves(0, 4, moves);
        assertEquals(4, moves.size());
        position.whitesMove = !position.whitesMove;
        moves.clear();
        position.getKingMoves(7, 4, moves);
        assertEquals(4, moves.size());
    }

    @Test
    public void testGetSpecialPawnMovesForWhite() {
        position.board[4][4] = Position.WPawn;
        position.board[2][4] = Position.WPawn;
        position.board[3][6] = Position.Empty;
        position.board[3][4] = Position.BPawn;

        position.board[0][6] = Position.WPawn;
        position.board[0][7] = Position.Empty;
        position.enPassantFile = 3;
        MyArrayList<Position> moves = new MyArrayList<>();
        position.getPawnMoves(6, 0, moves);
        position.getEnPassantMoves(moves);
        assertEquals(10, moves.size());
    }

    @Test
    public void testGetSpecialPawnMovesForBlack() {
        position.whitesMove = !position.whitesMove;
        position.board[4][3] = Position.BPawn;
        position.board[2][3] = Position.BPawn;
        position.board[3][1] = Position.Empty;
        position.board[3][3] = Position.WPawn;

        position.board[0][1] = Position.BPawn;
        position.board[0][0] = Position.Empty;
        position.enPassantFile = 3;
        MyArrayList<Position> moves = new MyArrayList<Position>();
        position.getPawnMoves(1, 0, moves);
        position.getEnPassantMoves(moves);
        assertEquals(10, moves.size());
    }

    @Test
    public void testIsOnBoardMethodGivesFalseWhenSquareOutside() {
        assertFalse(position.isOnBoard(10, 7));
        assertFalse(position.isOnBoard(-1, 2));
        assertFalse(position.isOnBoard(5, 11));
    }

    @Test
    public void testIsOnBoardMethodGivesTrueWhenSquareInside() {
        assertTrue(position.isOnBoard(5, 5));
        assertTrue(position.isOnBoard(1, 7));
        assertTrue(position.isOnBoard(4, 2));
    }

    @Test
    public void testIsEmptyWorksCorrectly() {
        assertTrue(position.isEmpty(position.board[4][4]));
        assertFalse(position.isEmpty(position.board[0][0]));
    }

    @Test
    public void testIsWhitePieceMethodWorksCorrectly() {
        assertTrue(position.isWhitePiece(position.board[5][0]));
        assertFalse(position.isWhitePiece(position.board[4][3]));
        assertFalse(position.isWhitePiece(position.board[4][7]));
    }

    @Test
    public void testIsBlackPieceMethodWorksCorrectly() {
        assertFalse(position.isBlackPiece(position.board[5][0]));
        assertFalse(position.isBlackPiece(position.board[4][3]));
        assertTrue(position.isBlackPiece(position.board[4][7]));
    }

    @Test
    public void testIsSameColorMethodWorksCorrectly() {
        assertTrue(position.isSameColor(position.board[2][0], position.board[5][1]));
        assertFalse(position.isSameColor(position.board[3][0], position.board[5][7]));
        assertFalse(position.isSameColor(position.board[2][0], position.board[5][3]));
    }

    @Test
    public void testIsCurrentPlayersPieceWorksCorrectly() {
        assertTrue(position.isCurrentPlayersPiece(position.board[1][1]));
        assertFalse(position.isCurrentPlayersPiece(position.board[1][3]));
        assertFalse(position.isCurrentPlayersPiece(position.board[1][7]));

        position.whitesMove = false;
        assertFalse(position.isCurrentPlayersPiece(position.board[1][1]));
        assertFalse(position.isCurrentPlayersPiece(position.board[1][3]));
        assertTrue(position.isCurrentPlayersPiece(position.board[1][7]));
    }

    @Test
    public void testPieceIsAttackedMethodWorksCorrectly() {
        position.board[4][1] = Position.WQueen;
        position.board[4][6] = Position.Empty;
        assertTrue(position.pieceIsAttacked(Position.BKing));
        assertFalse(position.pieceIsAttacked(Position.WKing));
    }

    @Test
    public void testGetCloneAndChangeTurnWorksCorrectly() {
        Position newBoard = position.getCloneAndChangeTurn();
        assertTrue(newBoard.whitesMove != position.whitesMove);
        for (int r = 0; r < Position.rows; r++) {
            for (int c = 0; c < Position.cols; c++) {
                assertTrue(position.board[c][r] == newBoard.board[c][r]);
            }
        }
    }

    @Test
    public void testPrint() {
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        position.print();
        System.setOut(oldOut);
        String output = new String(baos.toByteArray());
        assertTrue(output.contains(("r n b q k b n r")));
        assertTrue(output.contains(("R N B Q K B N R")));
    }
}
