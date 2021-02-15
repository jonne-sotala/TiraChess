package tirachess.domain;

import java.security.SecureRandom;

/**
 * A class that takes care of Zobrist hashing for different chess positions.
 * https://www.chessprogramming.org/Zobrist_Hashing.
 */
public class Zobrist {
    long[][][] zBoard;
    long[] zCastling;
    long[] zEnPassant;
    long zBlackMove;

    /**
     * Consructor that that creates pseudorandom numbers for the Zobrist class.
     * These numbers will all be unique to the instance of the class.
     */
    public Zobrist() {
        SecureRandom random = new SecureRandom();
        zBoard = new long[8][8][12];
        zCastling = new long[4];
        zEnPassant = new long[8];
        for (int piece = 0; piece < 12; piece++) {
            for (int col = 0; col < 8; col++) {
                for (int row = 0; row < 8; row++) {
                    zBoard[col][row][piece] = random.nextLong();
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            zCastling[i] = random.nextLong();
        }
        for (int i = 0; i < 8; i++) {
            zEnPassant[i] = random.nextLong();
        }
        zBlackMove = random.nextLong();
    }

    /**
     * A method that returns a specific Zobrish hash for the given position. There
     * should be near zero collisions with any other chess position.
     * 
     * @param p The given position whose Zobrish hash will be calculated.
     * @return Long Returns a Zobrish hash for the position.
     */
    public long getHash(Position p) {
        long zHash = 0L;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (p.board[c][r] == Position.Empty) {
                    continue;
                }
                int piece = p.board[c][r];
                zHash ^= zBoard[c][r][piece - 1];
            }
        }

        if (p.whiteKingSideCastlingAllowed) {
            zHash ^= zCastling[0];
        }
        if (p.whiteQueenSideCastlingAllowed) {
            zHash ^= zCastling[1];
        }
        if (p.blackKingSideCastlingAllowed) {
            zHash ^= zCastling[2];
        }
        if (p.blackQueenSideCastlingAllowed) {
            zHash ^= zCastling[3];
        }

        if (p.enPassantFile != -1) {
            zHash ^= zEnPassant[p.enPassantFile];
        }

        if (!p.whitesMove) {
            zHash ^= zBlackMove;
        }

        return zHash;
    }
}
