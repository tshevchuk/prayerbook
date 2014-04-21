package com.tshevchuk.prayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class HtmlViewFragment extends FragmentBase {
	private String title;
	private String assetFileName;

	private WebView wvContent;

	public static HtmlViewFragment getInstance(String title,
			String assetFileName) {
		HtmlViewFragment f = new HtmlViewFragment();
		Bundle b = new Bundle();
		b.putString("title", title);
		b.putString("assetFileName", assetFileName);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		title = getArguments().getString("title");
		assetFileName = getArguments().getString("assetFileName");
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_html_view, container, false);
		wvContent = (WebView) v.findViewById(R.id.wv_content);
		wvContent.loadUrl("file:///android_asset/" + assetFileName);
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
		getActivity().getActionBar().setTitle(title);
	}

	@Override
	public boolean isSameScreen(FragmentBase f) {
		return getClass().equals(f.getClass())
				&& ((HtmlViewFragment) f).getArguments()
						.getString("assetFileName")
						.equals(getArguments().getString("assetFileName"));
	}
}
