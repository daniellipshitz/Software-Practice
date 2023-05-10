package project;

import java.util.ArrayList;

import project.ChessPiece;
import project.pieces.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Image;

public class ChessBoard {
    ChessPiece[][] board;
    MoveTree moveTree;
    JFrame frame;

    public ChessBoard() {
        this.board = new ChessPiece[8][8];
        this.moveTree = new MoveTree();
        this.frame = new JFrame();
        initializeBoard();
    }

    private void initializeBoard() {
        board[0][0] = new Rook(false, new int[]{0, 0});
        board[0][1] = new Knight(false, new int[]{0, 1});
        board[0][2] = new Bishop(false, new int[]{0, 2});
        board[0][3] = new Queen(false, new int[]{0, 3});
        board[0][4] = new King(false, new int[]{0, 4});
        board[0][5] = new Bishop(false, new int[]{0, 5});
        board[0][6] = new Knight(false, new int[]{0, 6});
        board[0][7] = new Rook(false, new int[]{0, 7});

        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(false, new int[]{1, i});
            board[6][i] = new Pawn(true, new int[]{6, i});
        }
        
        board[7][0] = new Rook(true, new int[]{7, 0});
        board[7][1] = new Knight(true, new int[]{7, 1});
        board[7][2] = new Bishop(true, new int[]{7, 2});
        board[7][3] = new Queen(true, new int[]{7, 3});
        board[7][4] = new King(true, new int[]{7, 4});
        board[7][5] = new Bishop(true, new int[]{7, 5});
        board[7][6] = new Knight(true, new int[]{7, 6});
        board[7][7] = new Rook(true, new int[]{7, 7});
    }

    public boolean move(int x1, int y1, int x2, int y2) {
        if (!isLegalMove(x1, y1, x2, y2, moveTree.getMove().isWhite())) {
            return false;
        }

        createMove(x1, y1, x2, y2, (!moveTree.getMove().isWhite()));
        return true;
    }

    public void drawBoard(){
        frame.setDefaultCloseOperation(3);
        int horizontalBound = 512;
        int verticalBound = 512;
        frame.setBounds(0,0,horizontalBound,verticalBound);

        JPanel jp = new JPanel(){
            @Override
            public void paint(Graphics g){
                boolean white = false;
                for(int i=0;i<horizontalBound;i+=64){
                    white=!white;
                    for(int j=0;j<verticalBound;j+=64){
                        if(white){
                            g.setColor(Color.WHITE.darker());
                        }
                        else{
                            g.setColor(Color.BLACK.brighter());
                        }
                        g.fillRect(i,j,64,64);
                        drawPiece(g, i/64, j/64);
                        white=!white;
                    }
                }
                //g.drawImage(img2, 0, 0, this);
            }
        };

        frame.add(jp);
        frame.setVisible(true);
    }

    private void drawPiece(Graphics g, int xp, int yp){
        if(this.board[xp][yp]==null){
            return;
        }
        else{
            g.drawImage(this.board[xp][yp].getImage(), xp, yp, this);
        }
    }

    public boolean isPossibleMove(int x1, int y1, int x2, int y2, boolean isWhiteTurn) {
        ChessPiece piece = this.board[x1][y1];
        if (piece == null || piece.isWhite() != isWhiteTurn) {
            return false;
        }
    
        if (!piece.isLegalMove(x1, y1, x2, y2)) {
            return false;
        }
    
        if (!(piece.getClass() == Knight.class) && !isClear(x1, y1, x2, y2)) {
            return false;
        }
    
        if (isCapture(x1, y1, x2, y2)) {
            if (this.board[x2][y2].isWhite() == isWhiteTurn) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<int[]> findPiece(int x2, int y2, boolean isWhiteTurn, Class<? extends ChessPiece> piece) {
        ArrayList<ChessPiece> pieces = getPiecesOfColor(isWhiteTurn);
        ArrayList<int[]> positions = new ArrayList<>();

        for (ChessPiece currentPiece: pieces) {
            if (!(currentPiece.getClass() == piece) || !isPossibleMove(currentPiece.getPosition()[0], currentPiece.getPosition()[1], x2, y2, isWhiteTurn)) {
                continue;
            }
            positions.add(currentPiece.getPosition());
        }

        return positions;
    }

    private ArrayList<ChessPiece> getPiecesOfColor(boolean isWhiteTurn) {
        ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();

        for (int i=0; i < this.board.length; i++) {
            for (int j=0; i < this.board[i].length; j++) {
                if (this.board[i][j] != null && this.board[i][j].isWhite() == isWhiteTurn) {
                    pieces.add(this.board[i][j]);
                }
            }
        }

        return pieces;
    }

    public boolean isLegalMove(int x1, int y1, int x2, int y2, boolean isWhiteTurn) {
        if (!isPossibleMove(x1, y1, x2, y2, isWhiteTurn)) {
            return false;
        }
        
        if (kingInCheck(this.board, getEnemyPieces(this.board, isWhiteTurn), isWhiteTurn) && putsKingInCheck(x1, y1, x2, y2, isWhiteTurn)) {
            return false;
        }

        //if (putsKingInCheck(x1, y1, x2, y2, isWhiteTurn)) {
        //    if (isWhiteTurn == piece.isWhite())
        //    return false;
        //}
    
        return true;
    }

    public boolean isClear(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;

        int x = x1;
        int y = y1;

        for (int i = 0; i < Math.max(dx, dy); i++) {
            if (x == x2 && y == y2) {
                return true;
            }
            if (board[x][y] != null) {
                return false;
            }
            x += sx;
            y += sy;
        }

        return true;
    }

    public void createMove (int x1, int y1, int x2, int y2, boolean isWhiteTurn) {
        ChessPiece sourcePiece = board[x1][y1];
        ChessPiece destinationPiece = board[x2][y2];

        Runnable doMove = () -> {
            board[x1][y1] = null;
            board[x2][y2] = sourcePiece;
            board[x2][y2].setPosition(x2, y2);
        };
        Runnable undoMove = () -> {
            board[x1][y1] = sourcePiece;
            board[x1][y1].setPosition(x1, y1);
            board[x2][y2] = destinationPiece;
            board[x2][y2].setPosition(x2, y2);
        };

        int moveNumber = moveTree.getMove() == null? 0: moveTree.getMove().getMoveNumber();
        moveNumber += ((isWhiteTurn) ? 1 : 0);
        Move move = new Move(doMove, undoMove, isWhiteTurn, moveNumber, x1, y1, x2, y2);
        moveTree.addMove(move);

        moveTree.getMove().doMove();
    }

    private boolean isCapture(int x1, int y1, int x2, int y2) {
        if (board[x2][y2] != null) {
            return true;
        }
        return false;
    }

    private boolean putsKingInCheck(int x1, int y1, int x2, int y2, boolean isWhiteTurn) {
        ChessPiece alternateBoard[][] = new ChessPiece[8][8];

        for (int i=0; i < this.board.length; i++) {
            for (int j=0; i < this.board[i].length; j++) {
                alternateBoard[i][j] = this.board[i][j];
            }
        }

        ArrayList<ChessPiece> enemyPieces = getEnemyPieces(alternateBoard, isWhiteTurn);

        if (kingInCheck(alternateBoard, enemyPieces, isWhiteTurn)) {
            return true;
        }

        return false;
    }

    private ArrayList<ChessPiece> getEnemyPieces(ChessPiece[][] board, boolean isWhiteTurn) {
        ArrayList<ChessPiece> enemyPieces = new ArrayList<ChessPiece>();

        for (int i=0; i < this.board.length; i++) {
            for (int j=0; i < this.board[i].length; j++) {
                if (this.board[i][j] != null && this.board[i][j].isWhite() != isWhiteTurn) {
                    enemyPieces.add(this.board[i][j]);
                }
            }
        }

        return enemyPieces;
    }

    private boolean kingInCheck(ChessPiece[][] board, ArrayList<ChessPiece> enemyPieces, boolean isWhiteTurn) {
        ArrayList<ChessPiece> pieces = getPiecesOfColor(isWhiteTurn);

        ChessPiece king = null;

        for (ChessPiece piece: pieces) {
            if (piece.getClass() == King.class) king = piece;
        }
        
        for (ChessPiece piece: enemyPieces) {
            if (isPossibleMove(piece.getPosition()[0], piece.getPosition()[1], king.getPosition()[0], king.getPosition()[1], isWhiteTurn)) {
                return true;
            }
        }

        return false;
    }
}