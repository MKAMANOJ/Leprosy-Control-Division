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

import np.gov.shris.lcd.R;


public class Introduction_info extends Fragment {

    public Introduction_info() {
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
        webView.loadUrl("file:///android_asset/introduction_info.html");


        return rootview;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
