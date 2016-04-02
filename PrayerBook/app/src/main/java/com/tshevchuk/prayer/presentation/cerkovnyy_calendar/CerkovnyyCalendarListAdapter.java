package com.tshevchuk.prayer.presentation.cerkovnyy_calendar;

import android.content.Context;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.model.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CerkovnyyCalendarListAdapter extends BaseAdapter {
    private final ArrayList<CalendarDay> calendarDays;
    private final int todayPosition;
    private final int fontSizeSp;
    private final LayoutInflater inflater;
    private final SimpleDateFormat dayDateFormat;
    private final SimpleDateFormat dayOldStyleFormat;

    public CerkovnyyCalendarListAdapter(Context context,
                                        ArrayList<CalendarDay> calendarDays,
                                        int todayPosition, int fontSizeSp) {
        this.calendarDays = calendarDays;
        this.todayPosition = todayPosition;
        this.fontSizeSp = fontSizeSp;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dayDateFormat = new SimpleDateFormat("d EE", Utils.getUkrainianLocale());
        dayOldStyleFormat = new SimpleDateFormat("d MMM", Utils.getUkrainianLocale());
    }

    @Override
    public int getCount() {
        return calendarDays.size();
    }

    @Override
    public CalendarDay getItem(int position) {
        return calendarDays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = inflater.inflate(R.layout.f_cerkovnyy_calendar_item, parent, false);
            ViewHolder vh = new ViewHolder();

            vh.tvDay = (TextView) v.findViewById(R.id.tvDay);
            vh.tvDayOldStyle = (TextView) v.findViewById(R.id.tvDayOldStyle);
            vh.tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            vh.vMonthSeparator = v.findViewById(R.id.vMonthSeparator);

            vh.tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeSp);
            vh.tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeSp);
            v.setTag(vh);
        }
        ViewHolder vh = (ViewHolder) v.getTag();
        CalendarDay day = getItem(position);
        CharSequence dayDate = dayDateFormat.format(day.getDay());
        if (day.isDateRed()) {
            dayDate = Html.fromHtml("<font color=\"red\">" + dayDate + "</font>");
        }
        vh.tvDay.setText(dayDate);
        vh.tvDayOldStyle.setText(dayOldStyleFormat.format(day.getDayJulian()));
        vh.tvDescription.setText(Html.fromHtml(day.getDescription().toString()));
        Calendar cal = Calendar.getInstance();
        cal.setTime(day.getDay());
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
        return v;
    }

    private static class ViewHolder {
        TextView tvDay;
        TextView tvDayOldStyle;
        TextView tvDescription;
        View vMonthSeparator;
    }
}
