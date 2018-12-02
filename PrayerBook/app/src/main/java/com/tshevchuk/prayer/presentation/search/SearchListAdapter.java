package com.tshevchuk.prayer.presentation.search;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.model.MenuListItemSearch;

import java.util.List;

class SearchListAdapter extends BaseAdapter {
	private final List<MenuListItemSearch> items;
	private final LayoutInflater inflater;

	SearchListAdapter(Context context, List<MenuListItemSearch> items) {
		this.items = items;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public MenuListItemSearch getItem(int position) {
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
			v = inflater.inflate(R.layout.f_search_item, parent, false);
			ViewHolder vh = new ViewHolder();

			vh.tvName = v.findViewById(R.id.tvName);
			vh.tvParentName = v.findViewById(R.id.tvParentName);
			vh.ivOfficialStamp = v.findViewById(R.id.iv_official_stamp);
			v.setTag(vh);
		}
		ViewHolder vh = (ViewHolder) v.getTag();
		MenuListItemSearch si = items.get(position);
		vh.tvName.setText(Html.fromHtml(si.getDisplayName()));
		vh.ivOfficialStamp.setVisibility(si.isOfficialUGCCText() ? View.VISIBLE : View.INVISIBLE);
		if (si.getParentItemId() > 0) {
			vh.tvParentName.setVisibility(View.VISIBLE);
			vh.tvParentName.setText(si.getParentName());
		} else {
			vh.tvParentName.setVisibility(View.GONE);
		}
		return v;
	}

	private static class ViewHolder {
		TextView tvName;
		TextView tvParentName;
		ImageView ivOfficialStamp;
	}
}
