package equipe3.vintagechess.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import equipe3.vintagechess.constant.BoardAttributes;
import equipe3.vintagechess.pieceModel.ChessPiece;
import equipe3.vintagechess.gameModel.Square;

public class SquareAdapter extends BaseAdapter {

    private Context context;
    private Square[][] board;

    public SquareAdapter(Context c, Square[][] board) {
        this.context = c;
        this.board = board;
    }


    @Override
    public int getCount() {
        return 64;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView v;
        if (convertView == null) {

            v = new ImageView(context);
            int size = parent.getWidth() / 8;

            v.setLayoutParams(new GridView.LayoutParams(size, size));



            //squaree colors
            int col = position / 8 % 2;
           switch (BoardAttributes.squareColor){
               case 1:
                   if (col == 0) {
                       if (position % 2 == 0)
                           v.setBackgroundColor(Color.parseColor("#D12374"));
                       else
                           v.setBackgroundColor(Color.parseColor("#6AEC26"));

                   } else {
                       if (position % 2 == 0)
                           v.setBackgroundColor(Color.parseColor("#6AEC26"));
                       else
                           v.setBackgroundColor(Color.parseColor("#D12374"));
                   }
                    break;

                case 2:
                    if (col == 0) {
                        if (position % 2 == 0)
                            v.setBackgroundColor(Color.parseColor("#FFAE74"));
                        else
                            v.setBackgroundColor(Color.parseColor("#6B499F"));

                    } else {
                        if (position % 2 == 0)
                            v.setBackgroundColor(Color.parseColor("#6B499F"));
                        else
                            v.setBackgroundColor(Color.parseColor("#FFAE74"));
                    }
                    break;

                default:
                    if (col == 0) {
                        if (position % 2 == 0)
                            v.setBackgroundColor(Color.parseColor("#DFAE74"));
                        else
                            v.setBackgroundColor(Color.parseColor("#6B4226"));

                    } else {
                        if (position % 2 == 0)
                            v.setBackgroundColor(Color.parseColor("#6B4226"));
                        else
                            v.setBackgroundColor(Color.parseColor("#DFAE74"));
                    }
                    break;
            }


            //load images
            ChessPiece p = board[position / 8][position % 8].getPiece();
            if (p != null) {
                switch (BoardAttributes.pieceStyle) {
                    case 1:
                        v.setImageResource(context.getResources().getIdentifier(p.toString()+"1", "drawable", context.getPackageName()));
                        break;
                    case 2:
                        v.setImageResource(context.getResources().getIdentifier(p.toString()+"1", "drawable", context.getPackageName()));
                        break;
                    default:
                        v.setImageResource(context.getResources().getIdentifier(p.toString(), "drawable", context.getPackageName()));
                        break;
                }
            }
        } else {
            v = (ImageView) convertView;
        }

        return v;
    }
}


