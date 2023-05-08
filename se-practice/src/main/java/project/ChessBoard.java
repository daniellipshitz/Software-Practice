package project;

import java.util.ArrayList;

import project.ChessPiece;

public class ChessBoard {
    ChessPiece[][] board;
    MoveTree moveTree;

    public ChessBoard() {
        this.board = new ChessPiece[8][8];
        this.moveTree = new MoveTree();
    }

    public boolean Move(int x1, int y1, int x2, int y2) {
        if (!isLegalMove(x1, y1, x2, y2, moveTree.getMove().isWhite())) {
            return false;
        }

        createMove(x1, y1, x2, y2, (!moveTree.getMove().isWhite()));
        return true;
    }

    public boolean isPossibleMove(int x1, int y1, int x2, int y2) {
        return true;
    }//finsih!!!

    public int[] findPiece(int x2, int y2, boolean isWhiteTurn, ChessPiece piece) {
        ArrayList<ChessPiece> ChessPiece = findPiecesOfColor(isWhiteTurn, piece);

        for (ChessPiece piece: pieces) {

        }
    }

    private ArrayList<ChessPiece> findPiecesOfColor(boolean isWhiteTurn) {
        ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();

        for (int i=0; i < this.board.length; i++) {
            for (int j=0; i < this.board[i].length; j++) {
                if (this.board[i][j] != null && this.board[i][j].isWhite() == isWhiteTurn) {
                    pieces.add(this.board[i][j]);
                }
            }
        }
    }

    public boolean isLegalMove(int x1, int y1, int x2, int y2, boolean isWhiteTurn) {
        ChessPiece piece = this.board[x1][y1];
        if (piece == null || piece.isWhite() != isWhiteTurn) {
            return false;
        }
    
        if (!piece.isLegalMove(x1, y1, x2, y2)) {
            return false;
        }
    
        if (!isClear(x1, y1, x2, y2)) {
            return false;
        }
    
        if (isCapture(x1, y1, x2, y2)) {
            if (this.board[x2][y2].isWhite() == isWhiteTurn) {
                return false;
            }
        }
        
        if (kingInCheck(this.board, isWhiteTurn)) {

        }

        if (putsKingInCheck(x1, y1, x2, y2, isWhiteTurn)) {
            if (isWhiteTurn == piece.isWhite())
            return false;
        }
    

        return true;
    }

    public boolean isClear(int x1, int y1, int x2, int y2) {
        return true;
    }

    public void createMove (int x1, int y1, int x2, int y2, boolean isWhiteTurn) {
        ChessPiece sourcePiece = board[x1][y1];
        ChessPiece destinationPiece = board[x2][y2];
        boolean isWhite = sourcePiece.isWhite();

        Runnable doMove = () -> {
            board[x1][y1] = null;
            board[x2][y2] = sourcePiece;
        };
        Runnable undoMove = () -> {
            board[x1][y1] = sourcePiece;
            board[x2][y2] = destinationPiece;
        };

        Move move = new Move(doMove, undoMove, isWhiteTurn, x1, y1, x2, y2);
        moveTree.addMove(move);

        moveTree.getMove().doMove();
    }

    private boolean isCapture(int x1, int y1, int x2, int y2) {
        if (board[x2][y2] != null) {
            return true;
        }
        return false;
    }

    private boolean putsKingInCheck(int x1, int y1, int x2, int y2, boolean isWhiteTurn) {
        ChessPiece alternateBoard[][] = new ChessPiece[8][8];
        ArrayList<ChessPiece> enemyPieces = new ArrayList<ChessPiece>();

        for (int i=0; i < this.board.length; i++) {
            for (int j=0; i < this.board[i].length; j++) {
                alternateBoard[i][j] = this.board[i][j];

                if (this.board[i][j] != null && this.board[i][j].isWhite() != isWhiteTurn) {
                    enemyPieces.add(this.board[i][j]);
                }
            }
        }

        if (kingInCheck(alternateBoard, isWhiteTurn)) {
            return false;
        }
    }

    private boolean kingInCheck(ChessPiece[][] board, ArrayList<ChessPiece> enemyPieces, boolean isWhiteTurn) {
        for (ChessPiece enemy: enemyPieces) {
            if (isLegalMove()) {
                
            }
        }
    }
}