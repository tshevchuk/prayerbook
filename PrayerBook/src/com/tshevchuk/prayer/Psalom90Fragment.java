package com.tshevchuk.prayer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class Psalom90Fragment extends FragmentBase {

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		getActivity().getActionBar().setTitle("Псалом 90");
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_psalom_90, container, false);
		WebView wv = (WebView) v.findViewById(R.id.wv_content);
		wv.loadData(getString(R.string.psalom_90), "text/html; charset=UTF-8",
				null);
		return v;
	}
}
