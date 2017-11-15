package equipe3.vintagechess.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import equipe3.vintagechess.constant.PlayerColor;
import equipe3.vintagechess.gameModel.Board;
import com.CS213.androidchess101.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import equipe3.vintagechess.constant.ChessConstants;
import equipe3.vintagechess.request.QueueSingleton;
import equipe3.vintagechess.controller.Game;
import equipe3.vintagechess.gameModel.Square;
import equipe3.vintagechess.adapter.SquareAdapter;

import static equipe3.vintagechess.activity.MainMenuActivity.codeAuth;


public class ChessActivity extends AppCompatActivity implements OnItemClickListener {

    private static Game game = new Game();
    private TextView turnView;
    private GridView chessboard;
    private View[] squaresSelected;
    private int[] squarePositions;
    private SquareAdapter adapter;
    private boolean promotion = false;
    final public static String baseUrl = "http://132.207.89.37/";
    final public static String urlMove = baseUrl + "move/";
    final public static String urlNewGame = baseUrl + "new_game";
    final public static String urlGameStart = baseUrl + "game_start";
    final public static String urlGameEnd = baseUrl + "game_end";
    private static String urlGameStatus = "http://132.207.89.37/status/board";
    private String urlPromote =baseUrl+"promote/";
    private boolean promoted = true;
    private static RequestQueue requestQueue;
    private Handler mHandler;
    private static String promotedPeice =null;

    Button gameEnd;
    Button gameSettings;
    //static boolean runOnce = true;
    private int turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess_activity);

        squaresSelected = new View[2];
        squarePositions = new int[2];
        mHandler = new Handler();

        adapter = new SquareAdapter(this, game.getBoard());
        turnView = (TextView) findViewById(R.id.turnView);

        final GridView chessBoardGridView = (GridView) findViewById(R.id.chessboard);
        chessBoardGridView.setAdapter(adapter);
        chessBoardGridView.setOnItemClickListener(this);

       /*if(runOnce){
            Intent svc = new Intent(this, MusicService.class);
            startService(svc);
            runOnce = false;
        }*/

        this.chessboard = chessBoardGridView;
        //AsyncHttpClient client = new AsyncHttpClient();
        requestQueue = QueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        //client.setConnectTimeout(30000);

        gameEnd = (Button) findViewById(R.id.gameEnd);
        gameEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlGameEnd, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Intent intent = new Intent(ChessActivity.this, ChessActivity.class);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "You can't end the game now", Toast.LENGTH_LONG).show();
                            }
                        });
                requestQueue.add(JsonObjectRequest);
            }
        });

        gameSettings = (Button) findViewById(R.id.gameSettings);
        gameSettings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent intent = new Intent(ChessActivity.this, SettingsMenuActivity.class);
                startActivity(intent);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true) {
                    try {
                        Thread.sleep(8000);
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                // Write your code here to update the UI.
                                JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlGameStatus, null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Log.i("Status board:",response.toString());
                                                upDate(getBoardStatusServer(response),getBoardStatusLocal(game));
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getApplicationContext(), "Error in getting game status", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                requestQueue.add(JsonObjectRequest);
                            }
                        });
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.chess, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case (R.id.action_settings):
				return true;*/
            case (android.R.id.home):
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Quit game?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(ChessActivity.this, HomeActivity.class));
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position,
                            long id) {

        //selecting a piece to move.
        if (squaresSelected[0] == null) {

            Square selectedSquare = game.getBoard()[position / 8][position % 8];

            if (selectedSquare.getPiece() == null) return;

            if (selectedSquare.getPiece().getPlayer().getColor() != game.getCurrentPlayer().getColor())
                return;

            squaresSelected[0] = view;
            squarePositions[0] = position;

            Log.i("Piece Name",selectedSquare.getPiece().getPieceName());

            view.setBackgroundColor(Color.BLUE);

        } else {

            squaresSelected[1] = view;
            squarePositions[1] = position;

            String url  =  urlMove +
                           game.playerId() + "/" +
                           ChessConstants.positions[squarePositions[1]] + "-" +
                           ChessConstants.positions[squarePositions[0]] + "/" +
                           codeAuth;
            JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        if(response.getBoolean("promotion") == Boolean.TRUE){
                            promotedOp();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }



                    if (game.move(squarePositions[0], squarePositions[1])) {

                        adapter.notifyDataSetChanged();
                        chessboard.setAdapter(adapter);
                        changeTurnText();
                        turn = game.playerId();
                        game.setTurn();
                    }

                    if(squaresSelected[0] != null){
                        squaresSelected[0].setBackgroundColor(updateColor(squarePositions[0]));
                    }
                    squaresSelected[0] = null;
                    squaresSelected[1] = null;

                    //On remet promotion a faux
                    //promotion = false;
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Illegal Move", Toast.LENGTH_LONG).show();
                            if(squaresSelected[0] != null){
                                squaresSelected[0].setBackgroundColor(updateColor(squarePositions[0]));
                            }
                            squaresSelected[0] = null;
                            squaresSelected[1] = null;

                            VolleyLog.e("Error", error.toString());
                        }
                    });
            /*JsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    3000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/

            requestQueue.add(JsonObjectRequest);
        }
    }

    private void changeTurnText() {
        if (turnView.getText().toString().compareTo(getResources().getString(R.string.white_turn)) == 0) {
            turnView.setText(getResources().getString(R.string.black_turn));
        } else {
            turnView.setText(getResources().getString(R.string.white_turn));
        }
    }

    private int updateColor(int position) {
        int col = position / 8 % 2;
        if (col == 0) {
            if (position % 2 == 0)
                return Color.parseColor("#DFAE74");
            else
                return Color.parseColor("#6B4226");
        } else {
            if (position % 2 == 0)
                return Color.parseColor("#6B4226");
            else
                return Color.parseColor("#DFAE74");
        }
    }


    public Map<String,Integer> getBoardStatusServer(JSONObject jsonObject){
        Map<String,Integer> piecesServer = new LinkedHashMap<String, Integer>(32);
        try {
            piecesServer.put("king1", getPostionConstant(jsonObject.getString("king1")));
            piecesServer.put("queen1", getPostionConstant(jsonObject.getString("queen1")));
            piecesServer.put("bishop1A",getPostionConstant(jsonObject.getString("bishop1A")));
            piecesServer.put("bishop1B",getPostionConstant(jsonObject.getString("bishop1B")));

            piecesServer.put("rook1A", getPostionConstant(jsonObject.getString("rook1A")));
            piecesServer.put("rook1B", getPostionConstant(jsonObject.getString("rook1B")));
            piecesServer.put("knight1A",getPostionConstant(jsonObject.getString("knight1A")));
            piecesServer.put("knight1B",getPostionConstant(jsonObject.getString("knight1B")));

            for(int i =0; i< 8;i++) {
                piecesServer.put("pawnW"+i,getPostionConstant(jsonObject.getString("pawn1").split("")[1+i]));
            }

            piecesServer.put("king2", getPostionConstant(jsonObject.getString("king2")));
            piecesServer.put("queen2", getPostionConstant(jsonObject.getString("queen2")));
            piecesServer.put("bishop2A",getPostionConstant(jsonObject.getString("bishop2A")));
            piecesServer.put("bishop2B",getPostionConstant(jsonObject.getString("bishop2B")));

            piecesServer.put("rook2A", getPostionConstant(jsonObject.getString("rook2A")));
            piecesServer.put("rook2B", getPostionConstant(jsonObject.getString("rook2B")));
            piecesServer.put("knight2A",getPostionConstant(jsonObject.getString("knight2A")));
            piecesServer.put("knight2B",getPostionConstant(jsonObject.getString("knight2B")));

            for(int i =0; i< 8;i++) {
                piecesServer.put("pawnB"+i,getPostionConstant(jsonObject.getString("pawn2").split("")[1+i]));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return piecesServer;
    }

    public Map<String,Integer> getBoardStatusLocal(Game game) {
        Map<String,Integer> piecesLocal = new HashMap<>(32);
        int pawnB = 0;
        int pawnW = 0;
        boolean rookB=true;
        boolean rookW=true;
        boolean bishopW = true;
        boolean bishopB = true;
        boolean knightB = true;
        boolean knightW = true;
        for(int i = 0;i < 8;i++){
            for(int j =0;j< 8;j++) {
                if(game.getBoard()[i][j].getPiece() != null) {
                    if(game.getBoard()[i][j].getPiece().getPieceName() == "pawn" && game.getBoard()[i][j].getPiece().getPlayer().getColor() == PlayerColor.BLACK){
                        piecesLocal.put("pawnB"+pawnB,game.getBoard()[i][j].getPiece().getLocation().getPosition());
                        pawnB++;
                    }
                    if(game.getBoard()[i][j].getPiece().getPieceName() == "pawn" && game.getBoard()[i][j].getPiece().getPlayer().getColor() == PlayerColor.WHITE){
                        piecesLocal.put("pawnW"+pawnW,game.getBoard()[i][j].getPiece().getLocation().getPosition());
                        pawnW++;
                    }
                    if(game.getBoard()[i][j].getPiece().getPieceName() == "king" && game.getBoard()[i][j].getPiece().getPlayer().getColor() == PlayerColor.BLACK){
                        piecesLocal.put("kingB",game.getBoard()[i][j].getPiece().getLocation().getPosition());
                    }
                    if(game.getBoard()[i][j].getPiece().getPieceName() == "king" && game.getBoard()[i][j].getPiece().getPlayer().getColor() == PlayerColor.WHITE){
                        piecesLocal.put("kingW",game.getBoard()[i][j].getPiece().getLocation().getPosition());
                    }

                    if(game.getBoard()[i][j].getPiece().getPieceName() == "queen" && game.getBoard()[i][j].getPiece().getPlayer().getColor() == PlayerColor.BLACK){
                        piecesLocal.put("queenB",game.getBoard()[i][j].getPiece().getLocation().getPosition());
                    }
                    if(game.getBoard()[i][j].getPiece().getPieceName() == "queen" && game.getBoard()[i][j].getPiece().getPlayer().getColor() == PlayerColor.WHITE){
                        piecesLocal.put("queenW",game.getBoard()[i][j].getPiece().getLocation().getPosition());
                    }
                    if(game.getBoard()[i][j].getPiece().getPieceName() == "rook" && game.getBoard()[i][j].getPiece().getPlayer().getColor() == PlayerColor.BLACK && rookB){
                        piecesLocal.put("rook2A",game.getBoard()[i][j].getPiece().getLocation().getPosition());
                        rookB = false;
                    }else {
                        piecesLocal.put("rook2B",game.getBoard()[i][j].getPiece().getLocation().getPosition());

                    }
                    if(game.getBoard()[i][j].getPiece().getPieceName() == "rook" && game.getBoard()[i][j].getPiece().getPlayer().getColor() == PlayerColor.WHITE && rookW){
                        piecesLocal.put("rook1A",game.getBoard()[i][j].getPiece().getLocation().getPosition());
                        rookB = false;
                    }else {
                        piecesLocal.put("rook1B",game.getBoard()[i][j].getPiece().getLocation().getPosition());

                    }

                    if(game.getBoard()[i][j].getPiece().getPieceName() == "bishop" && game.getBoard()[i][j].getPiece().getPlayer().getColor() == PlayerColor.BLACK && bishopB){
                        piecesLocal.put("bishop2A",game.getBoard()[i][j].getPiece().getLocation().getPosition());
                        bishopB = false;
                    }else {
                        piecesLocal.put("bishop2B",game.getBoard()[i][j].getPiece().getLocation().getPosition());

                    }
                    if(game.getBoard()[i][j].getPiece().getPieceName() == "bishop" && game.getBoard()[i][j].getPiece().getPlayer().getColor() == PlayerColor.WHITE && bishopW){
                        piecesLocal.put("bishop1A",game.getBoard()[i][j].getPiece().getLocation().getPosition());
                        bishopW = false;
                    }else {
                        piecesLocal.put("bishop1B",game.getBoard()[i][j].getPiece().getLocation().getPosition());

                    }

                    if(game.getBoard()[i][j].getPiece().getPieceName() == "knight" && game.getBoard()[i][j].getPiece().getPlayer().getColor() == PlayerColor.BLACK && knightB){
                        piecesLocal.put("knight2A",game.getBoard()[i][j].getPiece().getLocation().getPosition());
                        knightB = false;
                    }else {
                        piecesLocal.put("knight2B",game.getBoard()[i][j].getPiece().getLocation().getPosition());

                    }
                    if(game.getBoard()[i][j].getPiece().getPieceName() == "knight" && game.getBoard()[i][j].getPiece().getPlayer().getColor() == PlayerColor.WHITE && knightW){
                        piecesLocal.put("knight1A",game.getBoard()[i][j].getPiece().getLocation().getPosition());
                        knightW = false;
                    }else {
                        piecesLocal.put("knight1B",game.getBoard()[i][j].getPiece().getLocation().getPosition());

                    }

                }
            }
        }
        return piecesLocal;
    }

    public int getPostionConstant( String pos){
        int position =0;
        for(int i =0;i< ChessConstants.positions.length;i++){
            if(ChessConstants.positions[i]== pos)
                position = i;
        }
        return position;
    }


    public void upDate(Map<String,Integer> piecesServer,Map<String,Integer> piecesLocal) {
        Iterator itServer = piecesServer.entrySet().iterator();
        Iterator itLocal = piecesLocal.entrySet().iterator();

        while( itServer.hasNext()) {
            Map.Entry serverEntry = (Map.Entry)itServer.next();
            while(itLocal.hasNext()) {
                Map.Entry localEntry = (Map.Entry)itLocal.next();
                if((serverEntry.getKey() == localEntry.getKey() && serverEntry.getValue() != localEntry.getValue())) {
                    if (game.move((Integer) localEntry.getValue(), (Integer)serverEntry.getValue())) {
                        Log.i("Move "+ localEntry.getValue(),serverEntry.getValue().toString());
                    }
                }
            }
        }
    }
    // reference :http://stackoverflow.com/questions/16954196/alertdialog-with-checkbox-in-android
    public void promotedOp() {
        final CharSequence[] items = {" Knight "," Queen "," Rook "," Bishop "};
        // arraylist to keep the selected items
        final ArrayList seletedItems=new ArrayList();

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Select the piece for promotion")
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            seletedItems.add(indexSelected);
                        } else if (seletedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            seletedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on OK
                        //  You can write the code  to save the selected item here
                        if(seletedItems.size() == 1){
                            urlPromote = urlPromote+ game.playerId()+ (String)seletedItems.get(0);
                            JsonObjectRequest promoteRequeste = new JsonObjectRequest(Request.Method.POST, urlPromote, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });

                        }else {
                            Toast.makeText(getApplicationContext(),"Select one piece Please !!!!!",Toast.LENGTH_LONG).show();
                            promotedOp();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                    }
                }).create();
        dialog.show();
    }

}
