package com.tshevchuk.prayer.presentation.often_used;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.model.MenuListItemOftenUsed;

import java.util.List;

class OftenUsedListAdapter extends BaseAdapter {
	private final List<MenuListItemOftenUsed> items;
	private final LayoutInflater inflater;

	OftenUsedListAdapter(Context context, List<MenuListItemOftenUsed> items) {
		this.items = items;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public MenuListItemOftenUsed getItem(int position) {
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

			vh.tvName = v.findViewById(R.id.tvName);
			vh.tvParentName = v.findViewById(R.id.tvParentName);
			vh.ivOfficialUgcc = v.findViewById(R.id.iv_official_ugcc);
			v.setTag(vh);
		}

		ViewHolder vh = (ViewHolder) v.getTag();
		MenuListItemOftenUsed mi = items.get(position);
		vh.tvName.setText(mi.getName());
		vh.ivOfficialUgcc.setVisibility(mi.isOfficialUGCCText() ? View.VISIBLE : View.INVISIBLE);
		if (TextUtils.isEmpty(mi.getParentName())) {
			vh.tvParentName.setVisibility(View.GONE);
		} else {
			vh.tvParentName.setText(mi.getName());
			vh.tvParentName.setVisibility(View.VISIBLE);
		}
		return v;
	}

	private static class ViewHolder {
		TextView tvName;
		TextView tvParentName;
		ImageView ivOfficialUgcc;
	}
}
