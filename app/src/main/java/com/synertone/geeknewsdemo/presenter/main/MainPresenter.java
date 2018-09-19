package com.synertone.geeknewsdemo.presenter.main;

import android.Manifest;

import com.synertone.geeknewsdemo.base.RxPresenter;
import com.synertone.geeknewsdemo.base.contract.main.MainContract;
import com.synertone.geeknewsdemo.model.DataManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;
import io.reactivex.functions.Consumer;


/**
 * Created by codeest on 16/8/9.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter{

    private DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
    }

    @Override
    public void checkPermissions(RxPermissions rxPermissions) {
        addSubscribe(rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) {
                        if (granted) {
                            mView.startDownloadService();
                        } else {
                            mView.showErrorMsg("下载应用需要文件写入权限哦~");
                        }
                    }
                })
        );
    }
    @Override
    public void setCurrentItem(int index) {
        mDataManager.setCurrentItem(index);
    }

    @Override
    public int getCurrentItem() {
        return mDataManager.getCurrentItem();
    }

    @Override
    public void setVersionPoint(boolean b) {
        mDataManager.setVersionPoint(b);
    }

    @Override
    public boolean getVersionPoint() {
        return mDataManager.getVersionPoint();
    }

}
