package com.sohojhajirat.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        WebView w = new WebView(this);
        WebSettings s = w.getSettings();

        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setAllowFileAccess(true);

        w.addJavascriptInterface(new ShareBridge(), "AndroidShare");
        w.addJavascriptInterface(new DeviceBridge(), "AndroidDevice");

        w.loadUrl("file:///android_asset/index.html");

        setContentView(w);
    }

    public class DeviceBridge {

        @JavascriptInterface
        public String getDeviceId() {
            return Settings.Secure.getString(
                    getContentResolver(),
                    Settings.Secure.ANDROID_ID
            );
        }
    }

    public class ShareBridge {

        @JavascriptInterface
        public void share(String text) {
            Intent send = new Intent(Intent.ACTION_SEND);
            send.setType("text/plain");
            send.putExtra(Intent.EXTRA_TEXT, text);

            startActivity(
                    Intent.createChooser(
                            send,
                            "সহজ হাজিরাত শেয়ার করুন"
                    )
            );
        }
    }
}
