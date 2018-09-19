package com.synertone.geeknewsdemo.presenter.zhihu;


import com.synertone.geeknewsdemo.base.RxPresenter;
import com.synertone.geeknewsdemo.base.contract.zhihu.ThemeContract;
import com.synertone.geeknewsdemo.model.DataManager;
import com.synertone.geeknewsdemo.model.bean.ThemeListBean;
import com.synertone.geeknewsdemo.util.RxUtil;
import com.synertone.geeknewsdemo.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by codeest on 16/8/12.
 */

public class ThemePresenter extends RxPresenter<ThemeContract.View> implements ThemeContract.Presenter{

    private DataManager mDataManager;

    @Inject
    public ThemePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getThemeData() {
        mDataManager.fetchDailyThemeListInfo()
                .compose(RxUtil.<ThemeListBean>rxSchedulerHelper())
                .subscribe(new CommonSubscriber<ThemeListBean>(mView) {
                    @Override
                    public void onNext(ThemeListBean themeListBean) {
                        mView.showContent(themeListBean);
                    }
                });
    }
}
