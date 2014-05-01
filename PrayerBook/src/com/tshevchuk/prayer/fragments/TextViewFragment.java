package com.tshevchuk.prayer.fragments;

import java.io.IOException;

import com.tshevchuk.prayer.HomeActivity;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.R.id;
import com.tshevchuk.prayer.R.layout;
import com.tshevchuk.prayer.data.Catalog.Prayer;
import com.tshevchuk.prayer.data.Catalog.SubMenu;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextViewFragment extends FragmentBase {
	private Prayer prayer;

	private TextView tvContent;

	public static TextViewFragment getInstance(Prayer prayer) {
		TextViewFragment f = new TextViewFragment();
		Bundle b = new Bundle();
		b.putSerializable("prayer", prayer);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		prayer = (Prayer) getArguments().getSerializable("prayer");
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_text_view, container, false);
		tvContent = (TextView) v.findViewById(R.id.tv_content);
		try {
			String html = Utils.getAssetAsString(prayer.getFileName());
			tvContent.setText(Html.fromHtml(html));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
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
			((HomeActivity) getActivity()).displayFragment(
					AboutPrayerFragment.getInstance(prayer), null);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean isSameScreen(FragmentBase f) {
		if (getClass().equals(f.getClass())) {
			Prayer p1 = (Prayer) getArguments().getSerializable("prayer");
			Prayer p2 = (Prayer) f.getArguments().getSerializable("prayer");
			return p1.getFileName().equals(p2.getFileName());
		}
		return false;
	}
}
