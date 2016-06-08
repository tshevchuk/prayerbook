package com.tshevchuk.prayer.adapters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tshevchuk.prayer.PreferenceManager;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.catholic.R;
import com.tshevchuk.prayer.data.CalendarDay;
import com.tshevchuk.prayer.data.CerkovnyyCalendar;

public class CerkovnyyCalendarListAdapter extends BaseAdapter {
	public final static String MONTHES[] = { "Січень", "Лютий", "Березень",
			"Квітень", "Травень", "Червень", "Липень", "Серпень", "Вересень",
			"Жовтень", "Листопад", "Грудень" };

	private CerkovnyyCalendar cerkovnyyCalendar = CerkovnyyCalendar
			.getInstance();
	private Calendar calendar;
	private int year;
	private int daysCount;
	private int currentYear;
	private int currentDayOfYear;
	private LayoutInflater inflater;
	private SimpleDateFormat dayDateFormat;
	private SimpleDateFormat dayOldStyleFormat;

	public CerkovnyyCalendarListAdapter(Context context, int year) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dayDateFormat = new SimpleDateFormat("d EE", Utils.getUkrainianLocale());
		dayOldStyleFormat = new SimpleDateFormat("d MMM",
				Utils.getUkrainianLocale());
		calendar = Calendar.getInstance();
		currentYear = calendar.get(Calendar.YEAR);
		currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.YEAR, year);
		this.year = year;
	}

	@Override
	public int getCount() {
		if (daysCount == 0) {
			daysCount = new GregorianCalendar().isLeapYear(year) ? 366 : 365;
		}
		return daysCount;
	}

	@Override
	public CalendarDay getItem(int position) {
		calendar.set(Calendar.DAY_OF_YEAR, position + 1);
		return cerkovnyyCalendar.getCalendarDay(calendar.getTime());
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = inflater.inflate(R.layout.f_cerkovnyy_calendar_item, parent,
					false);
			ViewHolder vh = new ViewHolder();

			vh.tvDay = (TextView) v.findViewById(R.id.tvDay);
			vh.tvDayOldStyle = (TextView) v.findViewById(R.id.tvDayOldStyle);
			vh.tvDescription = (TextView) v.findViewById(R.id.tvDescription);
			vh.vMonthSeparator = v.findViewById(R.id.vMonthSeparator);

			vh.tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,
					PreferenceManager.getInstance().getFontSizeSp());
			vh.tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, PreferenceManager
					.getInstance().getFontSizeSp());
			v.setTag(vh);
		}
		ViewHolder vh = (ViewHolder) v.getTag();
		CalendarDay day = getItem(position);
		CharSequence dayDate = dayDateFormat.format(day.getDay());
		if (day.isDateRed()) {
			dayDate = Html.fromHtml("<font color=\"red\">" + dayDate
					+ "</font>");
		}
		vh.tvDay.setText(dayDate);
		vh.tvDayOldStyle.setText(dayOldStyleFormat.format(day.getDayJulian()));
		vh.tvDescription
				.setText(Html.fromHtml(day.getDescription().toString()));
		Calendar cal = Calendar.getInstance();
		cal.setTime(day.getDay());
		if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
			vh.vMonthSeparator.setVisibility(View.VISIBLE);
		} else {
			vh.vMonthSeparator.setVisibility(View.GONE);
		}
		if (year == currentYear
				&& cal.get(Calendar.DAY_OF_YEAR) == currentDayOfYear) {
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
