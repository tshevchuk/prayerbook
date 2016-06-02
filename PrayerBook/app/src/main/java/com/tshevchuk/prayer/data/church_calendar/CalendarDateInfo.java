package com.tshevchuk.prayer.data.church_calendar;

import java.util.Date;

public class CalendarDateInfo {
	public final static String PIST_PIST = "pist";
	public final static String PIST_STROHYY = "strohyjPist";
	public final static String PIST_ZAHALNYTSYA = "zahalnytsya";

	private final String person;
	private final String day;
	private final boolean isDateRed;
	private final Date date;
	private final Date dateJulian;
	private final int shiftDaysFromEaster;
	private final String pistType;
	private final String pistName;
	private String dayDescription;

	public CalendarDateInfo(Date date, Date dateJulian,
							String day, String person, boolean isDateRed, int shiftDaysFromEaster, String pistType, String pistName) {
		this.date = date;
		this.dateJulian = dateJulian;
		this.day = day;
		this.person = person;
		this.isDateRed = isDateRed;
		this.shiftDaysFromEaster = shiftDaysFromEaster;
		this.pistType = pistType;
		this.pistName = pistName;
	}

	public boolean isDateRed() {
		return isDateRed;
	}

	public Date getDate() {
		return date;
	}

	public Date getDateJulian() {
		return dateJulian;
	}

	public String getPerson() {
		return person;
	}

	public String getDay() {
		return day;
	}

	public String getPistName() {
		return pistName;
	}

	@Override
	public String toString() {
		return "CalendarDateInfo{" +
				"person='" + person + '\'' +
				", day='" + day + '\'' +
				", isDateRed=" + isDateRed +
				", date=" + date +
				", dateJulian=" + dateJulian +
				", shiftDaysFromEaster=" + shiftDaysFromEaster +
				", pistType='" + pistType + '\'' +
				", pistName='" + pistName + '\'' +
				'}';
	}

	public String getDayDescription() {
		return dayDescription;
	}

	public void setDayDescription(String dayDescription) {
		this.dayDescription = dayDescription;
	}

	public int getShiftDaysFromEaster() {
		return shiftDaysFromEaster;
	}

	public String getPistType() {
		return pistType;
	}
}
