package equipe3.vintagechess.controller;

import equipe3.vintagechess.pieceModel.Bishop;
import equipe3.vintagechess.pieceModel.ChessPiece;
import equipe3.vintagechess.pieceModel.King;
import equipe3.vintagechess.pieceModel.Knight;
import equipe3.vintagechess.pieceModel.Pawn;
import equipe3.vintagechess.gameModel.Player;
import equipe3.vintagechess.constant.PlayerColor;
import equipe3.vintagechess.pieceModel.Queen;
import equipe3.vintagechess.pieceModel.Rook;
import equipe3.vintagechess.gameModel.Square;

public class Game {

    private Square[][] board;
    private ChessPiece[] capturedWhite;
    private ChessPiece[] capturedBlack;
    private int capturedWhiteCount;
    private int capturedBlackCount;
    private Player black;
    private Player white;
    private Player turn;

    public Game() {
        board = new Square[8][8];
        black = new Player(PlayerColor.BLACK);
        white = new Player(PlayerColor.WHITE);
        capturedWhite = new ChessPiece[16];
        capturedBlack = new ChessPiece[16];
        capturedWhiteCount = 0;
        capturedBlackCount = 0;
        turn = white;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Square(i, j);
            }
        }
        placePieces(black);
        placePieces(white);
    }

    private void placePieces(Player player) {

        int row = player.getColor() == PlayerColor.WHITE ? 7 : 0;

        ChessPiece[] pieces = new ChessPiece[]
                {new Rook(), new Knight(), new Bishop(), new Queen(), new King(), new Bishop(), new Knight(), new Rook()};


        player.setKing((King) pieces[4]);

        for (int i = 0; i < 8; i++) {
            pieces[i].setLocation(board[row][i]);
            pieces[i].setBoard(board);
            board[row][i].setPiece(pieces[i]);
            board[row][i].getPiece().setPlayer(player);
            player.addPiece(pieces[i]);
            player.addCapturedPiece(pieces[i]);

        }

        row = player.getColor() == PlayerColor.WHITE ? 6 : 1;

        for (int i = 0; i < 8; i++) {
            ChessPiece pawn = new Pawn();
            pawn.setLocation(board[row][i]);
            pawn.setBoard(board);
            board[row][i].setPiece(pawn);
            board[row][i].getPiece().setPlayer(player);
            player.addPiece(pawn);
            player.addCapturedPiece(pawn);
        }
    }

    public Square[][] getBoard() {
        return board;
    }

   /* public ChessPiece[] getCapturedWhite() {
        return capturedWhite;
    }
    public ChessPiece[] getCapturedBlack() {
        return capturedBlack;
    }*/

    public Player getCurrentPlayer() {
        return turn;
    }

    //Move from 0-63
    //Move from 0-63
    public boolean move(int s, int d) {

        //blackInCheck = false;
        //whiteInCheck = false;

        if (s > 63 || s < 0 || d > 63 || d < 0) return false;

        //android position to (r,c) translation
        int c = s % 8;
        int r = s / 8;
        Square source = board[r][c];

        c = d % 8;
        r = d / 8;
        Square dest = board[r][c];

        ChessPiece sourcePiece = source.getPiece();
        ChessPiece destPiece = dest.getPiece();
        //ChessPiece capture = null;
        //Move.MoveType moveType = Move.MoveType.NORMAL;

        if (sourcePiece == null) return false;

        //if(sourcePiece== destPiece) return false;

        //attempting to move opponent's piece
        if (sourcePiece.getPlayer().getColor() != getCurrentPlayer().getColor()) return false;

        //normal capture
        if (destPiece != null) {

            destPiece.setLocation(null);

            //Ajouter aux pieces capturees
            if (turn == white) {
                capturedBlack[capturedBlackCount++] = destPiece;
            } else {
                capturedWhite[capturedWhiteCount++] = destPiece;
            }

            destPiece.getPlayer().removePiece(destPiece);
            //capture = destPiece;

        }

        //Move move = new Move(sourcePiece, capture, source, dest);

        source.setPiece(null);
        sourcePiece.setLocation(dest);
        dest.setPiece(sourcePiece);
        sourcePiece.incrementMoves();

        //move.setType(moveType);
        //move.setSourcePosition(s);
        //move.setDestPosition(d);
        //moves.add(move);

        return true;
    }

    public void setTurn() {
        turn = (turn == white) ? black : white;
    }

    public int playerId() {
        if (turn == white) return 1;
        return 2;
    }

  /*  public String whiteCapturedToString(){
        String pieces = "";
        for(int i=0;i<capturedWhite.length;i++){
            if(capturedWhite[i]!=null) {
                pieces += capturedWhite[i] + "  ";
            }
        }
        return pieces;
    }
    public String blackCapturedToString(){
        String pieces = "";
        for(int i=0;i<capturedBlack.length;i++){
            if(capturedBlack[i]!=null) {
                pieces += capturedBlack[i] + "  ";
            }
        }
        return pieces;
    }*/




}
