package np.gov.shris.lcd.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import np.gov.shris.lcd.R;

public class ImageSliderActivity extends AppCompatActivity {


    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    private ViewFlipper flipper;
    String imageSlideShow[] = {
            "http://apis.citizeninfotech.com.np/lcd/storage/leprosy1.jpg",
            "http://apis.citizeninfotech.com.np/lcd/storage/leprosy2.jpg",
            "http://apis.citizeninfotech.com.np/lcd/storage/leprosy3.jpg"
    };

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_flipper);
        flipper = (ViewFlipper) findViewById(R.id.viewflipper);

        for(int i=0;i<imageSlideShow.length;i++)
        {
            // create dynamic image view and add them to ViewFlipper
            setImageInFlipr(imageSlideShow[i]);
        }

        handler = new Handler();

       flipper.addOnLayoutChangeListener(onLayoutChangeListener_viewFlipper);

    }

    View.OnLayoutChangeListener onLayoutChangeListener_viewFlipper = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            int displayedChild = flipper.getDisplayedChild();
            int childCount = flipper.getChildCount();

            if (displayedChild == childCount - 1) {
                flipper.stopFlipping();

                /* New Handler to start the MainActivity
         * and close this Splash-Screen after some seconds.*/

                runnable = new Runnable() {
                    @Override
                    public void run() {
                          /* Create an Intent that will start the Menu-Activity. */
                        Intent mainIntent = new Intent(ImageSliderActivity.this, MainActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                        finish();
                    }
                };

                handler.postDelayed(runnable,SPLASH_DISPLAY_LENGTH);
            }
        }
    };

    private void setImageInFlipr(String imgUrl) {

        ImageView image = new ImageView(ImageSliderActivity.this);
        Picasso.with(ImageSliderActivity.this)
                .load(imgUrl)
                .error(R.drawable.government_high_resolution_image)
                .resize(600,600)
                .into(image);
        flipper.addView(image);
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }
}
