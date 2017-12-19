package np.gov.shris.lcd.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import np.gov.shris.lcd.Adapters.ImageAdapter;
import np.gov.shris.lcd.Helpers.AppConstants;
import np.gov.shris.lcd.Utilities.ExpandableGridView;
import np.gov.shris.lcd.Utilities.OnSwipeTouchListener;
import np.gov.shris.lcd.R;


/**
 * Created by mka on 9/20/17.
 */

public class GalleryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Integer> list1;

    int pos;

    ImageView img;

    AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.main_photo_gallery);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.setDrawerListener(toggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        //Dialog init for displaying image
        dialog = new AlertDialog.Builder(GalleryActivity.this).create();

        ExpandableGridView gridview1 = (ExpandableGridView) findViewById(R.id.gridview1);
        gridview1.setExpanded(true);

        list1 = new ArrayList<>();
        generateData();

        gridview1.setAdapter(new ImageAdapter(getApplicationContext(), list1));


        gridview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                pos = position;
//                setImageInDialog(list1.get(position), list1);
//                dialog.show();
                showImageInAlertView(list1.get(position), list1);
            }
        });

    }

    private void setImageInDialog(Integer imageResource, final ArrayList<Integer> list) {
        LayoutInflater inflator = LayoutInflater.from(GalleryActivity.this);
        View view = inflator.inflate(R.layout.image_view, null);

        img = (ImageView) view.findViewById(R.id.dialog_imageview);
        img.setImageResource(imageResource);

        img.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeLeft() {
                pos++;
                if (pos > (list.size() - 1)) {
                    dialog.dismiss();
                }
                setImageInDialog(list.get(pos), list);
                //showImageInAlertView(list.get(pos), list);
            }

            public void onSwipeRight() {
                pos--;
                if (pos < 0) {
                    dialog.dismiss();
                }
                setImageInDialog(list.get(pos), list);
                //showImageInAlertView(list.get(pos), list);
            }
        });

        dialog.setView(view);

    }

    private void showImageInAlertView(Integer imageResource, final ArrayList<Integer> list) {
        dialog = new AlertDialog.Builder(GalleryActivity.this).create();
        LayoutInflater inflator = LayoutInflater.from(GalleryActivity.this);
        View view = inflator.inflate(R.layout.image_view, null);

        img = (ImageView) view.findViewById(R.id.dialog_imageview);
        Picasso.with(GalleryActivity.this)
                .load(imageResource)
                .error(R.drawable.government_high_resolution_image)
                .resize(1600,1600)
                .centerInside()
                .into(img);

        img.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeLeft() {
                pos++;
                dialog.dismiss();
                showImageInAlertView(list.get(pos), list);
            }

            public void onSwipeRight() {
                pos--;
                dialog.dismiss();
                showImageInAlertView(list.get(pos), list);
            }
        });

        dialog.setView(view);
        dialog.show();
    }

    private void generateData() {

        for (int i = 0; i < 6; i++) {
            list1.add(AppConstants.galleryList.get(i));
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.action_about);
            builder.setMessage(R.string.action_about_msg);
            builder.show();
            return true;
        } else if (id == R.id.action_feedback) {
            startActivity(new Intent(this, ContactActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.main_introduction:
                //Introduction
                startActivity(new Intent(getApplicationContext(), IntroductionActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.main_programs:
                //Programs
                startActivity(new Intent(getApplicationContext(), ProgramActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.main_leprosy_effects:
                //Leprosy Effects
                startActivity(new Intent(getApplicationContext(), LeprosyEffectsActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.main_news:
                //News
                startActivity(new Intent(getApplicationContext(), NewsAndEventsActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.main_photo_gallery:
                //Photo Gallery
                startActivity(new Intent(getApplicationContext(), GalleryActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.main_staff_information:
                //Staff Information
                startActivity(new Intent(getApplicationContext(), StaffInformationActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.main_publications:
                //Publications
                startActivity(new Intent(getApplicationContext(), PublicationActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.main_contact_us:
                //Contact us
                startActivity(new Intent(getApplicationContext(), ContactActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.main_director_message:
                //Director's Message
                startActivity(new Intent(getApplicationContext(), DirectorActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}
