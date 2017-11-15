package equipe3.vintagechess.pieceModel;

import equipe3.vintagechess.constant.PlayerColor;
import equipe3.vintagechess.gameModel.Player;
import equipe3.vintagechess.gameModel.Square;

public abstract class ChessPiece {

    private Square[][] board;

    private Square location;

    private Player player;

    private int moves;

    public abstract String getInitial();

    public abstract String getPieceName();

    public Square getLocation() {
        return location;
    }

    public void setLocation(Square location) {
        this.location = location;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Square[][] getBoard() {
        return board;
    }

    public void incrementMoves() {
        moves++;
    }

    public void decrementMoves() {
        moves--;
    }

    public int numberOfMoves() {
        return moves;
    }

    public void setNumberOfMoves(int moves) {
        this.moves = moves;
    }

    public void setBoard(Square[][] board) {
        this.board = board;
    }

    public String colorString() {

        return getPlayer().getColor() == PlayerColor.WHITE ? "white" : "black";
    }

    public String toString() {
        return colorString() + getPieceName();
    }

}


