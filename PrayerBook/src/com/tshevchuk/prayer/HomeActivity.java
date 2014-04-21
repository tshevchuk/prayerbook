package com.tshevchuk.prayer;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends Activity {
	private String[] menuItems;

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_home);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		menuItems = getResources().getStringArray(R.array.home_menu);

		drawerList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menuItems));
		drawerList.setBackgroundColor(Color.LTGRAY);
		drawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				displayFragment(position);
			}
		});

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_launcher, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				// getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				// getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);

		if (savedInstanceState == null) {
			displayFragment(0);
		}
	}

	@Override
	protected void onDestroy() {
		GoogleAnalytics.getInstance(PrayerBookApplication.getInstance())
				.dispatchLocalHits();
		super.onDestroy();

	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item))
			return true;

		return super.onOptionsItemSelected(item);
	}

	private void displayFragment(int position) {
		FragmentBase f = null;
		switch (position) {
		case 0:
			f = HtmlViewFragment.getInstance(menuItems[position],
					"schodenni_molytvy.html");
			break;
		case 1:
			f = HtmlViewFragment.getInstance(menuItems[position],
					"ranishni_molytvy.html");
			break;
		case 2:
			f = HtmlViewFragment.getInstance(menuItems[position],
					"vechirni_molytvy.html");
			break;
		case 3:
			f = HtmlViewFragment.getInstance(menuItems[position],
					"molytvy_na_kozhen_den.html");
			break;
		case 4:
			f = HtmlViewFragment.getInstance(menuItems[position],
					"molytvy_pry_trapezi.html");
			break;
		case 5:
			f = HtmlViewFragment.getInstance(menuItems[position],
					"molytvy_na_rizni_potreby.html");
			break;
		case 6:
			f = HtmlViewFragment.getInstance(menuItems[position],
					"psalom_90.html");
			break;
		case 7:
			f = HtmlViewFragment.getInstance(menuItems[position],
					"katekhyzmovi_pravdy.html");
			break;
		case 8:
			f = HtmlViewFragment.getInstance(menuItems[position],
					"chesnoty.html");
			break;
		}

		FragmentBase curFragment = (FragmentBase) getFragmentManager()
				.findFragmentById(R.id.content_frame);
		if (curFragment != null && curFragment.isSameScreen(f)) {
			return;
		}

		if (f != null) {
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.content_frame, f);
			if (curFragment != null)
				transaction.addToBackStack(null);
			transaction.commit();
			drawerList.setItemChecked(position, true);
			drawerList.setSelection(position);
			setTitle(menuItems[position]);
			drawerLayout.closeDrawer(drawerList);
		}

		PrayerBookApplication
				.getInstance()
				.getTracker()
				.send(new HitBuilders.EventBuilder()
						.setCategory("Fragment Opened")
						.setAction(menuItems[position]).build());
	}
}
