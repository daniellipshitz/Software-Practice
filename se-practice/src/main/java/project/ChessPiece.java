package project;

public abstract class ChessPiece {
    private boolean color;
    
    public boolean isWhite() {
        return this.color;
    }

    public abstract boolean isLegalMove(int x1, int y1, int x2, int y2);
}