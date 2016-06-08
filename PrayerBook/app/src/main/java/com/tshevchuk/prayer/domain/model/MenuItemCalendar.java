package com.tshevchuk.prayer.domain.model;


import com.tshevchuk.prayer.data.Catalog;

import org.parceler.Parcel;

@Parcel
public class MenuItemCalendar extends MenuItemBase {

	public MenuItemCalendar() {
		super(Catalog.ID_CALENDAR, "Церковний календар");
	}

}