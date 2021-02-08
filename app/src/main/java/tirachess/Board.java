
package tirachess;

import java.util.ArrayList;

/**
 * Board is a class that represents a chess board and keeps track of the
 * different positions in a chess game.
 */
public class Board {
    int[][] board;
    static final int rows = 8;
    static final int cols = 8;

    static final int Empty = 0;
    static final int WKing = 1;
    static final int WQueen = 2;
    static final int WRook = 3;
    static final int WBishop = 4;
    static final int WKnight = 5;
    static final int WPawn = 6;
    static final int BKing = 7;
    static final int BQueen = 8;
    static final int BRook = 9;
    static final int BBishop = 10;
    static final int BKnight = 11;
    static final int BPawn = 12;

    static final int[] Nx = { -2, -2, -1, -1, 1, 1, 2, 2 };
    static final int[] Ny = { 1, -1, 2, -2, 2, -2, 1, -1 };
    static final int[][] Bx = { { 1, 2, 3, 4, 5, 6, 7 }, { 1, 2, 3, 4, 5, 6, 7 }, { -1, -2, -3, -4, -5, -6, -7 },
            { -1, -2, -3, -4, -5, -6, -7 } };
    static final int[][] By = { { 1, 2, 3, 4, 5, 6, 7 }, { -1, -2, -3, -4, -5, -6, -7 }, { 1, 2, 3, 4, 5, 6, 7 },
            { -1, -2, -3, -4, -5, -6, -7 } };
    static final int[][] Rx = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 1, 2, 3, 4, 5, 6, 7 },
            { -1, -2, -3, -4, -5, -6, -7 } };
    static final int[][] Ry = { { 1, 2, 3, 4, 5, 6, 7 }, { -1, -2, -3, -4, -5, -6, -7 }, { 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0 } };
    static final int[] Kx = { 1, 1, 1, 0, 0, -1, -1, -1 };
    static final int[] Ky = { 1, 0, -1, 1, -1, 1, 0, -1 };

    boolean whitesMove;

    /**
     * This is the constructor that creates the board, sets it up, and sets white to
     * be the first to move.
     */
    public Board() {
        this.board = new int[cols][rows];
        this.setUpBoard();
    }

    /**
     * This is the constructor that creates board and sets it up to a specific state
     * given in parameters.
     * 
     * @param board      The board state that will be given to the object.
     * @param whitesMove Boolean value that tells whether it is white's turn.
     */
    public Board(int[][] board, boolean whitesMove) {
        this.board = board;
        this.whitesMove = whitesMove;
    }

    /**
     * A method that sets up the pieces on the board when the game starts. Also sets
     * white to be the starting player.
     */
    public void setUpBoard() {
        this.whitesMove = true;
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
     * A method that prints the board position.
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
     * @return all the next possible board states
     */
    public ArrayList<Board> getMoves() {
        ArrayList<Board> moves = new ArrayList<>();
        for (int r = 0; r < Board.rows; r++) {
            for (int c = 0; c < Board.cols; c++) {
                int piece = this.board[c][r];
                if (!this.isCurrentPlayersPiece(piece)) {
                    continue;
                }

                if (piece == Board.WKing || piece == Board.BKing) {
                    this.getKingMoves(r, c, moves);
                } else if (piece == Board.WQueen || piece == Board.BQueen) {
                    this.getQueenMoves(r, c, moves);
                } else if (piece == Board.WRook || piece == Board.BRook) {
                    this.getRookMoves(r, c, moves);
                } else if (piece == Board.WBishop || piece == Board.BBishop) {
                    this.getBishopMoves(r, c, moves);
                } else if (piece == Board.WKnight || piece == Board.BKnight) {
                    this.getKnightMoves(r, c, moves);
                } else if (piece == Board.WPawn || piece == Board.BPawn) {
                    this.getPawnMoves(r, c, moves);
                }

            }
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
    public void getKingMoves(int r, int c, ArrayList<Board> moves) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int r2 = r + i;
                int c2 = c + j;
                if (this.isOnBoard(r2, c2)) {
                    int piece1 = this.board[c][r];
                    int piece2 = this.board[c2][r2];
                    if (isEmpty(piece2) || !isSameColor(piece1, piece2)) {
                        Board newBoard = this.getCloneAndChangeTurn();
                        newBoard.board[c2][r2] = newBoard.board[c][r];
                        newBoard.board[c][r] = Board.Empty;
                        if (!newBoard.positionIsInvalid()) {
                            moves.add(newBoard);
                        }
                    }
                }
            }
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
    public void getQueenMoves(int r, int c, ArrayList<Board> moves) {
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
    public void getRookMoves(int r, int c, ArrayList<Board> moves) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Board.Rx[i].length; j++) {
                int r2 = r + Board.Rx[i][j];
                int c2 = c + Board.Ry[i][j];
                if (!this.isOnBoard(r2, c2)) {
                    break;
                }
                int piece1 = this.board[c][r];
                int piece2 = this.board[c2][r2];
                if (!this.isEmpty(piece2) && this.isSameColor(piece1, piece2)) {
                    break;
                }
                Board newBoard = this.getCloneAndChangeTurn();
                newBoard.board[c2][r2] = newBoard.board[c][r];
                newBoard.board[c][r] = Board.Empty;
                if (!newBoard.positionIsInvalid()) {
                    moves.add(newBoard);
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
    public void getBishopMoves(int r, int c, ArrayList<Board> moves) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Board.Bx[i].length; j++) {
                int r2 = r + Board.Bx[i][j];
                int c2 = c + Board.By[i][j];
                if (!this.isOnBoard(r2, c2)) {
                    break;
                }
                int piece1 = this.board[c][r];
                int piece2 = this.board[c2][r2];
                if (!this.isEmpty(piece2) && this.isSameColor(piece1, piece2)) {
                    break;
                }
                Board newBoard = this.getCloneAndChangeTurn();
                newBoard.board[c2][r2] = newBoard.board[c][r];
                newBoard.board[c][r] = Board.Empty;
                if (!newBoard.positionIsInvalid()) {
                    moves.add(newBoard);
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
    public void getKnightMoves(int r, int c, ArrayList<Board> moves) {
        for (int i = 0; i < Board.Nx.length; i++) {
            int r2 = r + Board.Nx[i];
            int c2 = c + Board.Ny[i];
            if (!this.isOnBoard(r2, c2)) {
                continue;
            }
            int piece1 = this.board[c][r];
            int piece2 = this.board[c2][r2];
            if (!this.isEmpty(piece2) && this.isSameColor(piece1, piece2)) {
                continue;
            }
            Board newBoard = this.getCloneAndChangeTurn();
            newBoard.board[c2][r2] = newBoard.board[c][r];
            newBoard.board[c][r] = Board.Empty;
            if (!newBoard.positionIsInvalid()) {
                moves.add(newBoard);
            }
        }
    }

    /**
     * A method that will calculate all the moves for the pawn(s).
     * 
     * @param r     The row of the board where the pawn is located.
     * @param c     The column of the board where the pawn is located.
     * @param moves The list of moves that have been calculated for the current
     *              position.
     */
    public void getPawnMoves(int r, int c, ArrayList<Board> moves) {
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
                            Board newBoard = this.getCloneAndChangeTurn();
                            newBoard.board[c][r] = Board.Empty;
                            newBoard.board[c2][r2] = Board.WPawn;
                            if (!newBoard.positionIsInvalid()) {
                                moves.add(newBoard);
                            }
                        }
                    }
                    // MOVING 1 SQUARE
                } else if (this.isOnBoard(r2, c2)) {
                    int piece2 = this.board[c2][r2];
                    if (this.isEmpty(piece2)) {
                        if (!this.promotePawn(r, c, r2, c2, moves)) {
                            Board newBoard = this.getCloneAndChangeTurn();
                            newBoard.board[c][r] = Board.Empty;
                            newBoard.board[c2][r2] = Board.WPawn;
                            if (!newBoard.positionIsInvalid()) {
                                moves.add(newBoard);
                            }
                        }
                        // MOVING 2 SQUARES
                        r2++;
                        if (this.isOnBoard(r2, c2)) {
                            piece2 = this.board[c2][r2];
                            if (r == 1 && this.isEmpty(piece2)) {
                                Board newBoard = this.getCloneAndChangeTurn();
                                newBoard.board[c][r] = Board.Empty;
                                newBoard.board[c2][r2] = Board.WPawn;
                                if (!newBoard.positionIsInvalid()) {
                                    moves.add(newBoard);
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
                            Board newBoard = this.getCloneAndChangeTurn();
                            newBoard.board[c][r] = Board.Empty;
                            newBoard.board[c2][r2] = Board.BPawn;
                            if (!newBoard.positionIsInvalid()) {
                                moves.add(newBoard);
                            }
                        }
                    }
                    // MOVING 1 SQUARE
                } else if (this.isOnBoard(r2, c2)) {
                    int piece2 = this.board[c2][r2];
                    if (this.isEmpty(piece2)) {
                        if (!this.promotePawn(r, c, r2, c2, moves)) {
                            Board newBoard = this.getCloneAndChangeTurn();
                            newBoard.board[c][r] = Board.Empty;
                            newBoard.board[c2][r2] = Board.BPawn;
                            if (!newBoard.positionIsInvalid()) {
                                moves.add(newBoard);
                            }
                        }
                        // MOVING 2 SQUARES
                        r2--;
                        if (this.isOnBoard(r2, c2)) {
                            piece2 = this.board[c2][r2];
                            if (r == Board.rows - 2 && this.isEmpty(piece2)) {
                                Board newBoard = this.getCloneAndChangeTurn();
                                newBoard.board[c][r] = Board.Empty;
                                newBoard.board[c2][r2] = Board.BPawn;
                                if (!newBoard.positionIsInvalid()) {
                                    moves.add(newBoard);
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
    public boolean promotePawn(int r, int c, int r2, int c2, ArrayList<Board> moves) {
        if (r2 == 0) {
            int[] pieces = { Board.BQueen, Board.BRook, Board.BBishop, Board.BKnight };
            for (int piece : pieces) {
                Board newBoard = this.getCloneAndChangeTurn();
                newBoard.board[c][r] = Board.Empty;
                newBoard.board[c2][r2] = piece;
                if (!newBoard.positionIsInvalid()) {
                    moves.add(newBoard);
                }
            }
            return true;
        } else if (r2 == Board.rows - 1) {
            int[] pieces = { Board.WQueen, Board.WRook, Board.WBishop, Board.WKnight };
            for (int piece : pieces) {
                Board new_board = this.getCloneAndChangeTurn();
                new_board.board[c][r] = Board.Empty;
                new_board.board[c2][r2] = piece;
                if (!new_board.positionIsInvalid()) {
                    moves.add(new_board);
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
        return r >= 0 && r < Board.rows && c >= 0 && c < Board.cols;
    }

    /**
     * A method that tells whether there is a piece on a square or not.
     * 
     * @param piece The piece value of the square.
     * @return boolean Returns true when there is no piece on the square.
     */
    public boolean isEmpty(int piece) {
        return piece == Board.Empty;
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
    public boolean positionIsInvalid() {
        if (this.whitesMove && this.pieceIsAttacked(Board.BKing)) {
            return true;
        }
        if (!this.whitesMove && this.pieceIsAttacked(Board.WKing)) {
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
        for (int r = 0; r < Board.rows; r++) {
            for (int c = 0; c < Board.cols; c++) {
                if (this.board[c][r] != piece) {
                    continue;
                }
                // KNIGHT ATTACKS
                for (int i = 0; i < Board.Nx.length; i++) {
                    int r2 = r + Board.Nx[i];
                    int c2 = c + Board.Ny[i];
                    if (!this.isOnBoard(r2, c2)) {
                        continue;
                    }
                    int piece1 = this.board[c][r];
                    int piece2 = this.board[c2][r2];
                    if ((piece2 == Board.BKnight || piece2 == Board.WKnight) && !this.isSameColor(piece1, piece2)) {
                        return true;
                    }
                }
                // ROOK OR QUEEN ATTACKS
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < Board.Rx[i].length; j++) {
                        int r2 = r + Board.Rx[i][j];
                        int c2 = c + Board.Ry[i][j];
                        if (!this.isOnBoard(r2, c2)) {
                            break;
                        }
                        int piece1 = this.board[c][r];
                        int piece2 = this.board[c2][r2];
                        if (!this.isEmpty(piece2)) {
                            if ((piece2 == Board.WQueen || piece2 == Board.BQueen || piece2 == Board.WRook
                                    || piece2 == Board.BRook) && !this.isSameColor(piece1, piece2)) {
                                return true;
                            }
                            break;
                        }
                    }
                }
                // BISHOP OR QUEEN ATTACKS
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < Board.Bx[i].length; j++) {
                        int r2 = r + Board.Bx[i][j];
                        int c2 = c + Board.By[i][j];
                        if (!this.isOnBoard(r2, c2)) {
                            break;
                        }
                        int piece1 = this.board[c][r];
                        int piece2 = this.board[c2][r2];
                        if (!this.isEmpty(piece2)) {
                            if ((piece2 == Board.WQueen || piece2 == Board.BQueen || piece2 == Board.WBishop
                                    || piece2 == Board.BBishop) && !this.isSameColor(piece1, piece2)) {
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
                        if (piece2 == Board.BPawn) {
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
                        if (piece2 == Board.WPawn) {
                            return true;
                        }
                    }

                }
                // KING ATTACKS
                for (int i = 0; i < Board.Kx.length; i++) {
                    int r2 = r + Board.Kx[i];
                    int c2 = c + Board.Ky[i];
                    if (!this.isOnBoard(r2, c2)) {
                        continue;
                    }
                    int piece1 = this.board[c][r];
                    int piece2 = this.board[c2][r2];
                    if ((piece2 == Board.WKing || piece2 == Board.BKing) && !this.isSameColor(piece1, piece2)) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    /**
     * A method copies the current chess position and changes the turn. You need to
     * manually give the copy of the board its new position.
     * 
     * @return Board Return a copy of the current board that has different player's
     *         turn.
     */
    public Board getCloneAndChangeTurn() {
        int[][] newBoard = new int[Board.rows][Board.cols];
        for (int r = 0; r < Board.rows; r++) {
            for (int c = 0; c < Board.cols; c++) {
                newBoard[c][r] = this.board[c][r];
            }
        }
        return new Board(newBoard, !this.whitesMove);
    }

}
