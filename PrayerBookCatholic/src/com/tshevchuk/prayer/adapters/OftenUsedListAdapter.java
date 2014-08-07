package com.tshevchuk.prayer.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.catholic.R;
import com.tshevchuk.prayer.data.MenuItemBase;

public class OftenUsedListAdapter extends BaseAdapter {
	private List<MenuItemBase> items;
	private LayoutInflater inflater;

	public OftenUsedListAdapter(Context context, List<MenuItemBase> items) {
		this.items = items;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public MenuItemBase getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = inflater.inflate(R.layout.f_often_used_item, parent, false);
			ViewHolder vh = new ViewHolder();

			vh.tvName = (TextView) v.findViewById(R.id.tvName);
			vh.tvParentName = (TextView) v.findViewById(R.id.tvParentName);
			vh.ivOfficialUgcc = (ImageView) v
					.findViewById(R.id.iv_official_ugcc);
			v.setTag(vh);
		}

		ViewHolder vh = (ViewHolder) v.getTag();
		MenuItemBase mi = items.get(position);
		vh.tvName.setText(mi.getName());
		vh.ivOfficialUgcc.setVisibility(mi.isOfficialUGCCText() ? View.VISIBLE
				: View.INVISIBLE);
		if (mi.getParentItemId() > 0) {
			vh.tvParentName.setText(PrayerBookApplication.getInstance()
					.getCatalog().getMenuItemById(mi.getParentItemId())
					.getName());
			vh.tvParentName.setVisibility(View.VISIBLE);
		} else {
			vh.tvParentName.setVisibility(View.GONE);
		}
		return v;
	}

	private static class ViewHolder {
		TextView tvName;
		TextView tvParentName;
		ImageView ivOfficialUgcc;
	}
}
