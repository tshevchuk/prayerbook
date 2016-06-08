package com.tshevchuk.prayer.di;

import android.app.Application;
import android.content.Context;

import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.FileManager;
import com.tshevchuk.prayer.data.PreferenceManager;
import com.tshevchuk.prayer.data.cache.InMemoryCacheManager;
import com.tshevchuk.prayer.data.cache.InMemoryCacheManagerImpl;
import com.tshevchuk.prayer.data.church_calendar.CalendarConfigReader;
import com.tshevchuk.prayer.data.church_calendar.ChurchCalendar;
import com.tshevchuk.prayer.data.html_parser.HtmlParser;
import com.tshevchuk.prayer.data.html_parser.HtmlParserImpl;
import com.tshevchuk.prayer.data.repositories.ChurchCalendarRepository;
import com.tshevchuk.prayer.data.repositories.TextsRepository;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.presentation.AnalyticsManagerImpl;
import com.tshevchuk.prayer.presentation.AsyncTaskManager;
import com.tshevchuk.prayer.presentation.AsyncTaskManagerImpl;
import com.tshevchuk.prayer.presentation.Navigator;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by taras on 20.03.16.
 */
@Module
public class ModelModule {
    @Provides
    @Singleton
    Catalog provideCatalog(Application application) {
        return new Catalog(application);
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
    ChurchCalendar provideCerkovnyyCalendar(final Context context) {
        CalendarConfigReader calendarConfigReader = new CalendarConfigReader() {
            @Override
            public String readConfig(String fileName) throws IOException {
                return Utils.getAssetAsString(context, "church_calendar/" + fileName);
            }
        };
        return new ChurchCalendar(calendarConfigReader);
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
    ChurchCalendarRepository provideCalendarRepository(ChurchCalendar churchCalendar) {
        return new ChurchCalendarRepository(churchCalendar);
    }

    @Provides
    @Singleton
    Navigator provideNavigator(AnalyticsManager analyticsManager) {
        return new Navigator(analyticsManager);
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


    @Provides
    @Singleton
    FileManager provideScreenshotFileManager(Context context) {
        return new FileManager(context);
    }
}
