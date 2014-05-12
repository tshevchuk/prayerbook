package com.tshevchuk.prayer.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tshevchuk.prayer.HomeActivity;
import com.tshevchuk.prayer.PrayerLoader;
import com.tshevchuk.prayer.PreferenceManager;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.ResponsiveScrollView;
import com.tshevchuk.prayer.ResponsiveScrollView.OnEndScrollListener;
import com.tshevchuk.prayer.UIUtils;
import com.tshevchuk.prayer.data.Catalog.Prayer;

public class TextViewFragment extends FragmentBase implements
		LoaderCallbacks<CharSequence> {
	private final static int LOADER_ID_LOAD_PRAYER = 1;

	private Prayer prayer;
	private CharSequence htmlContent;

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
		Bundle params = new Bundle();
		params.putString(PrayerLoader.PARAM_ASSET_FILE_NAME,
				prayer.getFileName());
		params.putBoolean(PrayerLoader.PARAM_IS_HTML, prayer.isHtml());
		getLoaderManager().initLoader(LOADER_ID_LOAD_PRAYER, params, this);
		getActivity().setProgressBarIndeterminateVisibility(true);
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_text_view, container, false);
		tvContent = (TextView) v.findViewById(R.id.tv_content);
		if (!getResources().getBoolean(R.bool.has_sw480)) {
			final ResponsiveScrollView svScroll = (ResponsiveScrollView) v
					.findViewById(R.id.svScroll);
			svScroll.setOnEndScrollListener(new OnEndScrollListener() {
				@Override
				public void onEndScroll(boolean moveContentUp, boolean isFling,
						int dy) {
					boolean show = false;
					boolean hide = false;

					if (svScroll.getScrollY() < UIUtils.dpToPx(80))
						show = true;
					if (!moveContentUp && dy < -UIUtils.dpToPx(30))
						show = true;
					if (!show && moveContentUp && isFling)
						hide = true;

					ActionBar ab = getActivity().getActionBar();

					if (show && !ab.isShowing())
						ab.show();
					else if (hide && ab.isShowing())
						ab.hide();
				}
			});

			svScroll.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
						// is bottom
						View view = svScroll.getChildAt(svScroll
								.getChildCount() - 1);
						if ((svScroll.getHeight() + svScroll.getScrollY()) >= view
								.getBottom()
								- getActivity().getActionBar().getHeight()) {
							getActivity().getActionBar().show();
						}
					}
					return false;
				}
			});
		}

		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(prayer.getFullName());
		tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, PreferenceManager
				.getInstance().getFontSizeSp());
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
			((HomeActivity) getActivity())
					.sendAnalyticsOptionsMenuEvent("Опис");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean isSameScreen(Fragment f) {
		if (getClass().equals(f.getClass())) {
			Prayer p1 = (Prayer) getArguments().getSerializable("prayer");
			Prayer p2 = (Prayer) f.getArguments().getSerializable("prayer");
			return p1.getFileName().equals(p2.getFileName());
		}
		return false;
	}

	@Override
	public Loader<CharSequence> onCreateLoader(int id, Bundle args) {
		return new PrayerLoader(getActivity(), args);
	}

	@Override
	public void onLoadFinished(Loader<CharSequence> loader, CharSequence data) {
		htmlContent = data;
		updateHtmlContent();
	}

	@Override
	public void onLoaderReset(Loader<CharSequence> loader) {
	}

	private void updateHtmlContent() {
		if (htmlContent != null && tvContent != null) {
			tvContent.setText(htmlContent);
			getActivity().setProgressBarIndeterminateVisibility(false);
		}
	}
}
