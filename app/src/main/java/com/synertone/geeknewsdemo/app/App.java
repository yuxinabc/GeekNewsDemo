package com.synertone.geeknewsdemo.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;
import com.synertone.geeknewsdemo.di.component.AppComponent;
import com.synertone.geeknewsdemo.di.component.DaggerAppComponent;
import com.synertone.geeknewsdemo.di.module.AppModule;
import com.synertone.geeknewsdemo.component.InitializeService;
import com.synertone.geeknewsdemo.di.module.HttpModule;
import com.synertone.geeknewsdemo.util.SystemUtil;
import com.synertone.geeknewsdemo.widget.AppBlockCanaryContext;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;

import static com.synertone.geeknewsdemo.util.LogUtil.isDebug;

/**
 * Created by codeest on 2016/8/2.
 */
public class App extends Application {

    private static App instance;
    public static AppComponent appComponent;
    private Set<Activity> allActivities;

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    public static synchronized App getInstance() {
        return instance;
    }

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化屏幕宽高
        getScreenSize();
        //初始化数据库
        Realm.init(getApplicationContext());
        //初始化过度绘制检测
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        //初始化内存泄漏检测
        initLeakCanary();
        //初始化错误收集
//        CrashHandler.init(new CrashHandler(getApplicationContext()));
        initBugly();
        //在子线程中完成其他初始化
        InitializeService.start(this);
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.

        }else{
            LeakCanary.install(this);
        }
    }

    private void initBugly() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(this, Constants.BUGLY_ID, isDebug, strategy);
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public void getScreenSize() {
        WindowManager windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if(SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }

    public static AppComponent getAppComponent(){
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }
}
