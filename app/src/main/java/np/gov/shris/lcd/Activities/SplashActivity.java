package np.gov.shris.lcd.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.squareup.picasso.Picasso;

import np.gov.shris.lcd.R;


/**
 * Created by mka on 6/27/17.
 */

public class SplashActivity extends Activity {

    String imageSlideShow[] = {
            "http://apis.citizeninfotech.com.np/lcd/storage/leprosy1.jpg",
            "http://apis.citizeninfotech.com.np/lcd/storage/leprosy2.jpg",
            "http://apis.citizeninfotech.com.np/lcd/storage/leprosy3.jpg"
    };

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        for (int i = 0; i < imageSlideShow.length; i++) {
            Picasso.with(this)
                    .load(imageSlideShow[i])
                    .error(R.mipmap.ic_launcher)
                    .resize(600, 600)
                    .fetch();
        }

        /* New Handler to start the MainActivity
         * and close this Splash-Screen after some seconds.*/

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, ImageSliderActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
                finish();
            }
        };

        handler.postDelayed(runnable,SPLASH_DISPLAY_LENGTH);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
