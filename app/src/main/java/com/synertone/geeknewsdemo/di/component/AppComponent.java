package com.synertone.geeknewsdemo.di.component;

import com.synertone.geeknewsdemo.app.App;
import com.synertone.geeknewsdemo.di.module.AppModule;
import com.synertone.geeknewsdemo.di.module.HttpModule;
import com.synertone.geeknewsdemo.model.DataManager;
import com.synertone.geeknewsdemo.model.db.RealmHelper;
import com.synertone.geeknewsdemo.model.http.RetrofitHelper;
import com.synertone.geeknewsdemo.model.prefs.ImplPreferencesHelper;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    App getContext();  // 提供App的Context
    ImplPreferencesHelper preferencesHelper(); //提供sp帮助类
    DataManager getDataManager(); //数据中心
    RetrofitHelper retrofitHelper();  //提供http的帮助类
    RealmHelper realmHelper();    //提供数据库帮助类
}
