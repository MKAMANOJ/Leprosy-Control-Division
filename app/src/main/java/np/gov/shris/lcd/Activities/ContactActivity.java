package np.gov.shris.lcd.Activities;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import np.gov.shris.lcd.Helpers.AppConstants;
import np.gov.shris.lcd.R;

/**
 * Created by mka on 9/20/17.
 */

public class ContactActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView btnContactPhone, btnContactFax;
    TextView btnContactEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.main_contact_us);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.setDrawerListener(toggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        btnContactPhone = (TextView) findViewById(R.id.btnTelephone);
        btnContactFax = (TextView) findViewById(R.id.btnFax);
        btnContactEmail = (TextView) findViewById(R.id.btnContactEmail);


        // drawable right click listener
        btnContactPhone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == 0) {
                    if (event.getRawX() >= (btnContactPhone.getRight() - btnContactPhone.getCompoundDrawables()
                            [DRAWABLE_RIGHT].getBounds().width())) {
                        final String[] phone = AppConstants.contactPhoneList;
                        AlertDialog.Builder builder = new AlertDialog.Builder(ContactActivity.this);
                        builder.setItems(phone, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                                intent1.setData(Uri.parse("tel:" + phone[which]));
                                try {
                                    startActivity(intent1);
                                } catch (ActivityNotFoundException activityNotFound) {

                                    Toast.makeText(ContactActivity.this, getString(R.string.no_phone_program),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        builder.show();
                        return true;
                    }
                }
                return false;
            }
        });

        // drawable right click listener
        btnContactEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == 0) {
                    if (event.getRawX() >= (btnContactEmail.getRight() - btnContactEmail.getCompoundDrawables()
                            [DRAWABLE_RIGHT].getBounds().width())) {
                        final String[] email = AppConstants.contactEmailList;
                        AlertDialog.Builder builder = new AlertDialog.Builder(ContactActivity.this);
                        builder.setItems(email, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                emailIntent.setType("plain/text");
                                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email[which]});
                                try {

                                    startActivity(emailIntent);
                                } catch (ActivityNotFoundException activityNotFound) {

                                    Toast.makeText(ContactActivity.this, getString(R.string.no_email_program),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        builder.show();
                        return true;
                    }
                }
                return false;
            }
        });

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
