package com.tshevchuk.prayer.presentation.sub_menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.MenuListItem;

import java.util.List;

class SubMenuListAdapter extends BaseAdapter {
	private final List<MenuListItem> items;
	private final LayoutInflater inflater;

	SubMenuListAdapter(Context context, List<MenuListItem> items) {
		this.items = items;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public MenuListItem getItem(int position) {
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
			v = inflater.inflate(R.layout.f_submenu_item, parent, false);
			ViewHolder vh = new ViewHolder();

			vh.tvName = v.findViewById(R.id.tv_name);
			vh.ivOfficialStamp = v.findViewById(R.id.iv_official_stamp);
			v.setTag(vh);
		}

		ViewHolder vh = (ViewHolder) v.getTag();
		MenuItemBase mi = items.get(position);
		vh.tvName.setText(mi.getName());
		vh.ivOfficialStamp.setVisibility(mi.isOfficialUGCCText() ? View.VISIBLE : View.INVISIBLE);
		return v;
	}

	private static class ViewHolder {
		TextView tvName;
		ImageView ivOfficialStamp;
	}
}
