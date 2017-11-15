package equipe3.vintagechess.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.CS213.androidchess101.R;

/**
 * Created by lester on 16-11-18.
 */

public class MusicService extends Service{
    public static final String TAG = "AudioService";

    MediaPlayer player;

    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.music);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return START_NOT_STICKY;
    }


    public IBinder onUnBind(Intent arg0) {
        return null;
    }

    public void onStop() {
        player.stop();
    }

    public void onPause() {
        player.pause();
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {
        player.stop();
        player.release();
    }
}
