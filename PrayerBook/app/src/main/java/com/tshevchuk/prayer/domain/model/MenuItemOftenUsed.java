package com.tshevchuk.prayer.domain.model;

import com.tshevchuk.prayer.data.Catalog;

import org.parceler.Parcel;

/**
 * Created by taras on 29.03.16.
 */
@Parcel
public class MenuItemOftenUsed extends MenuItemBase {

    public MenuItemOftenUsed() {
        super(Catalog.ID_RECENT_SCREENS, "Часто відкривалися");
    }

}
