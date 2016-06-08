package com.tshevchuk.prayer.presentation.prayer_html_view;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.common.BasePresenter;
import com.tshevchuk.prayer.presentation.common.FragmentBase;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import hugo.weaving.DebugLog;

public class HtmlViewFragment extends FragmentBase implements HtmlViewView {
    @Inject
    HtmlViewPresenter presenter;
    private String screenTitle;
    private WebView wvContent;
    private ProgressBar pbLoading;


    public static HtmlViewFragment getInstance(int id) {
        HtmlViewFragment f = new HtmlViewFragment();
        Bundle b = new Bundle();
        b.putInt("id", id);
        f.setArguments(b);
        return f;
    }

    @Override
    protected String getScreenTitle() {
        return screenTitle;
    }

    @Override
    public void setScreenTitle(String name) {
        screenTitle = name;
        if (isResumed()) {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(screenTitle);
            }
        }
    }

    @Override
    public void scrollToUrlAnchor(String url) {
        String anchor = null;
        if (url.startsWith(URL_ROOT_ASSET_FOLDER)) {
            anchor = Uri.parse(url).getEncodedFragment();
        }
        if (!TextUtils.isEmpty(anchor)) {
            wvContent.loadUrl("javascript:function prayerbook_scrollToElement(id) {"
                    + "var elem = document.getElementById(id);" + "var x = 0; var y = 0;"
                    + "while (elem != null) {" + "  y += elem.offsetTop;"
                    + "  elem = elem.offsetParent;  }" + "window.scrollTo(x, y);  };"
                    + " console.log ( '#someButton was clicked' );"
                    + "prayerbook_scrollToElement('" + anchor + "');");
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((PrayerBookApplication) getActivity().getApplication()).getViewComponent().inject(this);
        int id = getArguments().getInt("id");
        presenter.setArgPrayerId(id);
        setHasOptionsMenu(true);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_html_view, container, false);
        FrameLayout flContent = (FrameLayout) v.findViewById(R.id.fl_content);
        pbLoading = (ProgressBar) v.findViewById(R.id.pbLoading);
        if (wvContent == null) {
            wvContent = new WebView(activity);
            wvContent.setLayoutParams(new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            WebSettings settings = wvContent.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setDefaultTextEncodingName("utf-8");

            wvContent.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    presenter.onLoadingProgresChanged(newProgress);
                }
            });

            wvContent.setWebViewClient(new PrayerWebViewClient());
            presenter.onWebViewCreated();
        }
        flContent.addView(wvContent);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ((ViewGroup) wvContent.getParent()).removeView(wvContent);
    }

    @Override
    public void onPause() {
        super.onPause();
        wvContent.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        wvContent.onResume();
    }

    @Override
    public boolean onBackButtonPress() {
        if (wvContent.canGoBack()) {
            presenter.onGoBack();
            wvContent.goBack();
            return true;
        }
        return false;
    }

    @Override
    public boolean onUpButtonPress() {
        return presenter.onUpButtonPress();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.actionbar_create_shortcut, menu);
        inflater.inflate(R.menu.actionbar_textviewfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_create_shortcut:
                presenter.onCreateShortcutClick();
                return true;
            case R.id.mi_about_prayer:
                presenter.onOpenAboutClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setFontSizeSp(int textFontSizeSp) {
        WebSettings settings = wvContent.getSettings();
        settings.setDefaultFontSize(textFontSizeSp);
    }

    @DebugLog
    @Override
    public void loadUrl(String url) {
        wvContent.loadUrl(url);
    }

    @Override
    public String getErrorReportInfo() {
        return super.getErrorReportInfo() + "; URL: " + wvContent.getUrl();
    }

    @DebugLog
    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @DebugLog
    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }


    private class PrayerWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("prayerbook://")) {
                String params[] = url.substring(13).split("#");
                presenter.onLinkClick(params);
                return false;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        public void onPageFinished(WebView view, String url) {
            presenter.onPageLoadFinished(url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            InputStream stream = inputStreamForAndroidResource(url);
            if (stream != null) {
                return new WebResourceResponse(null, null, stream);
            }
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                InputStream stream = inputStreamForAndroidResource(request.getUrl().toString());
                if (stream != null) {
                    return new WebResourceResponse(null, null, stream);
                }
            }
            return super.shouldInterceptRequest(view, request);
        }

        private InputStream inputStreamForAndroidResource(String url) {
            if (url.startsWith(URL_ROOT_ASSET_FOLDER)) {
                url = url.replaceFirst(URL_ROOT_ASSET_FOLDER, "");
                try {
                    AssetManager assets = activity.getAssets();
                    Uri uri = Uri.parse(url);
                    return assets.open(uri.getPath(), AssetManager.ACCESS_STREAMING);
                } catch (IOException ignored) {
                }
            }
            return null;
        }
    }
}
