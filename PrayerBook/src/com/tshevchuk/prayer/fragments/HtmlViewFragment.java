package com.tshevchuk.prayer.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tshevchuk.prayer.HomeActivity;
import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.PreferenceManager;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.MenuItemBase;
import com.tshevchuk.prayer.data.MenuItemPrayer;

public class HtmlViewFragment extends FragmentBase {
	private MenuItemPrayer prayer;
	private HomeActivity activity;

	private WebView wvContent;

	public static HtmlViewFragment getInstance(MenuItemPrayer prayer) {
		HtmlViewFragment f = new HtmlViewFragment();
		Bundle b = new Bundle();
		b.putSerializable("prayer", prayer);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (HomeActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		prayer = (MenuItemPrayer) getArguments().getSerializable("prayer");
	};

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_html_view, container, false);
		wvContent = (WebView) v.findViewById(R.id.wv_content);

		WebSettings settings = wvContent.getSettings();
		settings.setDefaultTextEncodingName("utf-8");
		settings.setDefaultFontSize(PreferenceManager.getInstance()
				.getFontSizeSp());
		settings.setJavaScriptEnabled(true);

		wvContent.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				activity.setProgressBarIndeterminateVisibility(newProgress < 100);
			}
		});
		wvContent.setWebViewClient(new WebViewClient() {
			private String anchor = prayer.getHtmlLinkAnchor();

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("prayerbook://")) {
					String params[] = url.substring(13).split("#");
					int id = Integer.valueOf(params[0]);
					MenuItemBase mi = PrayerBookApplication.getInstance()
							.getCatalog().getMenuItemById(id);
					if (mi instanceof MenuItemPrayer) {
						if (params.length == 2 && !TextUtils.isEmpty(params[1])) {
							((MenuItemPrayer) mi).setHtmlLinkAnchor(params[1]);
						}
					}
					activity.displayMenuItem(mi);
					return false;
				}
				return super.shouldOverrideUrlLoading(view, url);
			}

			public void onPageFinished(WebView view, String url) {
				if (!TextUtils.isEmpty(this.anchor)) {
					StringBuilder sb = new StringBuilder();
					sb.append("javascript:function prayerbook_scrollToElement(id) {")
							.append("var elem = document.getElementById(id);")
							.append("var x = 0; var y = 0;")
							.append("while (elem != null) {")
							//.append("  x += elem.offsetLeft;")
							.append("  y += elem.offsetTop;")
							.append("  elem = elem.offsetParent;  }")
							.append("window.scrollTo(x, y);  };")
							.append(" console.log ( '#someButton was clicked' );")
							.append("prayerbook_scrollToElement('").append(this.anchor)
							.append("');")
							;

					view.loadUrl(sb.toString());
					this.anchor = null;
				}
			}
		});

		activity.setProgressBarIndeterminateVisibility(true);
		wvContent.loadUrl("file:///android_asset/" + prayer.getFileName());
		return v;
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
		getActivity().getActionBar().setTitle(prayer.getName());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar_textviewfragment, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mi_about_prayer:
			((HomeActivity) activity).displayFragment(
					AboutPrayerFragment.getInstance(prayer), 0, null);
			((HomeActivity) activity).sendAnalyticsOptionsMenuEvent("Опис",
					String.format("#%d %s", prayer.getId(), prayer.getName()));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean goBack() {
		if (wvContent.canGoBack()) {
			wvContent.goBack();
			return true;
		}
		return false;
	}

	@Override
	public boolean isSameScreen(Fragment f) {
		if (getClass().equals(f.getClass())) {
			MenuItemPrayer p1 = (MenuItemPrayer) getArguments()
					.getSerializable("prayer");
			MenuItemPrayer p2 = (MenuItemPrayer) f.getArguments()
					.getSerializable("prayer");
			return p1.getId() == p2.getId();
		}
		return false;
	}
}
