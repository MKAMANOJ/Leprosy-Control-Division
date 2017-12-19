package np.gov.shris.lcd.Activities;

import android.Manifest;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import np.gov.shris.lcd.Adapters.CustomExpandableListAdapter;
import np.gov.shris.lcd.Helpers.AppConstants;
import np.gov.shris.lcd.Models.NoticeItem;
import np.gov.shris.lcd.R;

public class PublicationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ExpandableListView listView;
    ArrayList<String> listHeader;
    HashMap<String, List<NoticeItem>> listChildData;
    private String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActivity();
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

    public void initActivity() {

        setContentView(R.layout.activity_publication);

        setTitle(R.string.main_publications);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.setDrawerListener(toggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        generateData();
        listView = (ExpandableListView) findViewById(R.id.listView);
        CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(this, listHeader, listChildData);

        listView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
//
//                Snackbar.make(view,listChildData.get(listHeader.get(groupPosition)).get(childPosition).file
//                        , Snackbar.LENGTH_LONG).show();
                final NoticeItem noticeItem = listChildData.get(listHeader.get(groupPosition)).get(childPosition);

                filename = noticeItem.file;

                if (filename != null && !filename.isEmpty()) {
                    Permissions.check(PublicationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            getString(R.string.storage_permission_necessary), new Permissions.Options()
                                    .setRationaleDialogTitle("Info"),
                            new PermissionHandler() {
                                @Override
                                public void onGranted() {
                                    new DownLoadOperation().execute(noticeItem);
                                }

                                @Override
                                public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                                    Toast.makeText(context, getString(R.string.storage_permission_denied),
                                            Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public boolean onBlocked(Context context, ArrayList<String> blockedList) {
                                    Toast.makeText(context, getString(R.string.storage_permisson_onblocked),
                                            Toast.LENGTH_LONG).show();
                                    return false;
                                }

                                @Override
                                public void onJustBlocked(Context context, ArrayList<String> justBlockedList,
                                                          ArrayList<String> deniedPermissions) {
//                                            Toast.makeText(context, "Storage just blocked:\n" + Arrays.toString(deniedPermissions.toArray()),
//                                                    Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    Toast.makeText(PublicationActivity.this,getString(R.string.no_pdf_associated),
                            Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

    }


    private void generateData() {
        listHeader = new ArrayList<>();
        listChildData = new HashMap<>();

        listHeader.add("1. Form/Formats");
        List<NoticeItem> publication1 = new ArrayList<>();
        publication1.add(new NoticeItem(
                "Form-Reporting-Case.pdf",
                "i) Form for house-house case Search Activity",
                null,
                "storage/Form-Reporting-Case.pdf"));

        publication1.add(new NoticeItem(
                null,
                "ii) Form: To record the list of new case detection after the search",
                null,
                null));

        publication1.add(new NoticeItem(
                null,
                "iii) Form: Reporting Case",
                null,
                null));

        listHeader.add("2. Guidelines/Policies, Acts and Regulations");
        List<NoticeItem> publication2 = new ArrayList<>();
        publication2.add(new NoticeItem(
                "Leprosy-Guideline-2070-2013.pdf",
                "i) Leprosy Programme National Program Implementation Guidelines 2070 (2013)",
                null,
                "storage/Leprosy-Guideline-2070-2013.pdf"));

        publication2.add(new NoticeItem(
                "Guidelines_for_Leprosy_Elimination_Campaign_in_Sub_National_Level.pdf",
                "ii) Guidelines for Leprosy Elimination Campaign in Sub National Level",
                null,
                "storage/Guidelines_for_Leprosy_Elimination_Campaign_in_Sub_National_Level.pdf"));

        publication2.add(new NoticeItem(
                "Leprosy-Control-Program-Nirdesika-2073-74.pdf",
                "iii) Leprosy Control Programme Guidelines 2073-74",
                null,
                "storage/Leprosy-Control-Program-Nirdesika-2073-74.pdf"));

        publication2.add(new NoticeItem(
                "Policy-Strategy-10-years-Action-Plan-on-DisabilityManagement_LCD.pdf",
                "iv) Policy, Strategy and 10 Year Action Plan on Disability Management (Prevention, Treatment and Rehabilitation) 2073-2082",
                null,
                "storage/Policy-Strategy-10-years-Action-Plan-on-DisabilityManagement_LCD.pdf"));

        listHeader.add("3. Reports");
        List<NoticeItem> publication3 = new ArrayList<>();
        publication3.add(new NoticeItem(
                "LCD_Annual-Report-71-72.pdf",
                "i) Leprosy Control Program Annual Report",
                null,
                "storage/LCD_Annual-Report-71-72.pdf"));

        listHeader.add("4. Other Publications");
        List<NoticeItem> publication4 = new ArrayList<>();
        publication4.add(new NoticeItem(
                "Bangkok-Declaration-for-Accelerating-Towards-Leprosy-Free-World-July-24-26-2013.pdf",
                "v) Bangkok Declaration for Accelerating Towards Leprosy Free World, July 24-26, 2013",
                null,
                "storage/Bangkok-Declaration-for-Accelerating-Towards-Leprosy-Free-World-July-24-26-2013.pdf"));

        publication4.add(new NoticeItem(
                "HamroSawalFinal_Combined-_4th-Year-6th-Edition_Baisakh-2074.pdf",
                "vi) HAMRO SAWAL, 4TH YEAR, 6TH EDITION, BAISAKH 2074 (APRIL 2017)",
                null,
                "storage/HamroSawalFinal_Combined-_4th-Year-6th-Edition_Baisakh-2074.pdf"));

        listChildData.put(listHeader.get(0), publication1); // Header, Child data
        listChildData.put(listHeader.get(1), publication2);
        listChildData.put(listHeader.get(2), publication3);
        listChildData.put(listHeader.get(3), publication4);
    }

    public long downloadFile(String url, String asFileName) {

        //Check folder exists, if not create
        File folder = new File(AppConstants.path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        File file = new File(AppConstants.path + asFileName);
        Log.e("file", "downloadFile: " + asFileName + " : " + (file.exists() ? "exists" : "does not exist"));

        //check if file already exists
        if (!file.exists()) {
            //Progress dialog
            //Check for active network connection
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                Uri Download_Uri = Uri.parse(url.replace(" ", "%20"));

                Log.e("Download URI ",Download_Uri.toString());

                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setTitle(asFileName);
                request.setVisibleInDownloadsUi(true);

                request.setDestinationInExternalPublicDir(AppConstants.downloadPath, asFileName);
                return downloadManager.enqueue(request);
            } else {
                return AppConstants.NO_INTERNET_CONNECTION;
            }
        } else {
            return AppConstants.PDF_REQUIRED;
        }
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long refId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            String action = intent.getAction();
            if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));
                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Cursor cursor = manager.query(query);
                if (cursor.moveToFirst()) {
                    if (cursor.getCount() > 0) {
                        int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            String file = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                            // So something here on success

                            NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(PublicationActivity.this)
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle(getString(R.string.app_name))
                                            .setContentText(filename + " \t Download completed");

                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify(455, mBuilder.build());

                            //Intent pdf file here
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setDataAndType(Uri.fromFile(new File(AppConstants.path
                                    + filename)), "application/pdf");
                            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            try {
                                startActivity(i);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(PublicationActivity.this, getString(R.string.pdf_reader_required),
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            int message = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON));
                            // So something here on failed.
                            Toast.makeText(PublicationActivity.this, getString(R.string.error_msg),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

        }
    };

    private class DownLoadOperation extends AsyncTask<NoticeItem, Void, Long> {

        @Override
        protected Long doInBackground(NoticeItem... noticeItems) {
            return downloadFile(noticeItems[0].fileUrl, noticeItems[0].file);
        }

        @Override
        protected void onPostExecute(Long noticeItem) {
            if (noticeItem == AppConstants.NO_INTERNET_CONNECTION){
                AlertDialog.Builder builder = new AlertDialog.Builder(PublicationActivity.this);
                builder.setTitle(getString(R.string.network_error));
                builder.setMessage(getString(R.string.no_internet_connection));
                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }else if (noticeItem == AppConstants.PDF_REQUIRED){

                //Intent pdf file here
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.fromFile(new File(AppConstants.path
                        + filename)), "application/pdf");
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(PublicationActivity.this, getString(R.string.pdf_reader_required),
                            Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
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
