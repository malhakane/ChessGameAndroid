package equipe3.vintagechess.gameModel;

/**
 * Created by Ablam on 13/10/2016.
 */

public class Board {
    private int turn, move_no;
    private String king1, queen1, bishop1A, bishop1B, rook1A, rook1B, Knight1A, Knight1B,
            pawn1;
    private String king2, queen2, bishop2A, bishop2B, rook2A, rook2B, Knight2A, Knight2B,
            pawn2;

    public Board(int turn,
                 int move_no,
                 String king1,
                 String queen1,
                 String bishop1A,
                 String bishop1B,
                 String rook1A,
                 String rook1B,
                 String knight1A,
                 String knight1B,
                 String pawn1,
                 String king2,
                 String queen2,
                 String bishop2A,
                 String bishop2B,
                 String rook2A,
                 String rook2B,
                 String knight2A,
                 String knight2B,
                 String pawn2) {
        this.turn = turn;
        this.move_no = move_no;
        this.king1 = king1;
        this.queen1 = queen1;
        this.bishop1A = bishop1A;
        this.bishop1B = bishop1B;
        this.rook1A = rook1A;
        this.rook1B = rook1B;
        this.Knight1A = knight1A;
        this.Knight1B = knight1B;
        this.pawn1 = pawn1;
        this.king2 = king2;
        this.queen2 = queen2;
        this.bishop2A = bishop2A;
        this.bishop2B = bishop2B;
        this.rook2A = rook2A;
        this.rook2B = rook2B;
        this.Knight2A = knight2A;
        this.Knight2B = knight2B;
        this.pawn2 = pawn2;

    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getMove_no() {
        return move_no;
    }

    public void setMove_no(int move_no) {
        this.move_no = move_no;
    }

    public String getKing1() {
        return king1;
    }

    public void setKing1(String king1) {
        this.king1 = king1;
    }

    public String getQueen1() {
        return queen1;
    }

    public void setQueen1(String queen1) {
        this.queen1 = queen1;
    }

    public String getBishop1A() {
        return bishop1A;
    }

    public void setBishop1A(String bishop1A) {
        this.bishop1A = bishop1A;
    }

    public String getBishop1B() {
        return bishop1B;
    }

    public void setBishop1B(String bishop1B) {
        this.bishop1B = bishop1B;
    }

    public String getRook1A() {
        return rook1A;
    }

    public void setRook1A(String rook1A) {
        this.rook1A = rook1A;
    }

    public String getRook1B() {
        return rook1B;
    }

    public void setRook1B(String rook1B) {
        this.rook1B = rook1B;
    }

    public String getKnight1A() {
        return Knight1A;
    }

    public void setKnight1A(String knight1A) {
        Knight1A = knight1A;
    }

    public String getKnight1B() {
        return Knight1B;
    }

    public void setKnight1B(String knight1B) {
        Knight1B = knight1B;
    }

    public String getPawn1() {
        return pawn1;
    }

    public void setPawn1(String pawn1) {
        this.pawn1 = pawn1;
    }



    public String getKing2() {
        return king2;
    }

    public void setKing2(String king2) {
        this.king2 = king2;
    }

    public String getQueen2() {
        return queen2;
    }

    public void setQueen2(String queen2) {
        this.queen2 = queen2;
    }

    public String getBishop2A() {
        return bishop2A;
    }

    public void setBishop2A(String bishop2A) {
        this.bishop2A = bishop2A;
    }

    public String getBishop2B() {
        return bishop2B;
    }

    public void setBishop2B(String bishop2B) {
        this.bishop2B = bishop2B;
    }

    public String getRook2A() {
        return rook2A;
    }

    public void setRook2A(String rook2A) {
        this.rook2A = rook2A;
    }

    public String getRook2B() {
        return rook2B;
    }

    public void setRook2B(String rook2B) {
        this.rook2B = rook2B;
    }

    public String getKnight2A() {
        return Knight2A;
    }

    public void setKnight2A(String knight2A) {
        Knight2A = knight2A;
    }

    public String getKnight2B() {
        return Knight2B;
    }

    public void setKnight2B(String knight2B) {
        Knight2B = knight2B;
    }

    public String getPawn2() {
        return pawn2;
    }

    public void setPawn2(String pawn21) {
        this.pawn2 = pawn2;
    }

}
