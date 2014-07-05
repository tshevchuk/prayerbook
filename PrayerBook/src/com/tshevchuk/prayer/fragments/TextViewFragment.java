package com.tshevchuk.prayer.fragments;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tshevchuk.prayer.HomeActivity;
import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.PrayerLoader;
import com.tshevchuk.prayer.PreferenceManager;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.ResponsiveScrollView;
import com.tshevchuk.prayer.ResponsiveScrollView.OnEndScrollListener;
import com.tshevchuk.prayer.UIUtils;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.data.CalendarDay;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.CerkovnyyCalendar;
import com.tshevchuk.prayer.data.MenuItemBase;
import com.tshevchuk.prayer.data.MenuItemPrayer;
import com.tshevchuk.prayer.data.MenuItemPrayer.Type;

public class TextViewFragment extends FragmentBase implements
		LoaderCallbacks<CharSequence> {
	private final static int LOADER_ID_LOAD_PRAYER = 1;

	private MenuItemPrayer prayer;
	private CharSequence htmlContent;

	private TextView tvContent;
	private HomeActivity activity;
	private LinearLayout llToday;
	private TextView tvDay;
	private TextView tvDescription;

	public static TextViewFragment getInstance(MenuItemPrayer prayer) {
		TextViewFragment f = new TextViewFragment();
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
		Bundle params = new Bundle();
		params.putString(PrayerLoader.PARAM_ASSET_FILE_NAME,
				prayer.getFileName());
		params.putBoolean(PrayerLoader.PARAM_IS_HTML,
				prayer.getType() == Type.HtmlInTextView);
		getLoaderManager().initLoader(LOADER_ID_LOAD_PRAYER, params, this);
		activity.setProgressBarIndeterminateVisibility(true);
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_text_view, container, false);

		tvContent = (TextView) v.findViewById(R.id.tv_content);
		llToday = (LinearLayout) v.findViewById(R.id.ll_today);
		tvDay = (TextView) v.findViewById(R.id.tvDay);
		tvDescription = (TextView) v.findViewById(R.id.tvDescription);

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

					ActionBar ab = activity.getActionBar();

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
								- activity.getActionBar().getHeight()) {
							activity.getActionBar().show();
						}
					}
					return false;
				}
			});
		}

		llToday.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.displayMenuItem(PrayerBookApplication.getInstance()
						.getCatalog().getMenuItemById(Catalog.ID_CALENDAR));
			}
		});

		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		activity.getActionBar().setTitle(prayer.getFullName());
		int fontSizeSp = PreferenceManager.getInstance().getFontSizeSp();
		tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeSp);

		if (PreferenceManager.getInstance().isShowTodayCalendarEnabled()
				&& prayer.getId() == Catalog.ID_SCHODENNI_MOLYTVY) {
			llToday.setVisibility(View.VISIBLE);
			CerkovnyyCalendar cal = CerkovnyyCalendar.getInstance();
			CalendarDay day = cal.getCalendarDay(new Date());
			String d = new SimpleDateFormat("d EE", Utils.getUkrainianLocale())
					.format(day.getDay());
			if (day.isDateRed()) {
				d = "<font color=\"red\">" + d + "</font>";
			}
			tvDay.setText(Html.fromHtml(d));
			tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeSp);
			tvDescription.setText(Html
					.fromHtml(day.getDescription().toString()));
			tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeSp);

		} else {
			llToday.setVisibility(View.GONE);
		}
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

	@Override
	public Loader<CharSequence> onCreateLoader(int id, Bundle args) {
		return new PrayerLoader(activity, args);
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
			activity.setProgressBarIndeterminateVisibility(false);
		}
	}

	@Override
	protected MenuItemBase getMenuItem() {
		return prayer;
	}
}
