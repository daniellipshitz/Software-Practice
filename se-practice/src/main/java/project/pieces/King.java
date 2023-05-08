package project.pieces;
import project.ChessPiece;

public class King extends ChessPiece {

    private boolean hasMoved; //this will be checked inside isLegalMove

    public King(boolean isWhite){
<<<<<<< HEAD
        this.isWhite = isWhite;
        this.hasMoved = false;
=======
        this.isWhite=isWhite;
>>>>>>> cf5acdcad0b0a82b558eaecadf1516ae1b1fe28c
    }
	
    @Override
    public boolean isLegalMove(int x1, int y1, int x2, int y2){
    	
    }    
}