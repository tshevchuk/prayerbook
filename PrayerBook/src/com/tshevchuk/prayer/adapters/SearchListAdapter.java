package com.tshevchuk.prayer.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.SearchItem;

public class SearchListAdapter extends BaseAdapter {
	private List<SearchItem> items;
	private LayoutInflater inflater;

	public SearchListAdapter(Context context, List<SearchItem> items) {
		this.items = items;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public SearchItem getItem(int position) {
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

			vh.tvName = (TextView) v.findViewById(R.id.tvName);
			vh.tvParentName = (TextView) v.findViewById(R.id.tvParentName);
			vh.ivOfficialStamp = (ImageView) v
					.findViewById(R.id.iv_official_stamp);
			v.setTag(vh);
		}
		ViewHolder vh = (ViewHolder) v.getTag();
		SearchItem si = items.get(position);
		vh.tvName.setText(Html.fromHtml(si.getName()));
		vh.ivOfficialStamp
				.setVisibility(si.getMenuItem().isOfficialUGCCText() ? View.VISIBLE
						: View.INVISIBLE);
		if (si.getMenuItem().getParentItemId() > 0) {
			vh.tvParentName.setVisibility(View.VISIBLE);
			vh.tvParentName.setText(PrayerBookApplication.getInstance()
					.getCatalog()
					.getMenuItemById(si.getMenuItem().getParentItemId())
					.getName());
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
