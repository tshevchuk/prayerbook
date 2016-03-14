package com.tshevchuk.prayer.fragments;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
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

import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.PreferenceManager;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.MenuItemBase;
import com.tshevchuk.prayer.data.MenuItemPrayer;
import com.tshevchuk.prayer.data.MenuItemPrayer.Type;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HtmlViewFragment extends TextFragmentBase {
	private final List<MenuItemPrayer> prayers = new ArrayList<>();

	private WebView wvContent;

	public static HtmlViewFragment getInstance(MenuItemPrayer prayer) {
		HtmlViewFragment f = new HtmlViewFragment();
		Bundle b = new Bundle();
		b.putSerializable("prayer", prayer);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		prayers.add((MenuItemPrayer) getArguments().getSerializable("prayer"));
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		WebSettings settings = wvContent.getSettings();
		settings.setDefaultFontSize(PreferenceManager.getInstance()
				.getFontSizeSp());
	}

	private void loadPrayer(MenuItemPrayer p) {
		String url = "file:///android_asset/" + p.getFileName();
		if (!TextUtils.isEmpty(p.getHtmlLinkAnchor())) {
			url += "#" + p.getHtmlLinkAnchor();
		}
		wvContent.loadUrl(url);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_html_view, container, false);
		FrameLayout flContent = (FrameLayout) v.findViewById(R.id.fl_content);
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
					activity.setProgressBarIndeterminateVisibility(newProgress < 100);
				}
			});

			wvContent.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					if (url.startsWith("prayerbook://")) {
						String params[] = url.substring(13).split("#");
						int id = Integer.valueOf(params[0]);
						MenuItemBase mi = PrayerBookApplication.getInstance()
								.getCatalog().getMenuItemById(id);
						if (mi instanceof MenuItemPrayer) {
							if (params.length == 2
									&& !TextUtils.isEmpty(params[1])) {
								((MenuItemPrayer) mi)
										.setHtmlLinkAnchor(params[1]);
							}
						}
						if (mi instanceof MenuItemPrayer
								&& ((MenuItemPrayer) mi).getType() == Type.HtmlInWebView) {
							prayers.add((MenuItemPrayer) mi);
							loadPrayer((MenuItemPrayer) mi);
						} else {
							activity.displayMenuItem(mi);
						}
						return false;
					}
					return super.shouldOverrideUrlLoading(view, url);
				}

				public void onPageFinished(WebView view, String url) {
					String anchor = null;
					if (url.startsWith("file:///android_asset/")) {
						anchor = Uri.parse(url).getEncodedFragment();
					}
					for (int i = prayers.size() - 1; i >= 0; --i) {
						MenuItemPrayer p = prayers.get(i);
						String u = "file:///android_asset/" + p.getFileName();
						if (url.equals(u) || url.startsWith(u + "#")) {
							while (prayers.size() > i + 1) {
								prayers.remove(prayers.size() - 1);
							}
							break;
						}
					}

					activity.getSupportActionBar().setTitle(
							getMenuItem().getName());

					if (!TextUtils.isEmpty(anchor)) {
						view.loadUrl("javascript:function prayerbook_scrollToElement(id) {"
								+ "var elem = document.getElementById(id);" + "var x = 0; var y = 0;"
								+ "while (elem != null) {" + "  y += elem.offsetTop;"
								+ "  elem = elem.offsetParent;  }" + "window.scrollTo(x, y);  };"
								+ " console.log ( '#someButton was clicked' );"
								+ "prayerbook_scrollToElement('" + anchor + "');");
					}
				}

				@SuppressWarnings("deprecation")
				@Override
				public WebResourceResponse shouldInterceptRequest(WebView view,
						String url) {
					InputStream stream = inputStreamForAndroidResource(url);
					if (stream != null) {
						return new WebResourceResponse(null, null, stream);
					}
					return super.shouldInterceptRequest(view, url);
				}

				@Override
				public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
					InputStream stream = inputStreamForAndroidResource(request.getUrl().toString());
					if (stream != null) {
						return new WebResourceResponse(null, null, stream);
					}
					return super.shouldInterceptRequest(view, request);
				}

				private InputStream inputStreamForAndroidResource(String url) {
					final String ANDROID_ASSET = "file:///android_asset/";

					if (url.startsWith(ANDROID_ASSET)) {
						url = url.replaceFirst(ANDROID_ASSET, "");
						try {
							AssetManager assets = activity.getAssets();
							Uri uri = Uri.parse(url);
							return assets.open(uri.getPath(),
									AssetManager.ACCESS_STREAMING);
						} catch (IOException ignored) {
						}
					}
					return null;
				}
			});

			activity.setProgressBarIndeterminateVisibility(true);
			loadPrayer(getMenuItem());
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
	public boolean goBack() {
		if (wvContent.canGoBack()) {
			if (prayers.size() > 1) {
				prayers.remove(prayers.size() - 1);
			}
			wvContent.goBack();
			return true;
		}
		return false;
	}

	@Override
	public boolean isSameScreen(Fragment f) {
		if (getClass().equals(f.getClass())) {
			MenuItemPrayer p1 = getMenuItem();
			if (p1 == null) {
				p1 = (MenuItemPrayer) getArguments().getSerializable("prayer");
			}
			MenuItemPrayer p2 = (MenuItemPrayer) f.getArguments()
					.getSerializable("prayer");
			return p1.getId() == p2.getId();
		}
		return false;
	}

	@Override
	public MenuItemPrayer getMenuItem() {
		return prayers.get(prayers.size() - 1);
	}
}
