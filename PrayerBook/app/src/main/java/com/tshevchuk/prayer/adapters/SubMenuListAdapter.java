package com.tshevchuk.prayer.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.MenuItemBase;

public class SubMenuListAdapter extends BaseAdapter {
	private List<MenuItemBase> items;
	private LayoutInflater inflater;

	public SubMenuListAdapter(Context context, List<MenuItemBase> items) {
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
			v = inflater.inflate(R.layout.f_submenu_item, parent, false);
			ViewHolder vh = new ViewHolder();

			vh.tvName = (TextView) v.findViewById(R.id.tv_name);
			vh.ivOfficialStamp = (ImageView) v
					.findViewById(R.id.iv_official_stamp);
			v.setTag(vh);
		}

		ViewHolder vh = (ViewHolder) v.getTag();
		MenuItemBase mi = items.get(position);
		vh.tvName.setText(mi.getName());
		vh.ivOfficialStamp.setVisibility(mi.isOfficialUGCCText() ? View.VISIBLE
				: View.INVISIBLE);
		return v;
	}

	private static class ViewHolder {
		TextView tvName;
		ImageView ivOfficialStamp;
	}
}
