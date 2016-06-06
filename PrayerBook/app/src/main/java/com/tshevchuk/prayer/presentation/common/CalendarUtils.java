package com.tshevchuk.prayer.presentation.common;

import android.app.UiModeManager;
import android.view.View;
import android.widget.ImageView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;

/**
 * Created by taras on 05.06.16.
 */
public class CalendarUtils {

    public static void showPistTypeOnImageView(ImageView ivPistIcon, CalendarDateInfo day,
                                               UiModeManager uiModeManager) {
        if (CalendarDateInfo.PIST_PIST.equals(day.getPistType())) {
            ivPistIcon.setImageResource(
                    uiModeManager.getNightMode() == UiModeManager.MODE_NIGHT_YES
                            ? R.drawable.ic_pist_fish_white
                            : R.drawable.ic_pist_fish_black);
            ivPistIcon.setBackgroundResource(R.drawable.background_pist_pist);
            ivPistIcon.setVisibility(View.VISIBLE);
        } else if (CalendarDateInfo.PIST_STROHYY.equals(day.getPistType())) {
            ivPistIcon.setImageResource(R.drawable.ic_pist_fish_red);
            ivPistIcon.setBackgroundResource(R.drawable.background_pist_pist);
            ivPistIcon.setVisibility(View.VISIBLE);
        } else if (CalendarDateInfo.PIST_ZAHALNYTSYA.equals(day.getPistType())) {
            ivPistIcon.setImageResource(0);
            ivPistIcon.setBackgroundResource(R.drawable.background_pist_zahalnytsya);
            ivPistIcon.setVisibility(View.VISIBLE);
        } else {
            ivPistIcon.setVisibility(View.GONE);
        }
    }
}
