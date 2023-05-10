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
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            boolean startReadingMoves = false;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("1.")) {
                    sb.append(line);
                }
                else if (startReadingMoves) {
                    sb.append(line);
                }
            }
        }
        
        String[] tokens = sb.toString().split("\\s+");
        boolean ignore = false;
        for (String token : tokens) {
            if (ignore) {
                if (token.endsWith("}")) {
                    ignore = false;
                }
                continue;
            }
            else if (token.startsWith("{")) {
                ignore = true;
                continue;
            }
            else if (token.matches("\\d+\\.")) { //move number
                continue;
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

    public void backMove() {
        board.moveTree.traverseUp(1);

        //reset graphics
    }

    public void forwardMove(int move) {
        board.moveTree.traverseDown(move);

        //reset graphics
    }

    public boolean move(String move) {
        int x2 = alphabet.indexOf(move.charAt(move.length() -2))+1;
        int y2 = Character.getNumericValue(move.charAt(move.length() -1));

        Class<? extends ChessPiece> piece = getPieceClass(move.charAt(0));

        ArrayList<int[]> sourcePosition = board.findPiece(x2, y2, !board.moveTree.getMove().isWhite(), piece);

        int x1;
        int y1;

        if (sourcePosition.size() > 1) {
            Character c = move.charAt(1);
            int pos = Character.getNumericValue(c);
            int[] position;
            if (pos > 0) {
                position = findPosition(sourcePosition, pos, 1);
            }
            else {
                position = findPosition(sourcePosition, alphabet.indexOf(c), 0);
            }
            x1 = position[0];
            y1 = position[1];
        }
        else {
            x1 = sourcePosition.get(0)[0];
            y1 = sourcePosition.get(0)[1];
        }

        return board.move(x1, y1, x2, y2);

        //reset graphics
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

    private int [] findPosition(ArrayList<int[]> sourcePosition, int coordinate, int index) {
        for (int[] position: sourcePosition) {
            if (position[index] == coordinate) {
                return position;
            }
        }
        return sourcePosition.get(0);
    }
}