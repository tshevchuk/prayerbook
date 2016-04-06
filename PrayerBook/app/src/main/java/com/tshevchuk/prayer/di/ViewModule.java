package com.tshevchuk.prayer.di;

import android.app.Application;
import android.content.Context;

import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.CerkovnyyCalendar;
import com.tshevchuk.prayer.data.PreferenceManager;
import com.tshevchuk.prayer.data.cache.InMemoryCacheManager;
import com.tshevchuk.prayer.data.cache.InMemoryCacheManagerImpl;
import com.tshevchuk.prayer.data.html_parser.HtmlParser;
import com.tshevchuk.prayer.data.html_parser.HtmlParserImpl;
import com.tshevchuk.prayer.data.repositories.CerkovnyyCalendarRepository;
import com.tshevchuk.prayer.data.repositories.TextsRepository;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.presentation.AnalyticsManagerImpl;
import com.tshevchuk.prayer.presentation.AsyncTaskManager;
import com.tshevchuk.prayer.presentation.AsyncTaskManagerImpl;
import com.tshevchuk.prayer.presentation.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by taras on 20.03.16.
 */
@Module
public class ViewModule {
    @Provides
    @Singleton
    Catalog provideCatalog(Application application) {
        return new Catalog(application);
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    PreferenceManager providePreferenceManager(Application application) {
        return new PreferenceManager(application);
    }

    @Provides
    @Singleton
    AnalyticsManager provideAnalyticsManager(Application application) {
        return new AnalyticsManagerImpl(application);
    }

    @Provides
    @Singleton
    CerkovnyyCalendar provideCerkovnyyCalendar() {
        return new CerkovnyyCalendar();
    }

    @Provides
    @Singleton
    Utils provideUtils(Application application) {
        return new Utils(application.getApplicationContext());
    }

    @Provides
    @Singleton
    TextsRepository provideTextsRepository(Catalog catalog, PreferenceManager preferenceManager,
                                           Context context,
                                           InMemoryCacheManager inMemoryCacheManager,
                                           HtmlParser htmlParser) {
        return new TextsRepository(catalog, preferenceManager, context, inMemoryCacheManager,
                htmlParser);
    }

    @Provides
    @Singleton
    CerkovnyyCalendarRepository provideCalendarRepository(CerkovnyyCalendar cerkovnyyCalendar) {
        return new CerkovnyyCalendarRepository(cerkovnyyCalendar);
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

    @Provides
    @Singleton
    InMemoryCacheManager provideInMemoryCacheManager() {
        return new InMemoryCacheManagerImpl();
    }

    @Provides
    @Singleton
    HtmlParser provideHtmlParser(Context context) {
        return new HtmlParserImpl(context);
    }

    @Provides
    AsyncTaskManager provideAsyncTaskManager() {
        return new AsyncTaskManagerImpl();
    }
}
