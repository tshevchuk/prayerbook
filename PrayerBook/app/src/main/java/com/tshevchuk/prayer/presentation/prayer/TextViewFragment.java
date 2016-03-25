package com.tshevchuk.prayer.presentation.prayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.PrayerLoader;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer.Type;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

import org.parceler.Parcels;

import javax.inject.Inject;

public class TextViewFragment extends TextFragmentBase implements
		LoaderManager.LoaderCallbacks<CharSequence> {
	private final static int LOADER_ID_LOAD_PRAYER = 1;
	@Inject
	TextViewPresenter presenter;
	private MenuItemPrayer prayer;
	private CharSequence htmlContent;
	private Integer firstVisibleCharacterOffset = null;
	private TextView tvContent;
	private NestedScrollView svScroll;

	public static TextViewFragment getInstance(MenuItemPrayer prayer) {
		TextViewFragment f = new TextViewFragment();
		Bundle b = new Bundle();
		b.putParcelable("prayer", Parcels.wrap(prayer));
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
		prayer = Parcels.unwrap(getArguments().getParcelable("prayer"));
		if (savedInstanceState != null) {
			firstVisibleCharacterOffset = savedInstanceState
					.getInt("firstVisibleCharOffset");
		}
		((PrayerBookApplication) getActivity().getApplication())
				.getViewComponent().inject(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_text_view, container, false);

		tvContent = (TextView) v.findViewById(R.id.tv_content);
		svScroll = (NestedScrollView) v.findViewById(R.id.svScroll);

		activity.setProgressBarIndeterminateVisibility(true);
		if (htmlContent == null) {
			Bundle params = new Bundle();
			params.putString(PrayerLoader.PARAM_ASSET_FILE_NAME,
					prayer.getFileName());
			params.putBoolean(PrayerLoader.PARAM_IS_HTML,
					prayer.getType() == Type.HtmlInTextView);
			getLoaderManager().initLoader(LOADER_ID_LOAD_PRAYER, params, this);
		} else {
			updateHtmlContent();
		}

		return v;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		firstVisibleCharacterOffset = getFirstVisibleCharacterOffset();
	}

	@Override
	public void onResume() {
		super.onResume();
		int fontSizeSp = preferenceManager.getFontSizeSp();
		tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeSp);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (firstVisibleCharacterOffset == null) {
			outState.putInt("firstVisibleCharOffset",
					getFirstVisibleCharacterOffset());
		} else {
			outState.putInt("firstVisibleCharOffset",
					firstVisibleCharacterOffset);
		}
	}

	private int getFirstVisibleCharacterOffset() {
		final int firstVisibleLineOffset = tvContent.getLayout()
				.getLineForVertical(svScroll.getScrollY());
		return tvContent.getLayout()
				.getLineStart(firstVisibleLineOffset);
	}

	@Override
	public boolean isSameScreen(Fragment f) {
		if (getClass().equals(f.getClass())) {
			MenuItemPrayer p1 = (MenuItemPrayer) getArguments()
					.getSerializable("prayer");
			MenuItemPrayer p2 = (MenuItemPrayer) f.getArguments()
					.getSerializable("prayer");
			return p1 != null && p2 != null && p1.getId() == p2.getId();
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

			svScroll.post(new Runnable() {
				public void run() {
					if (firstVisibleCharacterOffset != null) {
						final int firstVisableLineOffset = tvContent
								.getLayout().getLineForOffset(
										firstVisibleCharacterOffset);
						final int pixelOffset = tvContent.getLayout()
								.getLineTop(firstVisableLineOffset);
						svScroll.scrollTo(0, pixelOffset);
						firstVisibleCharacterOffset = null;
					}
				}
			});
		}
	}

	@Override
	public MenuItemPrayer getMenuItem() {
		return prayer;
	}
}
