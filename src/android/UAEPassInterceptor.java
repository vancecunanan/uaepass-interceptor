package com.uaepass.interceptor;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.cordova.*;

public class UAEPassInterceptor extends CordovaPlugin {

    @Override
    public void onStart() {
        super.onStart();

        cordova.getActivity().runOnUiThread(() -> {

            WebView view = (WebView) this.webView.getEngine().getView();

            view.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    if (url.startsWith("uaepass://")) {
                        String newUrl = url.replace("uaepass://", "uaepassstg://");

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newUrl));
                        cordova.getActivity().startActivity(intent);

                        return true;
                    }

                    return false;
                }
            });
        });
    }
}
