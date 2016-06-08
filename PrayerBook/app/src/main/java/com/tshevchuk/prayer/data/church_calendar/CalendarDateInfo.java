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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CalendarDateInfo that = (CalendarDateInfo) o;

		if (isDateRed != that.isDateRed) return false;
		if (shiftDaysFromEaster != that.shiftDaysFromEaster) return false;
		if (person != null ? !person.equals(that.person) : that.person != null) return false;
		if (day != null ? !day.equals(that.day) : that.day != null) return false;
		if (date != null ? !date.equals(that.date) : that.date != null) return false;
		if (dateJulian != null ? !dateJulian.equals(that.dateJulian) : that.dateJulian != null)
			return false;
		if (pistType != null ? !pistType.equals(that.pistType) : that.pistType != null)
			return false;
		if (pistName != null ? !pistName.equals(that.pistName) : that.pistName != null)
			return false;
		return !(dayDescription != null ? !dayDescription.equals(that.dayDescription) : that.dayDescription != null);

	}

	@Override
	public int hashCode() {
		int result = person != null ? person.hashCode() : 0;
		result = 31 * result + (day != null ? day.hashCode() : 0);
		result = 31 * result + (isDateRed ? 1 : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (dateJulian != null ? dateJulian.hashCode() : 0);
		result = 31 * result + shiftDaysFromEaster;
		result = 31 * result + (pistType != null ? pistType.hashCode() : 0);
		result = 31 * result + (pistName != null ? pistName.hashCode() : 0);
		result = 31 * result + (dayDescription != null ? dayDescription.hashCode() : 0);
		return result;
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
