package com.uaepass.interceptor;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.cordova.CordovaPlugin;

public class UAEPassInterceptor extends CordovaPlugin {

    @Override
    public void pluginInitialize() {

        cordova.getActivity().runOnUiThread(() -> {

            WebView view = (WebView) this.webView.getEngine().getView();

            view.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    Log.d("UAEPASS", "Intercepted URL: " + url);

                    if (url != null && url.startsWith("uaepass://")) {

                        try {
                            String newUrl = url.replace("uaepass://", "uaepassstg://");

                            Log.d("UAEPASS", "Redirecting to: " + newUrl);

                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newUrl));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            cordova.getActivity().startActivity(intent);

                            return true;

                        } catch (Exception e) {
                            Log.e("UAEPASS", "Error", e);
                        }
                    }

                    return false;
                }
            });
        });
    }
}
