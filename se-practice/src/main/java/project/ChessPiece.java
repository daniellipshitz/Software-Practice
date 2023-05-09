package project;

public abstract class ChessPiece {
    protected boolean isWhite;
    protected int[] position;

    public ChessPiece(boolean isWhite, int[] position){
        this.isWhite = isWhite;
        this.position = position;
    }
    
    public boolean isWhite() {
        return this.isWhite;
    }

    public abstract boolean isLegalMove(int x1, int y1, int x2, int y2);

    public void setPosition(int x, int y) {
        this.position[0] = x;
        this.position[1] = y;
    }

    public int[] getPosition() {
        return this.position;
    }
}