package project;

import java.io.File;

import project.ChessPiece;
import project.ChessBoard;

public class Game {

    ChessBoard board;
    File f;
    final static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    
    public Game(String fname) {
        this.f = new File(fname);
        this.board = new ChessBoard();
    }

    public boolean backMove() {
        
    }

    public boolean forwardMove(int move) {
        board
    }

    public boolean Move(String move) {
        int x2 = alphabet.indexOf(move.charAt(-2))+1;
        int y2 = Character.getNumericValue(move.charAt(-1));

        for () {
            
        }




        board.Move(0, 0, x2, y2);
    }

    public void addNote(String txt) {

    }
}