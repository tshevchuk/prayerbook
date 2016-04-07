package com.tshevchuk.prayer.presentation.prayer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.presentation.base.FragmentBase;

public abstract class TextFragmentBase extends FragmentBase {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar_textviewfragment, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mi_about_prayer:
			((TextBasePresenter) getPresenter()).onOpenAboutClick();
			return true;

		case android.R.id.home:
			return ((TextBasePresenter) getPresenter()).onUpClick();
		}
		return super.onOptionsItemSelected(item);
	}
}
