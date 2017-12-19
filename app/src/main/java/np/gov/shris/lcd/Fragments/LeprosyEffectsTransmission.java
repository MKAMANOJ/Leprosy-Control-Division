package np.gov.shris.lcd.Fragments;

/**
 * Created by mka on 9/18/17.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;

import java.util.ArrayList;

import np.gov.shris.lcd.Models.NoticeItem;
import np.gov.shris.lcd.R;


public class LeprosyEffectsTransmission extends Fragment {

    ListView listView;
    ArrayList<NoticeItem> noticeItems;
    private String filename;

    public LeprosyEffectsTransmission() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_common_webview, container, false);

        WebView webView = (WebView) rootview.findViewById(R.id.webVew);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/leprosy_effects_transmission.html");
        return rootview;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
