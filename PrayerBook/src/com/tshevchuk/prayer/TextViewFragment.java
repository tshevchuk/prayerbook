package com.tshevchuk.prayer;

import java.io.IOException;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextViewFragment extends FragmentBase {
	private String title;
	private String assetFileName;

	private TextView tvContent;

	public static TextViewFragment getInstance(String title,
			String assetFileName) {
		TextViewFragment f = new TextViewFragment();
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
		View v = inflater.inflate(R.layout.f_text_view, container, false);
		tvContent = (TextView) v.findViewById(R.id.tv_content);
		try {
			String html = Utils.getAssetAsString(assetFileName);
			tvContent.setText(Html.fromHtml(html));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(title);
	}

	@Override
	public boolean isSameScreen(FragmentBase f) {
		return getClass().equals(f.getClass())
				&& ((TextViewFragment) f).getArguments()
						.getString("assetFileName")
						.equals(getArguments().getString("assetFileName"));
	}
}
