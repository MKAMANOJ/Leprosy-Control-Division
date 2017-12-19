package np.gov.shris.lcd.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;

import np.gov.shris.lcd.Helpers.AppConstants;
import np.gov.shris.lcd.R;


public class NoticeDetails extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String title = bundle.getString("title");
            String details = bundle.getString("details");
            String filename = bundle.getString("filename");

            setTitle(title);

            if (details != null && !details.isEmpty()) {
                String htmlTemplate = "<html><head></head><body><img src='file:///android_res/drawable/" + details + "'></body></html>";
                webView.loadDataWithBaseURL(null, htmlTemplate, "text/html", "utf-8", null);
            } else if (filename != null && !filename.isEmpty()) {
                //Intent pdf file here
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(AppConstants.path
                        + filename)), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(NoticeDetails.this, getString(R.string.pdf_reader_required),
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(NoticeDetails.this,getString(R.string.no_pdf_associated),
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
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
