package com.tshevchuk.prayer.domain.model.parcels;

import android.content.ClipData;
import android.os.Parcel;

import com.tshevchuk.prayer.domain.model.MenuItemBase;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taras on 20.03.16.
 */
public class MenuItemBaseListParcelConverter implements ParcelConverter<List<MenuItemBase>> {
    @Override
    public void toParcel(List<MenuItemBase> input, Parcel parcel) {
        if (input == null) {
            parcel.writeInt(-1);
        } else {
            parcel.writeInt(input.size());
            for (MenuItemBase item : input) {
                parcel.writeParcelable(Parcels.wrap(item), 0);
            }
        }
    }

    @Override
    public List<MenuItemBase> fromParcel(Parcel parcel) {
        int size = parcel.readInt();
        if (size < 0) return null;
        List<MenuItemBase> items = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            items.add((MenuItemBase) Parcels.unwrap(parcel.readParcelable(ClipData.Item.class.getClassLoader())));
        }
        return items;
    }
}