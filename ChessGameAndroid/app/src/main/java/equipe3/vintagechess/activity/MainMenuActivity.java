package equipe3.vintagechess.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import equipe3.vintagechess.gameModel.*;
import equipe3.vintagechess.activity.*;
import equipe3.vintagechess.request.QueueSingleton;

import com.CS213.androidchess101.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.Charset;

import static equipe3.vintagechess.activity.ChessActivity.baseUrl;
import static equipe3.vintagechess.activity.ChessActivity.urlGameStart;
import static equipe3.vintagechess.activity.ChessActivity.urlNewGame;


public class MainMenuActivity extends AppCompatActivity {
    private Button valider;
    private Button commencer;
    private EditText player1;
    private EditText player2;
    private EditText round;
    private EditText location;
    private EditText codeSecret;
    private EditText time;
    private EditText increment;
    private EditText limit;
    private EditText overTime;
    private EditText overTimeIncrement;
    private CheckBox twoTablets;
    private CheckBox enPassant;
    private RequestQueue requestQueue;
    public static String codeAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        commencer = (Button) findViewById(R.id.commencer);
        commencer.setVisibility(View.INVISIBLE);
        valider = (Button) findViewById(R.id.valider);
        player1 = (EditText) findViewById(R.id.Player1);
        player2 = (EditText) findViewById(R.id.Player2);
        round = (EditText) findViewById(R.id.round);
        location = (EditText) findViewById(R.id.Location);
        codeSecret = (EditText) findViewById(R.id.CodeSecret);
        time = (EditText) findViewById(R.id.time);
        increment = (EditText) findViewById(R.id.increment);
        limit = (EditText) findViewById(R.id.jlimit);
        overTime = (EditText) findViewById(R.id.overTime);
        overTimeIncrement = (EditText) findViewById(R.id.overTimeIncrement);
        twoTablets = (CheckBox) findViewById(R.id.twoTablets);
        enPassant = (CheckBox) findViewById(R.id.enPassant);

        requestQueue = QueueSingleton.getInstance(this.getApplicationContext()).getRequestQueue();;

        commencer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, urlGameStart, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("HTTP :", "200 OK");
                                Toast.makeText(getApplicationContext(), "HTTP : 200 OK", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainMenuActivity.this, equipe3.vintagechess.activity.ChessActivity.class);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("HTTP :", "401 NO");
                                Toast.makeText(getApplicationContext(), "HTTP : 401 NO", Toast.LENGTH_LONG).show();
                            }
                        }
                );
                requestQueue.add(jsObjRequest);
            }
        });
        valider.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                JSONObject jsonNewGame = new JSONObject();
                /*if (isInteger(time.getText().toString()) &&
                        isInteger(limit.getText().toString()) &&
                        isInteger(overTime.getText().toString()) &&
                        isInteger(overTimeIncrement.getText().toString())) {*/

                    String tps = time.getText().toString();
                    try {
                        jsonNewGame.put("player1", player1.getText().toString());
                        jsonNewGame.put("player2", player2.getText().toString());
                        jsonNewGame.put("round", Integer.parseInt(round.getText().toString()));
                        jsonNewGame.put("location", location.getText().toString());
                        jsonNewGame.put("codeSecret", stringToBase64(codeSecret.getText().toString()));
                        codeAuth = stringToBase64(codeSecret.getText().toString());
                        if (twoTablets.isChecked()) {
                            jsonNewGame.put("twoTablets", Boolean.TRUE);
                        } else {
                            jsonNewGame.put("twoTablets", Boolean.FALSE);
                        }
                        if (enPassant.isChecked()) {
                            jsonNewGame.put("enPassant", Boolean.TRUE);
                        } else {
                            jsonNewGame.put("enPassant", Boolean.FALSE);
                        }
                        JSONObject time = new JSONObject();
                        time.put("time", Integer.parseInt(tps));
                        time.put("Increment", Integer.parseInt(increment.getText().toString()));
                        time.put("limit", Integer.parseInt(limit.getText().toString()));
                        time.put("overtime", Integer.parseInt(overTime.getText().toString()));
                        time.put("overtimeIncrement", Integer.parseInt(overTimeIncrement.getText().toString()));
                        jsonNewGame.put("timeFormat", time);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, urlNewGame, jsonNewGame,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.i("newGame :", response.toString());
                                    commencer.setVisibility(View.VISIBLE);
                                    valider.setVisibility(View.INVISIBLE);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "A game is currently in process", Toast.LENGTH_LONG).show();
                                }
                            }
                    );

                    requestQueue.add(jsObjRequest);
               /* } else {
                    Toast.makeText(getApplicationContext(), "Please fill up all the informations to play", Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }


    private String stringToBase64(String toByte) {
        byte[] byteArray = toByte.getBytes(Charset.forName("UTF-8"));
        String base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        //On enleve le \n
        base64 = base64.substring(0, base64.length() - 1);
        return base64;
    }

    private String base64ToString(String base64){
        byte[] byteArray = Base64.decode(base64, Base64.DEFAULT);
        String data = byteArray.toString();
        return data;
    }


    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}


