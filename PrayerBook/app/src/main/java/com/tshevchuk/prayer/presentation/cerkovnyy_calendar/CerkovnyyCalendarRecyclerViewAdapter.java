package com.tshevchuk.prayer.presentation.cerkovnyy_calendar;

import android.app.UiModeManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CerkovnyyCalendarRecyclerViewAdapter extends RecyclerView.Adapter<CerkovnyyCalendarRecyclerViewAdapter.DayViewHolder> {
    private final ArrayList<CalendarDateInfo> calendarDays;
    private final int todayPosition;
    private final int fontSizeSp;
    private final SimpleDateFormat dayDateFormat;
    private final SimpleDateFormat dayOldStyleFormat;
    private final UiModeManager uiModeManager;

    public CerkovnyyCalendarRecyclerViewAdapter(Context context,
                                                ArrayList<CalendarDateInfo> calendarDays,
                                                int todayPosition, int fontSizeSp) {
        this.calendarDays = calendarDays;
        this.todayPosition = todayPosition;
        this.fontSizeSp = fontSizeSp;
        dayDateFormat = new SimpleDateFormat("d EE", Utils.getUkrainianLocale());
        dayOldStyleFormat = new SimpleDateFormat("d MMM", Utils.getUkrainianLocale());
        uiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.f_cerkovnyy_calendar_item, parent, false);
        DayViewHolder vh = new DayViewHolder(v);
        vh.tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeSp);
        vh.tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeSp);
        return vh;
    }

    @Override
    public void onBindViewHolder(DayViewHolder vh, int position) {
        CalendarDateInfo day = calendarDays.get(position);
        CharSequence dayDate = dayDateFormat.format(day.getDate());
        if (day.isDateRed()) {
            dayDate = Html.fromHtml("<font color=\"red\">" + dayDate + "</font>");
        }
        vh.tvDay.setText(dayDate);
        vh.tvDayOldStyle.setText(dayOldStyleFormat.format(day.getDateJulian()));
        vh.tvDescription.setText(Html.fromHtml(day.getDayDescription()));
        Calendar cal = Calendar.getInstance();
        cal.setTime(day.getDate());
        if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
            vh.vMonthSeparator.setVisibility(View.VISIBLE);
        } else {
            vh.vMonthSeparator.setVisibility(View.GONE);
        }
        if (position == todayPosition) {
            vh.tvDay.setBackgroundResource(R.drawable.cerkovnyy_calendar_current_day_background);
        } else {
            vh.tvDay.setBackgroundResource(0);
        }
        if (CalendarDateInfo.PIST_PIST.equals(day.getPistType())) {
            vh.ivPistIcon.setImageResource(
                    uiModeManager.getNightMode() == UiModeManager.MODE_NIGHT_YES
                            ? R.drawable.ic_pist_fish_white
                            : R.drawable.ic_pist_fish_black);
            vh.ivPistIcon.setBackgroundResource(R.drawable.background_pist_pist);
            vh.ivPistIcon.setVisibility(View.VISIBLE);
        } else if (CalendarDateInfo.PIST_STROHYY.equals(day.getPistType())) {
            vh.ivPistIcon.setImageResource(R.drawable.ic_pist_fish_red);
            vh.ivPistIcon.setBackgroundResource(R.drawable.background_pist_pist);
            vh.ivPistIcon.setVisibility(View.VISIBLE);
        } else if (CalendarDateInfo.PIST_ZAHALNYTSYA.equals(day.getPistType())) {
            vh.ivPistIcon.setImageResource(0);
            vh.ivPistIcon.setBackgroundResource(R.drawable.background_pist_zahalnytsya);
            vh.ivPistIcon.setVisibility(View.VISIBLE);
        } else {
            vh.ivPistIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return calendarDays.size();
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay;
        TextView tvDayOldStyle;
        TextView tvDescription;
        ImageView ivPistIcon;
        View vMonthSeparator;

        public DayViewHolder(View v) {
            super(v);
            tvDay = (TextView) v.findViewById(R.id.tvDay);
            tvDayOldStyle = (TextView) v.findViewById(R.id.tvDayOldStyle);
            tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            vMonthSeparator = v.findViewById(R.id.vMonthSeparator);
            ivPistIcon = (ImageView) v.findViewById(R.id.ivIconPist);
        }
    }
}
