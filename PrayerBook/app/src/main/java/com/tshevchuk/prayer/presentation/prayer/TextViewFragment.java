package com.tshevchuk.prayer.presentation.prayer;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

import javax.inject.Inject;

public class TextViewFragment extends TextFragmentBase implements TextViewView {
    @Inject
    TextViewPresenter presenter;
    private Integer firstVisibleCharacterOffset = null;
    private TextView tvContent;
    private NestedScrollView svScroll;
    private MenuItemPrayer prayer;

    public static TextViewFragment getInstance(int id) {
        TextViewFragment f = new TextViewFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        f.setArguments(args);
        return f;
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            firstVisibleCharacterOffset = savedInstanceState.getInt("firstVisibleCharOffset");
        }
        ((PrayerBookApplication) getActivity().getApplication()).getViewComponent().inject(this);
        presenter.setId(getArguments().getInt("id"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_text_view, container, false);

        tvContent = (TextView) v.findViewById(R.id.tv_content);
        svScroll = (NestedScrollView) v.findViewById(R.id.svScroll);

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
            outState.putInt("firstVisibleCharOffset", getFirstVisibleCharacterOffset());
        } else {
            outState.putInt("firstVisibleCharOffset", firstVisibleCharacterOffset);
        }
    }

    private int getFirstVisibleCharacterOffset() {
        final int firstVisibleLineOffset = tvContent.getLayout()
                .getLineForVertical(svScroll.getScrollY());
        return tvContent.getLayout().getLineStart(firstVisibleLineOffset);
    }

    @Override
    public boolean hasContentWithSameId(int itemId) {
        //todo: implement
        return false;
    }

    @Override
    public MenuItemPrayer getMenuItem() {
        return prayer;
    }

    @Override
    public void setMenuItem(MenuItemPrayer prayer) {
        this.prayer = prayer;
    }

    @Override
    public void showProgress() {
        activity.setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void setPrayerText(CharSequence text) {
        tvContent.setText(text);
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

    @Override
    public void hideProgress() {
        activity.setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showError(String msg) {
        View v = getView();
        if (v == null) {
            return;
        }
        Snackbar.make(v, msg, Snackbar.LENGTH_LONG).show();
    }
}
