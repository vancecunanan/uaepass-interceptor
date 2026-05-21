package com.uaepass.interceptor;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;

import org.apache.cordova.*;
import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;

public class UAEPassInterceptor extends CordovaPlugin {

    @Override
    public void pluginInitialize() {

        cordova.getActivity().runOnUiThread(() -> {

            SystemWebViewEngine engine = (SystemWebViewEngine) webView.getEngine();
            SystemWebViewClient existingClient = new SystemWebViewClient(engine) {

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

                            return true; // stop original

                        } catch (Exception e) {
                            Log.e("UAEPASS", "Error handling URL", e);
                        }
                    }

                    return super.shouldOverrideUrlLoading(view, url);
                }
            };

            engine.getView().setWebViewClient(existingClient);
        });
    }
}
