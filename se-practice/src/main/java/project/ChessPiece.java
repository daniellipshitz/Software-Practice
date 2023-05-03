package project;

public abstract class ChessPiece{
    protected boolean isWhite;
    
    public boolean isWhite() {
        return this.isWhite;
    }

    public abstract boolean isLegalMove(int x1, int y1, int x2, int y2);
}