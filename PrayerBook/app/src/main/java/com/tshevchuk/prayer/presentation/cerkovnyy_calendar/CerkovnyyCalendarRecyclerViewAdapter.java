package com.tshevchuk.prayer.presentation.cerkovnyy_calendar;

import android.app.UiModeManager;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
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
import com.tshevchuk.prayer.presentation.common.CalendarUtils;

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

    CerkovnyyCalendarRecyclerViewAdapter(Context context,
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
        CalendarUtils.showPistTypeOnImageView(vh.ivPistIcon, day, uiModeManager);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return calendarDays.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        final TextView tvDay;
        final TextView tvDescription;
        final ImageView ivPistIcon;
        final View vMonthSeparator;

        DayViewHolder(View v) {
            super(v);
            tvDay = v.findViewById(R.id.tvDay);
            tvDescription = v.findViewById(R.id.tvDescription);
            vMonthSeparator = v.findViewById(R.id.vMonthSeparator);
            ivPistIcon = v.findViewById(R.id.ivIconPist);
        }
    }
}
