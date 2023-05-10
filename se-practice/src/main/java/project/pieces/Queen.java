package project.pieces;
import project.ChessPiece;

public class Queen extends ChessPiece {

    public Queen(boolean isWhite, int[] position){
        super(isWhite, position);
        String temp = getColorPath("queen", this.isWhite);
        this.img=pathToScaledImage(temp);
    }
	
    @Override
    public boolean isLegalMove(int x1, int y1, int x2, int y2){
    	
    }	
}