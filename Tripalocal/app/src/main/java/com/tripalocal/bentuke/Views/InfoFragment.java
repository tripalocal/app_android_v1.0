package com.tripalocal.bentuke.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tripalocal.bentuke.R;

/**
 * Created by user on 23/06/2015.
 */
public class InfoFragment extends Fragment {

    public WebView webViewPage;
    public static String title="";
    public InfoFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

           View view = inflater.inflate(R.layout.fragment_infos, container, false);
        webViewPage=(WebView)view.findViewById(R.id.infoWebView);
        webViewPage.setWebViewClient(new MyWebViewClient());
        webViewPage.loadUrl(getActivity().getResources().getString(R.string.server_url)+HomeActivity.webViewPage_info);
//        webViewPage.set
        getActivity().invalidateOptionsMenu();
        getActivity().setTitle(title);

        return view;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            Log.v("uuuurl", url);
            view.loadUrl(url);
            return true;
        }
    }

}
