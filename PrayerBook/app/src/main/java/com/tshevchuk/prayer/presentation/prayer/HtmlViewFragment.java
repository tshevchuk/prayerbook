package com.tshevchuk.prayer.presentation.prayer;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer.Type;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HtmlViewFragment extends TextFragmentBase {
	private final List<MenuItemPrayer> prayers = new ArrayList<>();
	@Inject
	HtmlViewPresenter presenter;
	private WebView wvContent;

	public static HtmlViewFragment getInstance() {
		HtmlViewFragment f = new HtmlViewFragment();
		Bundle b = new Bundle();
		//TODO: implement
//		b.putParcelable("prayer", Parcels.wrap(prayer));
		f.setArguments(b);
		return f;
	}

	@Override
	protected BasePresenter getPresenter() {
		return presenter;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		((PrayerBookApplication) getActivity().getApplication())
				.getViewComponent().inject(this);
		prayers.add((MenuItemPrayer) Parcels.unwrap(getArguments().getParcelable("prayer")));
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		WebSettings settings = wvContent.getSettings();
		settings.setDefaultFontSize(preferenceManager.getFontSizeSp());
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
						MenuItemBase mi = catalog.getMenuItemById(id);
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
							//todo: implement
							//activity.displayMenuItem(mi);
							//todo: add update of recently used
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

					ActionBar actionBar = activity.getSupportActionBar();
					if (actionBar != null) {
						actionBar.setTitle(getMenuItem().getName());
					}

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
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						InputStream stream = inputStreamForAndroidResource(request.getUrl().toString());
						if (stream != null) {
							return new WebResourceResponse(null, null, stream);
						}
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
	public boolean hasContentWithSameId(int itemId) {
		MenuItemPrayer p1 = getMenuItem();
		if (p1 == null) {
			p1 = Parcels.unwrap(getArguments().getParcelable("prayer"));
		}
		return p1 != null && p1.getId() == itemId;
	}

	@Override
	public MenuItemPrayer getMenuItem() {
		return prayers.get(prayers.size() - 1);
	}
}
