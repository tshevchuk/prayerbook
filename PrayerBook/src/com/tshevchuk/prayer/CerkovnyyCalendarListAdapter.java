package com.tshevchuk.prayer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.content.Context;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tshevchuk.prayer.data.CalendarDay;
import com.tshevchuk.prayer.data.CerkovnyyCalendar;

public class CerkovnyyCalendarListAdapter extends BaseAdapter {
	final String MONTHES[] = { "Січень", "Лютий", "Березень", "Квітень",
			"Травень", "Червень", "Липень", "Серпень", "Вересень", "Жовтень",
			"Листопад", "Грудень" };

	private CerkovnyyCalendar calendar = CerkovnyyCalendar.getInstance();
	private LayoutInflater inflater;
	private SimpleDateFormat dayDateFormat;
	private SimpleDateFormat dayOldStyleFormat;

	public CerkovnyyCalendarListAdapter(Context context) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dayDateFormat = new SimpleDateFormat("d EE", Locale.getDefault());
		dayOldStyleFormat = new SimpleDateFormat("d MMM", Locale.getDefault());

	}

	@Override
	public int getCount() {
		return calendar.getTotalDaysCount();
	}

	@Override
	public CalendarDay getItem(int position) {
		return calendar.getCalendarDay(calendar.getDateForDayNumber(position));
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
			vh.tvMonth = (TextView) v.findViewById(R.id.tvMonth);

			vh.tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,
					PreferenceManager.getInstance().getFontSizeSp());
			vh.tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, PreferenceManager
					.getInstance().getFontSizeSp());
			vh.tvMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP,
					PreferenceManager.getInstance().getFontSizeSp() + 2);
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
			vh.tvMonth.setVisibility(View.VISIBLE);
			vh.tvMonth.setText(String.format("%s %s",
					MONTHES[cal.get(Calendar.MONTH)], cal.get(Calendar.YEAR)));
		} else {
			vh.tvMonth.setVisibility(View.GONE);
		}
		return v;
	}

	private static class ViewHolder {
		TextView tvDay;
		TextView tvDayOldStyle;
		TextView tvDescription;
		TextView tvMonth;
	}
}
