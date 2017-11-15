package equipe3.vintagechess.gameModel;

import java.io.Serializable;

import equipe3.vintagechess.pieceModel.ChessPiece;


public class Square implements Serializable {


    private int x;    //Rank is row, x value
    private int y;    //File is column, y value

    private static final long serialVersionUID = 1L;

    private ChessPiece piece; //this chess piece is occupying the current position (square)

    private int getX() {
        return x;
    }

    private int getY() {
        return y;
    }

    public Square(int r, int c) {
        this.y = r;
        this.x = c;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Square)) return false;

        return this.getX() == ((Square) o).getX() && this.getY() == ((Square) o).getY();

    }

    public int getPosition(){
        return (x*8 + y);
    }
}
