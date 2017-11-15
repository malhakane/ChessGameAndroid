package equipe3.vintagechess.gameModel;

import java.util.ArrayList;

import equipe3.vintagechess.constant.PlayerColor;
import equipe3.vintagechess.pieceModel.ChessPiece;
import equipe3.vintagechess.pieceModel.King;

public class Player {

    private ArrayList<ChessPiece> pieces;

    private ArrayList<ChessPiece> capturedPieces;

    //private Player opponent;

    private King king;

    private PlayerColor color;

    public Player(PlayerColor color) {
        this.color = color;
        pieces = new ArrayList<ChessPiece>(16);
        capturedPieces = new ArrayList<ChessPiece>(16);
    }

    public PlayerColor getColor() {
        return color;
    }
/*
    public Player getOpponent() {
        return opponent;
    }
*/
    public void addPiece(ChessPiece piece) {
        pieces.add(piece);
    }

    public void addCapturedPiece(ChessPiece piece) {
        capturedPieces.add(piece);
    }

    public void removePiece(ChessPiece piece) {
        capturedPieces.remove(piece);
    }

    public ArrayList<ChessPiece> getPieces() {
        return pieces;
    }

    public ArrayList<ChessPiece> getcapturedPieces() {
        return capturedPieces;
    }

/*
    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
*/
    public void setKing(King king) {
        this.king = king;
    }

    public King getKing() {
        return king;
    }

    public int getPlayerColor() {
        if (color == PlayerColor.BLACK)
            return 2;
        return 1;
    }

}
