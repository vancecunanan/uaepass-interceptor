package com.uaepass.interceptor;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.apache.cordova.CordovaPlugin;

public class UAEPassInterceptor extends CordovaPlugin {

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null && intent.getData() != null) {

            Uri uri = intent.getData();
            String url = uri.toString();

            Log.d("UAEPASS", "Incoming intent URL: " + url);

            if (url.startsWith("uaepass://")) {

                try {
                    String newUrl = url.replace("uaepass://", "uaepassstg://");

                    Log.d("UAEPASS", "Redirecting to: " + newUrl);

                    Intent newIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newUrl));
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    cordova.getActivity().startActivity(newIntent);

                } catch (Exception e) {
                    Log.e("UAEPASS", "Error handling intent", e);
                }
            }
        }
    }
}
