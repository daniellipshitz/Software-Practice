package project.pieces;
import project.ChessPiece;

package pieces;
public class Rook extends ChessPiece {

    private boolean hasMoved;

    public Rook(boolean isWhite){
        this.isWhite=isWhite;
    }

    @Override
    public boolean isLegalMove(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
            return true;
        }
        return false;
    }
}