package com.synertone.geeknewsdemo.component;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.github.moduth.blockcanary.BlockCanary;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.synertone.geeknewsdemo.app.App;
import com.synertone.geeknewsdemo.app.Constants;
import com.synertone.geeknewsdemo.util.SystemUtil;
import com.synertone.geeknewsdemo.widget.AppBlockCanaryContext;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;

import static com.synertone.geeknewsdemo.util.LogUtil.isDebug;

/**
 * Created by codeest on 2017/2/12.
 */

public class InitializeService extends IntentService {

    private static final String ACTION_INIT = "initApplication";

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT.equals(action)) {
                initApplication();
            }
        }
    }

    private void initApplication() {
        //初始化日志
        Logger.init(getPackageName()).hideThreadInfo();
        //初始化tbs x5 webview
        QbSdk.allowThirdPartyAppDownload(true);
        QbSdk.initX5Environment(getApplicationContext(), QbSdk.WebviewInitType.FIRSTUSE_AND_PRELOAD, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
            }

            @Override
            public void onViewInitFinished(boolean b) {
            }
        });
    }


}
