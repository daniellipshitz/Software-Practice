package project;

import project.ChessPiece;

public class ChessBoard {
    ChessPiece[][] board;

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
    
        if (putsKingInCheck(x1, y1, x2, y2, isWhiteTurn)) {
            return false;
        }
    

        return true;
    }

    public boolean isClear(int x1, int y1, int x2, int y2) {
        return true;
    }
}