package com.tshevchuk.prayer.domain.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarDay {
	private final CharSequence description;
	private final boolean isDateRed;
	private final Date day;
	private final Date dayJulian;

	public CalendarDay(Date day, Date dayJulian,
			CharSequence description, boolean isDateRed) {
		this.day = day;
		this.dayJulian = dayJulian;
		this.description = description;
		this.isDateRed = isDateRed;
	}

	public CharSequence getDescription() {
		return description;
	}

	public boolean isDateRed() {
		return isDateRed;
	}

	public Date getDay() {
		return day;
	}

	public Date getDayJulian() {
		return dayJulian;
	}

	@Override
	public String toString() {
		java.text.DateFormat df = SimpleDateFormat
				.getDateInstance(SimpleDateFormat.FULL);
		String dayStr = df.format(day);
		if (isDateRed) {
			dayStr = "<font color=\"red\">" + dayStr + "</font>";
		}
		return dayStr + "\n" + description;
	}

}
