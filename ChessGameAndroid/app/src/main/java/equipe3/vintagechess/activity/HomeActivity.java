package equipe3.vintagechess.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.CS213.androidchess101.R;

import equipe3.vintagechess.service.MusicService;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initPlayButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*if (id == R.id.action_settings) {
			return true;
		}*/
        return super.onOptionsItemSelected(item);
    }

    private void initPlayButton() {

        Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View argo) {

                startActivity(new Intent(HomeActivity.this, MainMenuActivity.class));
            }
        });
    }


}
