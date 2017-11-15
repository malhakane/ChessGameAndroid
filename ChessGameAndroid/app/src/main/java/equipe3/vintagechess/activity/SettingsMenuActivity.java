package equipe3.vintagechess.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.CS213.androidchess101.R;

import java.util.ArrayList;
import java.util.List;

import equipe3.vintagechess.constant.BoardAttributes;
import equipe3.vintagechess.service.MusicService;

/**
 * Created by lester on 16-11-18.
 */

public class SettingsMenuActivity extends AppCompatActivity {

    private Spinner colorSquareSpinner, chessPieceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu_activity);

        addItemsOnColorSpinner();
        addItemsOnPieceSpinner();
        addListenerOnChkBox();
        addListenerOnButton();
    }

    public void addItemsOnColorSpinner() {
        colorSquareSpinner = (Spinner) findViewById(R.id.colorSquareSpinner);

        List<String> colorList = new ArrayList<>();
        colorList.add("Color 1");
        colorList.add("Color 2");
        colorList.add("color 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colorList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSquareSpinner.setAdapter(dataAdapter);
    }

    public void addItemsOnPieceSpinner() {
        chessPieceSpinner = (Spinner) findViewById(R.id.chessPieceSpinner);

        List<String> pieceList = new ArrayList<>();
        pieceList.add("Style 1");
        pieceList.add("Style 2");
        pieceList.add("Style 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pieceList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chessPieceSpinner.setAdapter(dataAdapter);
    }

    public void addListenerOnButton() {

        colorSquareSpinner = (Spinner) findViewById(R.id.colorSquareSpinner);
        chessPieceSpinner = (Spinner) findViewById(R.id.chessPieceSpinner);
        Button btnSubmitSettings = (Button) findViewById(R.id.btnSubmitSettings);

        btnSubmitSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            /*Toast.makeText(SettingsMenuActivity.this, "Selection : " +
            "\nColor : " + String.valueOf(colorSquareSpinner.getSelectedItem()) +
            "\nStyle : " + String.valueOf(chessPieceSpinner.getSelectedItem()),
            Toast.LENGTH_SHORT).show();*/

            BoardAttributes.squareColor = colorSquareSpinner.getSelectedItemPosition();
            BoardAttributes.pieceStyle = chessPieceSpinner.getSelectedItemPosition();
            finish();

            }

        });
    }

    private void processStopService(final String tag) {
        Intent intent = new Intent(getApplicationContext(), MusicService.class);
        intent.addCategory(tag);
        stopService(intent);
    }

    private void processStartService(final String tag) {
        Intent intent = new Intent(getApplicationContext(), MusicService.class);
        intent.addCategory(tag);
        startService(intent);
    }

    public void addListenerOnChkBox() {

        CheckBox musicOn = (CheckBox) findViewById(R.id.musicOn);

        musicOn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    processStartService(MusicService.TAG);
                }else{
                    processStopService(MusicService.TAG);
                }
            }
        });
    }
}