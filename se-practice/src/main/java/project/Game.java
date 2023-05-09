package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import project.ChessPiece;
import project.pieces.Bishop;
import project.pieces.King;
import project.pieces.Knight;
import project.pieces.Pawn;
import project.pieces.Queen;
import project.pieces.Rook;
import project.ChessBoard;

public class Game {
    List<String> moves;
    ChessBoard board;
    File f;
    final static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    
    public Game(String filename) throws IOException {
        this.board = new ChessBoard();
        this.moves = readPgnFile(filename);

        for (String move: moves) {
            move(move);
        }
    }

    private List<String> readPgnFile(String filename) throws IOException {
        List<String> moves = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
    
        while ((line = br.readLine()) != null) {
            if (line.startsWith("1.")) {
                sb.append(line);
            }
        }
    
        String[] tokens = sb.toString().split("\\s+");
        for (String token : tokens) {
            if (token.matches("\\d+\\.")) {
                continue; // ignore move numbers
            } else if (token.matches("[a-h][1-8]")) {
                moves.add("P" + token);
            } else if (token.matches("[KQRBN][a-h]?[1-8]?[x-]?[a-h][1-8]([+#])?")) {
                token = token.replace("x", "");
                if (token.endsWith("+") || token.endsWith("#")) {
                    token = token.substring(0, token.length() - 1);
                }
                moves.add(token);
            } else if (token.matches("O-O(-O)?")) {
                moves.add(token);
            }
        }
    
        return moves;
    }

    public boolean backMove() {
        
    }

    public boolean forwardMove(int move) {
        board
    }

    public boolean move(String move) {
        int x2 = alphabet.indexOf(move.charAt(-2))+1;
        int y2 = Character.getNumericValue(move.charAt(-1));

        Class<? extends ChessPiece> piece = getPieceClass(move.charAt(0));

        int[] sourcePosition = board.findPiece(x2, y2, !board.moveTree.getMove().isWhite(), piece);
        




        board.move(sourcePosition[0], sourcePosition[1], x2, y2);
    }

    public void addNote(String txt) {

    }

    private static Class<? extends ChessPiece> getPieceClass(Character piece) {
        switch (piece) {
            case 'K':
                return King.class;
            case 'Q':
                return Queen.class;
            case 'R':
                return Rook.class;
            case 'B':
                return Bishop.class;
            case 'N':
                return Knight.class;
            case 'P':
                return Pawn.class;
            default:
                throw new IllegalArgumentException("Invalid piece type: " + piece);
        }
    }
}