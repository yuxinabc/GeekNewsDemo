package com.synertone.geeknewsdemo.base.contract.main;


import com.synertone.geeknewsdemo.base.BasePresenter;
import com.synertone.geeknewsdemo.base.BaseView;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Created by codeest on 16/8/9.
 */

public interface MainContract {

    interface View extends BaseView {

        void showUpdateDialog(String versionContent);

        void startDownloadService();
    }

    interface  Presenter extends BasePresenter<View> {
        void checkPermissions(RxPermissions rxPermissions);
        void setCurrentItem(int index);

        int getCurrentItem();

        void setVersionPoint(boolean b);

        boolean getVersionPoint();
    }
}
