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
import android.support.annotation.Nullable;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import np.gov.shris.lcd.Adapters.NewsAdapter;
import np.gov.shris.lcd.Helpers.AppConstants;
import np.gov.shris.lcd.Helpers.DatabaseHandler;
import np.gov.shris.lcd.Models.News;
import np.gov.shris.lcd.Models.NewsStatics;
import np.gov.shris.lcd.NetworkUtility.RetrofitCallBack;
import np.gov.shris.lcd.NetworkUtility.RetrofitInterface;
import np.gov.shris.lcd.NetworkUtility.RetrofitService;
import np.gov.shris.lcd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsAndEventsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,RetrofitCallBack<NewsStatics> {

    private static final String TAG = NewsAndEventsActivity.class.getName();

    ListView listView;
    private String filename;
    DatabaseHandler db;
    NewsAdapter newsAdapter;
    List<News> newsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.main_news);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.setDrawerListener(toggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        db = new DatabaseHandler(NewsAndEventsActivity.this);
        listView = (ListView) findViewById(R.id.newsListiew);
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        RetrofitInterface retrofitService = RetrofitService.getClient();
        Call<NewsStatics> call = retrofitService.getNews();
        call.enqueue(new Callback<NewsStatics>() {
            @Override
            public void onResponse(Call<NewsStatics> call, Response<NewsStatics> response) {

                int code = response.code();
                if (code >= 200 && code < 300) {
                    success(response);
                } else if (code == 401) {
                    unauthenticated(response);
                } else if (code >= 400 && code < 500) {
                    clientError(response);
                } else if (code >= 500 && code < 600) {
                    serverError(response);
                } else {
                    unexpectedError(new RuntimeException("Unexpected response " + response));
                }
            }

            @Override
            public void onFailure(Call<NewsStatics> call, Throwable t) {

                if (t instanceof IOException) {
                    networkError((IOException) t);
                } else {
                    unexpectedError(t);
                }
                t.printStackTrace();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                final News news = newsList.get(position);

                filename = news.getOrginalFilename();

                if (filename != null && !filename.isEmpty()) {
                    Permissions.check(NewsAndEventsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            getString(R.string.storage_permission_necessary), new Permissions.Options()
                                    .setRationaleDialogTitle("Info"),
                            new PermissionHandler() {
                                @Override
                                public void onGranted() {
                                    new DownLoadOperation().execute(news);
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

                    Permissions.check(NewsAndEventsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            getString(R.string.storage_permission_necessary), new Permissions.Options()
                                    .setRationaleDialogTitle("Info"),
                            new PermissionHandler() {
                                @Override
                                public void onGranted() {

                                    Intent intent = new Intent(NewsAndEventsActivity.this, NoticeDetails.class);

                                    intent.putExtra("title", news.getNewsTitle());
                                    intent.putExtra("details", "");
                                    intent.putExtra("filename", news.getOrginalFilename());

                                    startActivity(intent);
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

                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
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
    public void success(Response<NewsStatics> response) {
        NewsStatics newsStatics = response.body();
        if (newsStatics.getCount() == 0){
            displayDialog(getString(R.string.zero_news_title),getString(R.string.zero_news));

            // empty table before
            db.truncate();
        }else{

            // empty table before
            db.truncate();
            newsList = new ArrayList<>();
            newsList = newsStatics.getNews();
            // Inserting news
            for (News news : newsList){
                db.addNews(news);
            }

            newsAdapter = new NewsAdapter(NewsAndEventsActivity.this, 0,db.getAllNews());
            listView.setAdapter(newsAdapter);
            newsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void unauthenticated(Response<?> response) {
        displayDialog(getString(R.string.unauthorized_error_tile), response.message());
    }

    @Override
    public void clientError(Response<?> response) {
        displayDialog(getString(R.string.client_error_title), response.message());
    }

    @Override
    public void serverError(Response<?> response) {
        displayDialog(getString(R.string.server_error_title), response.message());
    }

    @Override
    public void networkError(IOException e) {
        ConnectivityManager connMgr = (ConnectivityManager) NewsAndEventsActivity.this.
                getSystemService(Context.CONNECTIVITY_SERVICE); // 1
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); // 2
        if (networkInfo == null || !networkInfo.isConnected()) {
            if (db.getNewsCount() !=0) {

                newsList = new ArrayList<>();
                newsList = db.getAllNews();
                newsAdapter = new NewsAdapter(NewsAndEventsActivity.this, 0, newsList);
                listView.setAdapter(newsAdapter);
                newsAdapter.notifyDataSetChanged();
            }else{
                displayDialog(getString(R.string.internet_connection_required),getString(R.string.must_connect_internet));
            }
        } else {
            displayDialog(this.getString(R.string.network_error_title), e.getLocalizedMessage());
        }
    }

    @Override
    public void unexpectedError(Throwable t) {
        displayDialog(getString(R.string.fatal_error_title), t.getLocalizedMessage());
    }

    public void displayDialog(String title, String message) {
        AlertDialog alertDialog =
                new AlertDialog.Builder(NewsAndEventsActivity.this)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
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

                Log.e("NEWS",Download_Uri.toString());

                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setTitle(asFileName);
                request.setVisibleInDownloadsUi(true);

                request.setDestinationInExternalPublicDir(AppConstants.downloadPath, asFileName);
                return downloadManager.enqueue(request);
                //Network is available
//                progressDialog = new ProgressDialog(NewsAndEventsActivity.this);
//                progressDialog.setMessage(getString(R.string.please_wait));
//                progressDialog.setTitle(getString(R.string.downloading_resources));
//                progressDialog.setCancelable(false);
//                progressDialog.show();
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
                                    new NotificationCompat.Builder(NewsAndEventsActivity.this)
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
                                Toast.makeText(NewsAndEventsActivity.this, getString(R.string.pdf_reader_required),
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            int message = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON));
                            // So something here on failed.
                            Toast.makeText(NewsAndEventsActivity.this, getString(R.string.error_msg),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

        }
    };

    private class DownLoadOperation extends AsyncTask<News, Void, Long> {

        @Override
        protected Long doInBackground(News... news) {
            return downloadFile(AppConstants.DOWNLOAD_NEWS_URL_PREFIX + news[0].getFileLink(),
                    news[0].getOrginalFilename());
        }

        @Override
        protected void onPostExecute(Long noticeItem) {
            if (noticeItem == 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(NewsAndEventsActivity.this);
                builder.setTitle(getString(R.string.network_error));
                builder.setMessage(getString(R.string.no_internet_connection));
                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }else if (noticeItem == -1){

                //Intent pdf file here
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.fromFile(new File(AppConstants.path
                        + filename)), "application/pdf");
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(NewsAndEventsActivity.this, getString(R.string.pdf_reader_required),
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
    protected void onPause() {
        super.onPause();
    }

}
