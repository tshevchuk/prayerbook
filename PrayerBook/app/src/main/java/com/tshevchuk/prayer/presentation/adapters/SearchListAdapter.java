package com.tshevchuk.prayer.presentation.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.PreferenceManager;
import com.tshevchuk.prayer.domain.model.SearchItem;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;

import java.util.List;

public class SearchListAdapter extends BaseAdapter {
	private final List<SearchItem> items;
	private final LayoutInflater inflater;

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
		int showUGCCVisibility = si.getMenuItem().isOfficialUGCCText()
				&& PreferenceManager.getInstance().isShowOfficialUgccEnabled()
				? View.VISIBLE : View.INVISIBLE;
		vh.ivOfficialStamp.setVisibility(showUGCCVisibility);
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
