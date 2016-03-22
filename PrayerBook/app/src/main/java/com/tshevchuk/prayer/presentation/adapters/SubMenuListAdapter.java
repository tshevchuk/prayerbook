package com.tshevchuk.prayer.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.PreferenceManager;
import com.tshevchuk.prayer.domain.model.MenuItemBase;

import java.util.List;

public class SubMenuListAdapter extends BaseAdapter {
	private final List<MenuItemBase> items;
	private final LayoutInflater inflater;
	private final PreferenceManager preferenceManager;

	public SubMenuListAdapter(Context context, List<MenuItemBase> items, PreferenceManager preferenceManager) {
		this.items = items;
		this.preferenceManager = preferenceManager;
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
		int showUGCCVisibility = mi.isOfficialUGCCText()
				&& preferenceManager.isShowOfficialUgccEnabled()
				? View.VISIBLE : View.INVISIBLE;
		vh.ivOfficialStamp.setVisibility(showUGCCVisibility);
		return v;
	}

	private static class ViewHolder {
		TextView tvName;
		ImageView ivOfficialStamp;
	}
}
