package project.pieces;
import project.ChessPiece;

public class King extends ChessPiece {

    private boolean hasMoved; //this will be checked inside isLegalMove

    public King(boolean isWhite, int[] position){
        super(isWhite, position);
        this.hasMoved = false;
    }
	
    @Override
    public boolean isLegalMove(int x1, int y1, int x2, int y2){
    	int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        if ((dx>0 && dy <= 1) || (dx <= 1 && dy>0)) {
            return true;
        }
        return false;
    }    
}