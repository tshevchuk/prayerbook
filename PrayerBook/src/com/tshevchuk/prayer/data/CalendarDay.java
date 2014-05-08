package com.tshevchuk.prayer.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarDay {
	public enum PistType {
		Normal, Pist, StrohyyPist, Zahalnytsya
	}

	private final CharSequence description;
	private final PistType pistType;
	private final boolean isDateRed;
	private final Date day;
	
	public CalendarDay(Date day, PistType pistType, CharSequence description, boolean isDateRed){
		this.day = day;
		this.pistType = pistType;
		this.description = description;
		this.isDateRed = isDateRed;
	}

	public CharSequence getDescription() {
		return description;
	}

	public PistType getPistType() {
		return pistType;
	}

	public boolean isDateRed() {
		return isDateRed;
	}

	public Date getDay() {
		return day;
	}

	@Override
	public String toString() {
		java.text.DateFormat df = SimpleDateFormat.getDateInstance(SimpleDateFormat.FULL);
		String dayStr = df.format(day);
		if(isDateRed){
			dayStr = "<font color=\"red\">" + dayStr + "</font>";
		}
		return dayStr + "\n" + description;
	}
	
	
}
