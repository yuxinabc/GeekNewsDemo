package com.synertone.geeknewsdemo.di.module;
import com.synertone.geeknewsdemo.app.App;
import com.synertone.geeknewsdemo.model.DataManager;
import com.synertone.geeknewsdemo.model.db.DBHelper;
import com.synertone.geeknewsdemo.model.db.RealmHelper;
import com.synertone.geeknewsdemo.model.http.HttpHelper;
import com.synertone.geeknewsdemo.model.http.RetrofitHelper;
import com.synertone.geeknewsdemo.model.prefs.ImplPreferencesHelper;
import com.synertone.geeknewsdemo.model.prefs.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codeest on 16/8/7.
 */

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }

    @Provides
    @Singleton
    DBHelper provideDBHelper(RealmHelper realmHelper) {
        return realmHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(ImplPreferencesHelper implPreferencesHelper) {
        return implPreferencesHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper, DBHelper DBHelper, PreferencesHelper preferencesHelper) {
        return new DataManager(httpHelper, DBHelper, preferencesHelper);
    }
}
