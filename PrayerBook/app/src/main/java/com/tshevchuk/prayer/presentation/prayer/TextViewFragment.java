package com.tshevchuk.prayer.presentation.prayer;

import android.os.Bundle;
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
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

import org.parceler.Parcels;

import javax.inject.Inject;

public class TextViewFragment extends TextFragmentBase implements
		LoaderManager.LoaderCallbacks<CharSequence> {
	private final static int LOADER_ID_LOAD_PRAYER = 1;
	@Inject
	TextViewPresenter presenter;
	private CharSequence htmlContent;
	private Integer firstVisibleCharacterOffset = null;
	private TextView tvContent;
	private NestedScrollView svScroll;

	public static TextViewFragment getInstance() {
		return new TextViewFragment();
	}

	@Override
	protected BasePresenter getPresenter() {
		return presenter;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
			//TODO: implement
//			params.putString(PrayerLoader.PARAM_ASSET_FILE_NAME,
//					listItem.getFileName());
//			params.putBoolean(PrayerLoader.PARAM_IS_HTML,
//					listItem.getType() == Type.HtmlInTextView);
//			getLoaderManager().initLoader(LOADER_ID_LOAD_PRAYER, params, this);
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
	public boolean hasContentWithSameId(int itemId) {
		MenuItemPrayer p1 = Parcels.unwrap(getArguments().getParcelable("listItem"));
		return p1 != null && p1.getId() == itemId;
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
		//todo: implement
		return null;
	}
}
