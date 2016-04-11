package com.tshevchuk.prayer.presentation.home;

import android.text.TextUtils;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.Analytics;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuListItemSearch;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.base.BasePresenter;
import com.tshevchuk.prayer.presentation.search.SearchFragment;

import java.util.ArrayList;

/**
 * Created by taras on 23.03.16.
 */
public class HomePresenter extends BasePresenter<HomeView> {
    private final Navigator navigator;
    private final DataManager dataManager;
    private final AnalyticsManager analyticsManager;
    private boolean restoringInstanceState;
    private int paramScreenId;

    public HomePresenter(Navigator navigator, DataManager dataManager, AnalyticsManager analyticsManager) {
        this.navigator = navigator;
        this.dataManager = dataManager;
        this.analyticsManager = analyticsManager;
    }

    @Override
    public void attachView(HomeView mvpView) {
        super.attachView(mvpView);

        if (!restoringInstanceState) {
            int id = paramScreenId;
            if (id == 0) {
                id = dataManager.getDefaultScreenMenuItemId();
            }
            navigator.showMenuItem(this, dataManager.getMenuListItem(id));

            getMvpView().updateAppRater();
        }
    }

    public void onBackPressed() {
        if (!getMvpView().handleUpAction()) {
            getMvpView().handleBackAction();
        }
    }

    public void setParamScreenId(int id) {
        paramScreenId = id;
    }

    public void setRestoringInstanceState(boolean restoring) {
        restoringInstanceState = restoring;
    }

    public void onSearchSubmit(String query) {
        if (!navigator.updateSearchPhraseOnSearchView(this, query)) {
            navigator.showSearchScreen(this, query);
        }
        analyticsManager.sendActionEvent(Analytics.CAT_SEARCH, "Підтверджено пошукову фразу", query);
    }

    public void onSearchQueryTextChange(String newText) {
        //todo: refactor
        if (getSupportFragmentManager().findFragmentById(R.id.content_frame) instanceof SearchFragment) {
            search(newText);
            analyticsManager.sendActionEvent(Analytics.CAT_SEARCH, "Пошук на фрагменті пошуку", newText);
        } else {
            final ArrayList<MenuListItemSearch> items = dataManager.searchMenuItems(newText);
            getMvpView().showSearchSuggestions(items);
            if (!TextUtils.isEmpty(newText)) {
                analyticsManager.sendActionEvent(Analytics.CAT_SEARCH,
                        "Пошук із випадаючим списком підказок", newText);
            }
        }
    }

    private void search(String query) {
        if (!navigator.updateSearchPhraseOnSearchView(this, query)) {
            navigator.showSearchScreen(this, query);
        }
    }

    public void onSettingsClick() {
        //todo: refactor
        navigator.showSettings(this);
        analyticsManager.sendActionEvent(Analytics.CAT_OPTIONS_MENU, "Налаштування");
    }

    public void onReportMistakeClick() {
        //todo: implement
//		File dir = new File(getCacheDir(), "error_report_screenshots");
//		if (!dir.exists()) {
//			if (!dir.mkdirs()) {
//				Log.d(TAG, "Can't create directory");
//				return;
//			}
//		}
//		File imageFile = new File(dir
//				, "prayerbook_error_image_" + System.currentTimeMillis() + ".png");
//
//		Bitmap bitmap = null;
//		View v1 = getWindow().getDecorView().findViewById(android.R.id.content);
//
//		boolean willNotCache = v1.willNotCacheDrawing();
//		v1.setWillNotCacheDrawing(false);
//
//		int color = v1.getDrawingCacheBackgroundColor();
//		v1.setDrawingCacheBackgroundColor(preferenceManager
//				.isNightModeEnabled() ? Color.BLACK : Color.WHITE);
//
//		if (color != 0) {
//			v1.destroyDrawingCache();
//		}
//		v1.buildDrawingCache();
//		Bitmap cacheBitmap = v1.getDrawingCache();
//		if (cacheBitmap != null) {
//			bitmap = Bitmap.createBitmap(cacheBitmap);
//		}
//
//		v1.destroyDrawingCache();
//		v1.setWillNotCacheDrawing(willNotCache);
//		v1.setDrawingCacheBackgroundColor(color);
//
//		if (bitmap != null) {
//			OutputStream fout;
//			try {
//				fout = new FileOutputStream(imageFile);
//				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
//				fout.flush();
//				fout.close();
//			} catch (IOException e) {
//				bitmap = null;
//				e.printStackTrace();
//			}
//		}
//
//		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//		emailIntent.setType("message/rfc822");
//		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
//				new String[]{"taras.shevchuk@gmail.com"});
//		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
//				"Молитовник: Повідомлення про помилку");
//
//		StringBuilder sb = new StringBuilder();
//		sb.append("Опишіть коротко помилку:\n\n\n\n");
//		sb.append("----------------------------");
//		sb.append("\nПрограма: ").append(utils.getApplicationNameAndVersion());
//		ActionBar actionBar = getSupportActionBar();
//		if (actionBar != null) {
//			sb.append("\nЗаголовок: ").append(actionBar.getTitle());
//		}
//		Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_frame);
//		sb.append("\nФрагмент: ").append(f.getClass().getName());
//		if (f instanceof FragmentBase) {
//			MenuItemBase mi = ((FragmentBase) f).getMenuItem();
//			if (mi != null) {
//				sb.append("\nЕлемент меню: ").append(mi.getId()).append(" ")
//						.append(mi.getName());
//			}
//		}
//		sb.append("\n").append(Utils.getDeviceInfo(getApplicationContext()));
//
//		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, sb.toString());
//		if (bitmap != null) {
//			emailIntent.putExtra(Intent.EXTRA_STREAM,
//					FileProvider.getUriForFile(this, "com.tshevchuk.prayer.fileprovider", imageFile)
//			);
//		}
//		startActivity(Intent.createChooser(emailIntent,
//				"Відправити повідомлення про помилку..."));

        analyticsManager.sendActionEvent(Analytics.CAT_OPTIONS_MENU, "Повідомити про помилку");
    }

    public void onSearchSuggestionClick(MenuListItemSearch mi) {
        navigator.showMenuItem(this, mi);
        dataManager.updateRecentlyUsedBecauseItemOpened(mi.getId());
        analyticsManager.sendActionEvent(Analytics.CAT_SEARCH,
                "Вибрано випадаючу підказку", mi.getId() + " " + mi.getName());
    }
}
