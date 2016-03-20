package com.tshevchuk.prayer.domain.model;


import com.tshevchuk.prayer.data.Catalog;

import org.parceler.Parcel;

@Parcel
public class MenuItemOftenUsed extends MenuItemBase {

	public MenuItemOftenUsed() {
		super(Catalog.ID_RECENT_SCREENS, "Часто відкривалися");
	}

}