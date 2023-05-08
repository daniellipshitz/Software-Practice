package project;

<<<<<<< HEAD
public abstract class ChessPiece {
    protected boolean isWhite;
=======
public abstract class ChessPiece{
    protected boolean isWhite;
    //protected graphics image;
>>>>>>> cf5acdcad0b0a82b558eaecadf1516ae1b1fe28c
    
    public boolean isWhite() {
        return this.isWhite;
    }

    public abstract boolean isLegalMove(int x1, int y1, int x2, int y2);
}