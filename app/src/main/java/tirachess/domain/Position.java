
package tirachess.domain;

import java.util.HashMap;

import tirachess.datastructures.MyArrayList;

/**
 * Board is a class that represents a chess board and keeps track of the
 * different positions in a chess game.
 */
public class Position {
    public int[][] board;
    public static final int rows = 8;
    public static final int cols = 8;
    public static final int Empty = 0;
    public static final int WKing = 1;
    public static final int WQueen = 2;
    public static final int WRook = 3;
    public static final int WBishop = 4;
    public static final int WKnight = 5;
    public static final int WPawn = 6;
    public static final int BKing = 7;
    public static final int BQueen = 8;
    public static final int BRook = 9;
    public static final int BBishop = 10;
    public static final int BKnight = 11;
    public static final int BPawn = 12;

    private static final int[] Kx = {  1,  1,  1,  0,  0, -1, -1, -1 };
    private static final int[] Ky = {  1,  0, -1,  1, -1,  1,  0, -1 };

    private static final int[] Nx = { -2, -2, -1, -1,  1,  1,  2,  2 };
    private static final int[] Ny = {  1, -1,  2, -2,  2, -2,  1, -1 };

    private static final int[][] Bx = { {  1,  2,  3,  4,  5,  6,  7 }, 
                                        {  1,  2,  3,  4,  5,  6,  7 },
                                        { -1, -2, -3, -4, -5, -6, -7 }, 
                                        { -1, -2, -3, -4, -5, -6, -7 } };
    private static final int[][] By = { {  1,  2,  3,  4,  5,  6,  7 }, 
                                        { -1, -2, -3, -4, -5, -6, -7 },
                                        {  1,  2,  3,  4,  5,  6,  7 }, 
                                        { -1, -2, -3, -4, -5, -6, -7 } };

    private static final int[][] Rx = { {  0,  0,  0,  0,  0,  0,  0 }, 
                                        {  0,  0,  0,  0,  0,  0,  0 }, 
                                        {  1,  2,  3,  4,  5,  6,  7 },
                                        { -1, -2, -3, -4, -5, -6, -7 } };
    private static final int[][] Ry = { {  1,  2,  3,  4,  5,  6,  7 }, 
                                        { -1, -2, -3, -4, -5, -6, -7 },
                                        {  0,  0,  0,  0,  0,  0,  0 }, 
                                        {  0,  0,  0,  0,  0,  0,  0 } };

    public boolean whitesMove;
    public int halfMoveCounter;
    public int fullMoveCounter;
    public int enPassantFile;
    public boolean whiteKingSideCastlingAllowed;
    public boolean whiteQueenSideCastlingAllowed;
    public boolean blackKingSideCastlingAllowed;
    public boolean blackQueenSideCastlingAllowed;
    public boolean threeFoldRepetition;
    public HashMap<Long, Integer> pastPositions;

    public Zobrist zobrist;

    /**
     * This is the constructor that creates the position, sets it up, and sets white
     * to be the first to move.
     */
    public Position() {
        this.board = new int[cols][rows];
        this.pastPositions = new HashMap<>();
        this.enPassantFile = -1;
        this.setUpBoard();
    }

    /**
     * This is the constructor that creates position and sets it up to a specific
     * state given in parameters.
     * 
     * @param board           The board state that will be given to the object.
     * @param whitesMove      Boolean value that tells whether it is white's turn.
     * @param halfMoveCounter Integer value for the amount of moves since last
     *                        capture or pawn move.
     * @param fullMoveCounter Integer value for the move count in the game (one move
     *                        is white's move + black's move).
     */
    public Position(int[][] board, boolean whitesMove, int halfMoveCounter, int fullMoveCounter) {
        this.board = board;
        this.whitesMove = whitesMove;
        this.halfMoveCounter = halfMoveCounter;
        this.fullMoveCounter = fullMoveCounter;
        this.enPassantFile = -1;
    }

    /**
     * A method that sets up the pieces on the board when the game starts. Also sets
     * white to be the starting player and move counter to zero.
     */
    public void setUpBoard() {
        this.whitesMove = true;
        this.halfMoveCounter = 0;
        this.fullMoveCounter = 1;
        this.whiteKingSideCastlingAllowed = true;
        this.whiteQueenSideCastlingAllowed = true;
        this.blackKingSideCastlingAllowed = true;
        this.blackQueenSideCastlingAllowed = true;
        this.threeFoldRepetition = false;
        this.zobrist = new Zobrist();
        this.updatePastPositions();
        for (int col = 0; col < cols; col++) {
            this.board[col][1] = WPawn;
            this.board[col][rows - 2] = BPawn;
            if (col == 0 || col == cols - 1) {
                this.board[col][0] = WRook;
                this.board[col][rows - 1] = BRook;
            } else if (col == 1 || col == cols - 2) {
                this.board[col][0] = WKnight;
                this.board[col][rows - 1] = BKnight;
            } else if (col == 2 || col == cols - 3) {
                this.board[col][0] = WBishop;
                this.board[col][rows - 1] = BBishop;
            } else if (col == 3) {
                this.board[col][0] = WQueen;
                this.board[col][rows - 1] = BQueen;
            } else if (col == 4) {
                this.board[col][0] = WKing;
                this.board[col][rows - 1] = BKing;
            }
        }
    }

    /**
     * A method that prints the position.
     */
    public void print() {
        for (int r = rows - 1; r >= 0; r--) {
            for (int c = 0; c < cols; c++) {
                int v = this.board[c][r];
                if (v == Empty) {
                    System.out.print(".");
                }
                if (v == WKing) {
                    System.out.print("K");
                }
                if (v == WQueen) {
                    System.out.print("Q");
                }
                if (v == WRook) {
                    System.out.print("R");
                }
                if (v == WBishop) {
                    System.out.print("B");
                }
                if (v == WKnight) {
                    System.out.print("N");
                }
                if (v == WPawn) {
                    System.out.print("P");
                }
                if (v == BKing) {
                    System.out.print("k");
                }
                if (v == BQueen) {
                    System.out.print("q");
                }
                if (v == BRook) {
                    System.out.print("r");
                }
                if (v == BBishop) {
                    System.out.print("b");
                }
                if (v == BKnight) {
                    System.out.print("n");
                }
                if (v == BPawn) {
                    System.out.print("p");
                }
                System.out.print(' ');
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * A method that calculates all the possible next moves.
     * 
     * @return all the next possible position.
     */
    public MyArrayList<Position> getMoves() {
        MyArrayList<Position> moves = new MyArrayList<Position>();
        for (int r = 0; r < Position.rows; r++) {
            for (int c = 0; c < Position.cols; c++) {
                int piece = this.board[c][r];
                if (!this.isCurrentPlayersPiece(piece)) {
                    continue;
                }

                if (piece == Position.WKing || piece == Position.BKing) {
                    this.getKingMoves(r, c, moves);
                } else if (piece == Position.WQueen || piece == Position.BQueen) {
                    this.getQueenMoves(r, c, moves);
                } else if (piece == Position.WRook || piece == Position.BRook) {
                    this.getRookMoves(r, c, moves);
                } else if (piece == Position.WBishop || piece == Position.BBishop) {
                    this.getBishopMoves(r, c, moves);
                } else if (piece == Position.WKnight || piece == Position.BKnight) {
                    this.getKnightMoves(r, c, moves);
                } else if (piece == Position.WPawn || piece == Position.BPawn) {
                    this.getPawnMoves(r, c, moves);
                    this.getEnPassantMoves(moves);
                }

            }
        }
        for (int i = 0; i < moves.size(); i++) {
            moves.get(i).updatePastPositions();
        }
        return moves;
    }

    /**
     * A method that will calculate all the moves for the king(s).
     * 
     * @param r     The row of the board where the king is located.
     * @param c     The column of the board where the king is located.
     * @param moves The list of moves that have been calculated for the current
     *              position.
     */
    public void getKingMoves(int r, int c, MyArrayList<Position> moves) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int r2 = r + i;
                int c2 = c + j;
                if (this.isOnBoard(r2, c2)) {
                    int piece1 = this.board[c][r];
                    int piece2 = this.board[c2][r2];
                    if (isEmpty(piece2) || !isSameColor(piece1, piece2)) {
                        Position newPosition = this.getCloneAndChangeTurn();
                        if (!newPosition.isEmpty(piece2)) {
                            newPosition.resetHalfMoveCounter();
                        }
                        newPosition.board[c2][r2] = newPosition.board[c][r];
                        newPosition.board[c][r] = Position.Empty;
                        if (this.isWhitePiece(piece1)) {
                            newPosition.whiteKingSideCastlingAllowed = false;
                            newPosition.whiteQueenSideCastlingAllowed = false;
                        } else {
                            newPosition.blackKingSideCastlingAllowed = false;
                            newPosition.blackQueenSideCastlingAllowed = false;
                        }
                        if (!newPosition.isInvalid()) {
                            moves.add(newPosition);
                        }
                    }
                }
            }
        }
        // CASTLING
        int king = this.board[c][r];
        if (this.isWhitePiece(king) && this.whiteQueenSideCastlingAllowed 
                && this.squareIsEmpty(r, c - 1) && this.squareIsEmpty(r, c - 2) 
                && this.squareIsEmpty(r, c - 3)) {
            this.board[c - 1][r] = king;
            this.board[c - 2][r] = king;
            if (!this.pieceIsAttacked(king)) {
                Position newPosition = this.getCloneAndChangeTurn();
                newPosition.board[c][r] = Position.Empty;
                newPosition.board[c - 1][r] = newPosition.board[0][r];
                newPosition.board[0][r] = Position.Empty;
                newPosition.whiteKingSideCastlingAllowed = false;
                newPosition.whiteQueenSideCastlingAllowed = false;
                if (!newPosition.isInvalid()) {
                    moves.add(newPosition);
                }
            }
            this.board[c - 1][r] = Position.Empty;
            this.board[c - 2][r] = Position.Empty;
        }
        if (this.isWhitePiece(king) && this.whiteKingSideCastlingAllowed 
                && this.squareIsEmpty(r, c + 1) && this.squareIsEmpty(r, c + 2)) {
            this.board[c + 1][r] = king;
            this.board[c + 2][r] = king;
            if (!this.pieceIsAttacked(king)) {
                Position newPosition = this.getCloneAndChangeTurn();
                newPosition.board[c][r] = Position.Empty;
                newPosition.board[c + 1][r] = newPosition.board[Position.rows - 1][r];
                newPosition.board[Position.rows - 1][r] = Position.Empty;
                newPosition.whiteKingSideCastlingAllowed = false;
                newPosition.whiteQueenSideCastlingAllowed = false;
                if (!newPosition.isInvalid()) {
                    moves.add(newPosition);
                }
            }
            this.board[c + 1][r] = Position.Empty;
            this.board[c + 2][r] = Position.Empty;
        }
        if (this.isBlackPiece(king) && this.blackQueenSideCastlingAllowed 
                && this.squareIsEmpty(r, c - 1) && this.squareIsEmpty(r, c - 2) 
                && this.squareIsEmpty(r, c - 3)) {
            this.board[c - 1][r] = king;
            this.board[c - 2][r] = king;
            if (!this.pieceIsAttacked(king)) {
                Position newPosition = this.getCloneAndChangeTurn();
                newPosition.board[c][r] = Position.Empty;
                newPosition.board[c - 1][r] = newPosition.board[0][r];
                newPosition.board[0][r] = Position.Empty;
                newPosition.blackKingSideCastlingAllowed = false;
                newPosition.blackQueenSideCastlingAllowed = false;
                if (!newPosition.isInvalid()) {
                    moves.add(newPosition);
                }
            }
            this.board[c - 1][r] = Position.Empty;
            this.board[c - 2][r] = Position.Empty;
        }
        if (this.isBlackPiece(king) && this.blackKingSideCastlingAllowed 
                && this.squareIsEmpty(r, c + 1) && this.squareIsEmpty(r, c + 2)) {
            this.board[c + 1][r] = king;
            this.board[c + 2][r] = king;
            if (!this.pieceIsAttacked(king)) {
                Position newPosition = this.getCloneAndChangeTurn();
                newPosition.board[c][r] = Position.Empty;
                newPosition.board[c + 1][r] = newPosition.board[Position.rows - 1][r];
                newPosition.board[Position.rows - 1][r] = Position.Empty;
                newPosition.blackKingSideCastlingAllowed = false;
                newPosition.blackQueenSideCastlingAllowed = false;
                if (!newPosition.isInvalid()) {
                    moves.add(newPosition);
                }
            }
            this.board[c + 1][r] = Position.Empty;
            this.board[c + 2][r] = Position.Empty;
        }
    }

    /**
     * A method that will calculate all the moves for the queens(s).
     * 
     * @param r     The row of the board where the queen is located.
     * @param c     The column of the board where the queen is located.
     * @param moves The list of moves that have been calculated for the current
     *              position.
     */
    public void getQueenMoves(int r, int c, MyArrayList<Position> moves) {
        this.getRookMoves(r, c, moves);
        this.getBishopMoves(r, c, moves);
    }

    /**
     * A method that will calculate all the moves for the rook(s).
     * 
     * @param r     The row of the board where the rook is located.
     * @param c     The column of the board where the rook is located.
     * @param moves The list of moves that have been calculated for the current
     *              position.
     */
    public void getRookMoves(int r, int c, MyArrayList<Position> moves) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Position.Rx[i].length; j++) {
                int r2 = r + Position.Rx[i][j];
                int c2 = c + Position.Ry[i][j];
                if (!this.isOnBoard(r2, c2)) {
                    break;
                }
                int piece1 = this.board[c][r];
                int piece2 = this.board[c2][r2];
                if (!this.isEmpty(piece2) && this.isSameColor(piece1, piece2)) {
                    break;
                }
                Position newPosition = this.getCloneAndChangeTurn();
                if (!newPosition.isEmpty(piece2)) {
                    newPosition.resetHalfMoveCounter();
                }
                newPosition.board[c2][r2] = newPosition.board[c][r];
                newPosition.board[c][r] = Position.Empty;
                if (c == 0 && r == 0) {
                    newPosition.whiteQueenSideCastlingAllowed = false;
                } else if (c == Position.cols - 1 && r == 0) {
                    newPosition.whiteKingSideCastlingAllowed = false;
                } else if (c == 0 && r == Position.rows - 1) {
                    newPosition.blackQueenSideCastlingAllowed = false;
                } else if (c == Position.cols - 1 && r == Position.rows - 1) {
                    newPosition.blackKingSideCastlingAllowed = false;
                }
                if (!newPosition.isInvalid()) {
                    moves.add(newPosition);
                }
                if (!this.isEmpty(piece2)) {
                    break;
                }
            }
        }
    }

    /**
     * A method that will calculate all the moves for the bishop(s).
     * 
     * @param r     The row of the board where the bishop is located.
     * @param c     The column of the board where the bishop is located.
     * @param moves The list of moves that have been calculated for the current
     *              position.
     */
    public void getBishopMoves(int r, int c, MyArrayList<Position> moves) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Position.Bx[i].length; j++) {
                int r2 = r + Position.Bx[i][j];
                int c2 = c + Position.By[i][j];
                if (!this.isOnBoard(r2, c2)) {
                    break;
                }
                int piece1 = this.board[c][r];
                int piece2 = this.board[c2][r2];
                if (!this.isEmpty(piece2) && this.isSameColor(piece1, piece2)) {
                    break;
                }
                Position newPosition = this.getCloneAndChangeTurn();
                if (!newPosition.isEmpty(piece2)) {
                    newPosition.resetHalfMoveCounter();
                }
                newPosition.board[c2][r2] = newPosition.board[c][r];
                newPosition.board[c][r] = Position.Empty;
                if (!newPosition.isInvalid()) {
                    moves.add(newPosition);
                }
                if (!this.isEmpty(piece2)) {
                    break;
                }
            }
        }
    }

    /**
     * A method that will calculate all the moves for the knight(s).
     * 
     * @param r     The row of the board where the knight is located.
     * @param c     The column of the board where the knight is located.
     * @param moves The list of moves that have been calculated for the current
     *              position.
     */
    public void getKnightMoves(int r, int c, MyArrayList<Position> moves) {
        for (int i = 0; i < Position.Nx.length; i++) {
            int r2 = r + Position.Nx[i];
            int c2 = c + Position.Ny[i];
            if (!this.isOnBoard(r2, c2)) {
                continue;
            }
            int piece1 = this.board[c][r];
            int piece2 = this.board[c2][r2];
            if (!this.isEmpty(piece2) && this.isSameColor(piece1, piece2)) {
                continue;
            }
            Position newPosition = this.getCloneAndChangeTurn();
            if (!newPosition.isEmpty(piece2)) {
                newPosition.resetHalfMoveCounter();
            }
            newPosition.board[c2][r2] = newPosition.board[c][r];
            newPosition.board[c][r] = Position.Empty;
            if (!newPosition.isInvalid()) {
                moves.add(newPosition);
            }
        }
    }

    /**
     * A method that will calculate all the moves for the pawn(s) except for en
     * passant.
     * 
     * @param r     The row of the board where the pawn is located.
     * @param c     The column of the board where the pawn is located.
     * @param moves The list of moves that have been calculated for the current
     *              position.
     */
    public void getPawnMoves(int r, int c, MyArrayList<Position> moves) {
        // WHITE PAWNS
        if (this.whitesMove) {
            int r2 = r + 1;
            for (int c2 = c - 1; c2 <= c + 1; c2++) {
                // CAPTURING
                if (this.isOnBoard(r2, c2) && c2 != c) {
                    int piece1 = this.board[c][r];
                    int piece2 = this.board[c2][r2];
                    if (!this.isEmpty(piece2) && !this.isSameColor(piece1, piece2)) {
                        if (!this.promotePawn(r, c, r2, c2, moves)) {
                            Position newPosition = this.getCloneAndChangeTurn();
                            newPosition.board[c][r] = Position.Empty;
                            newPosition.board[c2][r2] = Position.WPawn;
                            newPosition.resetHalfMoveCounter();
                            if (!newPosition.isInvalid()) {
                                moves.add(newPosition);
                            }
                        }
                    }
                    // MOVING 1 SQUARE
                } else if (this.isOnBoard(r2, c2)) {
                    int piece2 = this.board[c2][r2];
                    if (this.isEmpty(piece2)) {
                        if (!this.promotePawn(r, c, r2, c2, moves)) {
                            Position newPosition = this.getCloneAndChangeTurn();
                            newPosition.board[c][r] = Position.Empty;
                            newPosition.board[c2][r2] = Position.WPawn;
                            newPosition.resetHalfMoveCounter();
                            if (!newPosition.isInvalid()) {
                                moves.add(newPosition);
                            }
                        }
                        // MOVING 2 SQUARES
                        r2++;
                        if (this.isOnBoard(r2, c2)) {
                            piece2 = this.board[c2][r2];
                            if (r == 1 && this.isEmpty(piece2)) {
                                Position newPosition = this.getCloneAndChangeTurn();
                                newPosition.board[c][r] = Position.Empty;
                                newPosition.board[c2][r2] = Position.WPawn;
                                newPosition.enPassantFile = c;
                                newPosition.resetHalfMoveCounter();
                                if (!newPosition.isInvalid()) {
                                    moves.add(newPosition);
                                }
                            }
                        }
                        r2--;
                    }
                }
            }
            // BLACK PAWNS
        } else {
            int r2 = r - 1;
            for (int c2 = c - 1; c2 <= c + 1; c2++) {
                // CAPTURING
                if (this.isOnBoard(r2, c2) && c2 != c) {
                    int piece1 = this.board[c][r];
                    int piece2 = this.board[c2][r2];
                    if (!this.isEmpty(piece2) && !this.isSameColor(piece1, piece2)) {
                        if (!this.promotePawn(r, c, r2, c2, moves)) {
                            Position newPosition = this.getCloneAndChangeTurn();
                            newPosition.board[c][r] = Position.Empty;
                            newPosition.board[c2][r2] = Position.BPawn;
                            newPosition.resetHalfMoveCounter();
                            if (!newPosition.isInvalid()) {
                                moves.add(newPosition);
                            }
                        }
                    }
                    // MOVING 1 SQUARE
                } else if (this.isOnBoard(r2, c2)) {
                    int piece2 = this.board[c2][r2];
                    if (this.isEmpty(piece2)) {
                        if (!this.promotePawn(r, c, r2, c2, moves)) {
                            Position newPosition = this.getCloneAndChangeTurn();
                            newPosition.board[c][r] = Position.Empty;
                            newPosition.board[c2][r2] = Position.BPawn;
                            newPosition.resetHalfMoveCounter();
                            if (!newPosition.isInvalid()) {
                                moves.add(newPosition);
                            }
                        }
                        // MOVING 2 SQUARES
                        r2--;
                        if (this.isOnBoard(r2, c2)) {
                            piece2 = this.board[c2][r2];
                            if (r == Position.rows - 2 && this.isEmpty(piece2)) {
                                Position newPosition = this.getCloneAndChangeTurn();
                                newPosition.board[c][r] = Position.Empty;
                                newPosition.board[c2][r2] = Position.BPawn;
                                newPosition.enPassantFile = c;
                                newPosition.resetHalfMoveCounter();
                                if (!newPosition.isInvalid()) {
                                    moves.add(newPosition);
                                }
                            }
                        }
                        r2++;
                    }
                }
            }
        }
    }

    /**
     * A method that will calculate all en passant moves in the position.
     * 
     * @param moves The list of moves that have been calculated for the current
     *              position.
     */
    public void getEnPassantMoves(MyArrayList<Position> moves) {
        if (this.enPassantFile != -1) {
            if (this.whitesMove) {
                if (this.isOnBoard(4, this.enPassantFile - 1)
                        && this.board[this.enPassantFile - 1][4] == Position.WPawn) {
                    Position newPosition = this.getCloneAndChangeTurn();
                    newPosition.board[this.enPassantFile - 1][4] = Position.Empty;
                    newPosition.board[this.enPassantFile][4] = Position.Empty;
                    newPosition.board[this.enPassantFile][5] = Position.WPawn;
                    newPosition.resetHalfMoveCounter();
                    if (!newPosition.isInvalid()) {
                        moves.add(newPosition);
                    }
                }
                if (this.isOnBoard(4, this.enPassantFile + 1)
                        && this.board[this.enPassantFile + 1][4] == Position.WPawn) {
                    Position newPosition = this.getCloneAndChangeTurn();
                    newPosition.board[this.enPassantFile + 1][4] = Position.Empty;
                    newPosition.board[this.enPassantFile][4] = Position.Empty;
                    newPosition.board[this.enPassantFile][5] = Position.WPawn;
                    newPosition.resetHalfMoveCounter();
                    if (!newPosition.isInvalid()) {
                        moves.add(newPosition);
                    }
                }
            } else {
                if (this.isOnBoard(3, this.enPassantFile - 1)
                        && this.board[this.enPassantFile - 1][3] == Position.BPawn) {
                    Position newPosition = this.getCloneAndChangeTurn();
                    newPosition.board[this.enPassantFile - 1][3] = Position.Empty;
                    newPosition.board[this.enPassantFile][3] = Position.Empty;
                    newPosition.board[this.enPassantFile][2] = Position.BPawn;
                    newPosition.resetHalfMoveCounter();
                    if (!newPosition.isInvalid()) {
                        moves.add(newPosition);
                    }
                }
                if (this.isOnBoard(3, this.enPassantFile + 1)
                        && this.board[this.enPassantFile + 1][3] == Position.BPawn) {
                    Position newPosition = this.getCloneAndChangeTurn();
                    newPosition.board[this.enPassantFile + 1][3] = Position.Empty;
                    newPosition.board[this.enPassantFile][3] = Position.Empty;
                    newPosition.board[this.enPassantFile][2] = Position.BPawn;
                    newPosition.resetHalfMoveCounter();
                    if (!newPosition.isInvalid()) {
                        moves.add(newPosition);
                    }
                }
            }
        }
    }

    /**
     * A method that will calculate all the promotions for a pawn that has reached
     * the last rank.
     * 
     * @param r     The row of the board where the pawn is located.
     * @param c     The column of the board where the pawn is located.
     * @param moves The list of moves that have been calculated for the current
     *              position.
     * @return boolean Returns a boolean on whether the pawn could be promoted or
     *         not.
     */
    public boolean promotePawn(int r, int c, int r2, int c2, MyArrayList<Position> moves) {
        if (r2 == 0) {
            int[] pieces = { Position.BQueen, Position.BRook, Position.BBishop, Position.BKnight };
            for (int piece : pieces) {
                Position newPosition = this.getCloneAndChangeTurn();
                newPosition.board[c][r] = Position.Empty;
                newPosition.board[c2][r2] = piece;
                newPosition.resetHalfMoveCounter();
                if (!newPosition.isInvalid()) {
                    moves.add(newPosition);
                }
            }
            return true;
        } else if (r2 == Position.rows - 1) {
            int[] pieces = { Position.WQueen, Position.WRook, Position.WBishop, Position.WKnight };
            for (int piece : pieces) {
                Position newPosition = this.getCloneAndChangeTurn();
                newPosition.board[c][r] = Position.Empty;
                newPosition.board[c2][r2] = piece;
                newPosition.resetHalfMoveCounter();
                if (!newPosition.isInvalid()) {
                    moves.add(newPosition);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * A method that tells whether a square is inside the board or not.
     * 
     * @param r The row of the board where the square is located.
     * @param c The column of the board where the square is located.
     * @return boolean Returns true when the square is inside the board.
     */
    public boolean isOnBoard(int r, int c) {
        return r >= 0 && r < Position.rows && c >= 0 && c < Position.cols;
    }

    /**
     * A method that tells whether there is a piece on a square or not.
     * 
     * @param r The row of the board where the square is located.
     * @param c The column of the board where the square is located.
     * @return boolean Returns true when there is no piece on the square.
     */
    public boolean squareIsEmpty(int r, int c) {
        return this.board[c][r] == Position.Empty;
    }

    /**
     * A method that tells whether the given piece is "Empty".
     * 
     * @param piece The value of the piece.
     * @return boolean Returns true when the piece is "Empty".
     */
    public boolean isEmpty(int piece) {
        return piece == Position.Empty;
    }

    /**
     * A method that tells whether there is a white piece on a square.
     * 
     * @param piece The piece value of the square.
     * @return boolean Returns true when there is a white piece on the square.
     */
    public boolean isWhitePiece(int piece) {
        return piece > 0 && piece < 7;
    }

    /**
     * A method that tells whether there is a black piece on a square.
     * 
     * @param piece The piece value of the square.
     * @return boolean Returns true when there is a black piece on the square.
     */
    public boolean isBlackPiece(int piece) {
        return piece > 6 && piece < 13;
    }

    /**
     * A method that tells whether the two given pieces are the same color.
     * 
     * @param piece1 The first chess piece.
     * @param piece2 The second chess piece.
     * @return boolean Returns true when the two pieces are the same color.
     */
    public boolean isSameColor(int piece1, int piece2) {
        return this.isWhitePiece(piece1) == this.isWhitePiece(piece2);
    }

    /**
     * A method that tells whether the piece is owned by the player whose turn it
     * is.
     * 
     * @param piece The chess piece.
     * @return boolean Returns true when the piece belongs to the current player.
     */
    public boolean isCurrentPlayersPiece(int piece) {
        if (this.isEmpty(piece)) {
            return false;
        }
        return this.whitesMove == this.isWhitePiece(piece);
    }

    /**
     * A method that tells whether the chess position is valid or invalid. An
     * invalid position is a position where the king can be captured.
     * 
     * @return boolean Returns true when a king can be captured (so the position is
     *         illegal).
     */
    public boolean isInvalid() {
        if (this.whitesMove && this.pieceIsAttacked(Position.BKing)) {
            return true;
        }
        if (!this.whitesMove && this.pieceIsAttacked(Position.WKing)) {
            return true;
        }
        return false;
    }

    /**
     * A method that tells whether a given piece is attacked by another piece on the
     * board. This is only used for the kings.
     * 
     * @param piece The given chess piece.
     * @return boolean Returns true when the given piece is attacked by another
     *         piece.
     */
    public boolean pieceIsAttacked(int piece) {
        for (int r = 0; r < Position.rows; r++) {
            for (int c = 0; c < Position.cols; c++) {
                if (this.board[c][r] != piece) {
                    continue;
                }
                // KNIGHT ATTACKS
                for (int i = 0; i < Position.Nx.length; i++) {
                    int r2 = r + Position.Nx[i];
                    int c2 = c + Position.Ny[i];
                    if (!this.isOnBoard(r2, c2)) {
                        continue;
                    }
                    int piece1 = this.board[c][r];
                    int piece2 = this.board[c2][r2];
                    if ((piece2 == Position.BKnight || piece2 == Position.WKnight)
                            && !this.isSameColor(piece1, piece2)) {
                        return true;
                    }
                }
                // ROOK OR QUEEN ATTACKS
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < Position.Rx[i].length; j++) {
                        int r2 = r + Position.Rx[i][j];
                        int c2 = c + Position.Ry[i][j];
                        if (!this.isOnBoard(r2, c2)) {
                            break;
                        }
                        int piece1 = this.board[c][r];
                        int piece2 = this.board[c2][r2];
                        if (!this.isEmpty(piece2)) {
                            if ((piece2 == Position.WQueen || piece2 == Position.BQueen 
                                    || piece2 == Position.WRook || piece2 == Position.BRook) 
                                    && !this.isSameColor(piece1, piece2)) {
                                return true;
                            }
                            break;
                        }
                    }
                }
                // BISHOP OR QUEEN ATTACKS
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < Position.Bx[i].length; j++) {
                        int r2 = r + Position.Bx[i][j];
                        int c2 = c + Position.By[i][j];
                        if (!this.isOnBoard(r2, c2)) {
                            break;
                        }
                        int piece1 = this.board[c][r];
                        int piece2 = this.board[c2][r2];
                        if (!this.isEmpty(piece2)) {
                            if ((piece2 == Position.WQueen || piece2 == Position.BQueen 
                                    || piece2 == Position.WBishop || piece2 == Position.BBishop) 
                                    && !this.isSameColor(piece1, piece2)) {
                                return true;
                            }
                            break;
                        }
                    }
                }
                // PAWN ATTACKS
                if (this.isWhitePiece(this.board[c][r])) {
                    int r2 = r + 1;
                    for (int c2 = -1; c2 <= 1; c2 += 2) {
                        if (!this.isOnBoard(r2, c2)) {
                            continue;
                        }
                        int piece2 = this.board[c2][r2];
                        if (piece2 == Position.BPawn) {
                            return true;
                        }
                    }
                } else {
                    int r2 = r - 1;
                    for (int c2 = -1; c2 <= 1; c2 += 2) {
                        if (!this.isOnBoard(r2, c2)) {
                            continue;
                        }
                        int piece2 = this.board[c2][r2];
                        if (piece2 == Position.WPawn) {
                            return true;
                        }
                    }

                }
                // KING ATTACKS
                for (int i = 0; i < Position.Kx.length; i++) {
                    int r2 = r + Position.Kx[i];
                    int c2 = c + Position.Ky[i];
                    if (!this.isOnBoard(r2, c2)) {
                        continue;
                    }
                    int piece1 = this.board[c][r];
                    int piece2 = this.board[c2][r2];
                    if ((piece2 == Position.WKing || piece2 == Position.BKing) 
                        && !this.isSameColor(piece1, piece2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * A method copies the current chess position and changes the turn. You need to
     * manually give the copy of the board its new position.
     * 
     * @return Position Return a copy of the current board that has different
     *         player's turn.
     */
    public Position getCloneAndChangeTurn() {
        int[][] newBoard = new int[Position.rows][Position.cols];
        for (int r = 0; r < Position.rows; r++) {
            for (int c = 0; c < Position.cols; c++) {
                newBoard[c][r] = this.board[c][r];
            }
        }
        Position newPosition;
        if (this.whitesMove) {
            newPosition = new Position(newBoard, 
                                       !this.whitesMove, 
                                       this.halfMoveCounter + 1, 
                                       this.fullMoveCounter);
        } else {
            newPosition = new Position(newBoard, 
                                       !this.whitesMove, 
                                       this.halfMoveCounter + 1, 
                                       this.fullMoveCounter + 1);

        }
        newPosition.whiteKingSideCastlingAllowed = this.whiteKingSideCastlingAllowed;
        newPosition.whiteQueenSideCastlingAllowed = this.whiteQueenSideCastlingAllowed;
        newPosition.blackKingSideCastlingAllowed = this.blackKingSideCastlingAllowed;
        newPosition.blackQueenSideCastlingAllowed = this.blackQueenSideCastlingAllowed;
        newPosition.zobrist = this.zobrist;
        newPosition.pastPositions = new HashMap<>();
        for (Long key : this.pastPositions.keySet()) {
            newPosition.pastPositions.put(key, this.pastPositions.get(key));
        }
        return newPosition;
    }

    /**
     * A method that updates the past positions hashmap. If the position is already
     * encountered 2 times (this being the 3rd time), it will set the three-fold
     * repetition as true.
     */
    public void updatePastPositions() {
        Long zHash = this.getZHash();
        int currentCount = this.pastPositions.getOrDefault(zHash, 0);
        this.pastPositions.put(zHash, currentCount + 1);
        if (currentCount >= 2) {
            this.threeFoldRepetition = true;
        }
    }

    /**
     * A method that returns the Zobrist hash for the position.
     * 
     * @return Long Returns Zobrist hash.
     */
    public long getZHash() {
        return this.zobrist.getHash(this);
    }

    /**
     * A method that resets the halfMoveCounter back to zero. It is usually called
     * after a capture or a pawn move. The method also clears past positions.
     */
    public void resetHalfMoveCounter() {
        this.halfMoveCounter = 0;
        this.pastPositions.clear();
    }

    /**
     * Sets the board to be equivalent to the given board. The given board is
     * expected to be a 8x8 array.
     * 
     * @param board The board position that is imported.
     */
    public void importBoard(int[][] board) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                this.board[c][r] = board[c][r];
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (!(o instanceof Position)) {
            return false;
        }

        Position p = (Position) o;

        return this.getZHash() == p.getZHash();
    }

}
