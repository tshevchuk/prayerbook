package com.tshevchuk.prayer.presentation.prayer_text_view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.presentation.audio.AudioControlIconButton;
import com.tshevchuk.prayer.presentation.audio.AudioPlayerService;
import com.tshevchuk.prayer.presentation.common.FragmentBase;

import javax.inject.Inject;

public class TextViewFragment extends FragmentBase implements TextViewView {
    @SuppressWarnings("WeakerAccess")
    @Inject
    TextViewPresenter presenter;
    private Integer firstVisibleCharacterOffset = null;
    private TextView tvContent;
    private NestedScrollView svScroll;
    private MenuItemPrayer prayer;
    private ProgressBar pbLoading;

    public static TextViewFragment getInstance(int id) {
        TextViewFragment f = new TextViewFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        f.setArguments(args);
        return f;
    }

    @Override
    protected String getScreenTitle() {
        return prayer.getName();
    }

    @Override
    public TextViewPresenter getPresenter() {
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_text_view, container, false);

        tvContent = v.findViewById(R.id.tv_content);
        svScroll = v.findViewById(R.id.svScroll);
        pbLoading = v.findViewById(R.id.pbLoading);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        firstVisibleCharacterOffset = getFirstVisibleCharacterOffset();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.actionbar_create_shortcut, menu);
        inflater.inflate(R.menu.actionbar_textviewfragment, menu);
        if(!TextUtils.isEmpty(prayer.getAudioUrl())) {
            inflater.inflate(R.menu.actionbar_play_audio, menu);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem menuItemAudioControl = menu.findItem(R.id.mi_audio_control);
        if(menuItemAudioControl != null){
            AudioControlIconButton audioControlIconButton
                    = (AudioControlIconButton) menuItemAudioControl.getActionView();
            audioControlIconButton.setAudio(prayer.getAudioUrl(), prayer.getAudioStartPosition(),
                    prayer.getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_create_shortcut:
                presenter.onCreateShortcutClick();
                return true;
            case R.id.mi_about_prayer:
                presenter.onOpenAboutClick();
                return true;
            case R.id.mi_audio_restart:
                final Intent intent = new Intent(getContext(), AudioPlayerService.class);
                intent.setAction(AudioPlayerService.ACTION_START);
                intent.setData(Uri.parse(prayer.getAudioUrl()));
                intent.putExtra(AudioPlayerService.PARAM_AUDIO_START_POSITION,
                        prayer.getAudioStartPosition());
                intent.putExtra(AudioPlayerService.PARAM_TITLE, prayer.getName());
                getContext().startService(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int getFirstVisibleCharacterOffset() {
        final int firstVisibleLineOffset = tvContent.getLayout()
                .getLineForVertical(svScroll.getScrollY());
        return tvContent.getLayout().getLineStart(firstVisibleLineOffset);
    }

    @Override
    public void setMenuItem(MenuItemPrayer prayer) {
        this.prayer = prayer;
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
    public void setFontSizeSp(int textFontSizeSp) {
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, textFontSizeSp);
    }

    @Override
    public void setKeepScreenOn(boolean keepScreenOn) {
        tvContent.setKeepScreenOn(keepScreenOn);
    }

    @Override
    public String getErrorReportInfo() {
        return super.getErrorReportInfo() + "; id: " + prayer.getId() + "; url: "
                + prayer.getFileName();
    }

    @Override
    public boolean onUpButtonPress() {
        return presenter.onUpButtonPress();
    }

    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }
}
