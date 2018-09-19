package com.synertone.geeknewsdemo.presenter.zhihu;



import com.synertone.geeknewsdemo.base.RxPresenter;
import com.synertone.geeknewsdemo.base.contract.zhihu.ThemeChildContract;
import com.synertone.geeknewsdemo.model.DataManager;
import com.synertone.geeknewsdemo.model.bean.ThemeChildListBean;
import com.synertone.geeknewsdemo.util.RxUtil;
import com.synertone.geeknewsdemo.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by codeest on 16/8/24.
 */

public class ThemeChildPresenter extends RxPresenter<ThemeChildContract.View> implements ThemeChildContract.Presenter{

    private DataManager mDataManager;

    @Inject
    public ThemeChildPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getThemeChildData(int id) {
        addSubscribe(mDataManager.fetchThemeChildListInfo(id)
                .compose(RxUtil.<ThemeChildListBean>rxSchedulerHelper())
                .map(new Function<ThemeChildListBean, ThemeChildListBean>() {
                    @Override
                    public ThemeChildListBean apply(ThemeChildListBean themeChildListBean) {
                        List<ThemeChildListBean.StoriesBean> list = themeChildListBean.getStories();
                        for(ThemeChildListBean.StoriesBean item : list) {
                            item.setReadState(mDataManager.queryNewsId(item.getId()));
                        }
                        return themeChildListBean;
                    }
                })
                .subscribeWith(new CommonSubscriber<ThemeChildListBean>(mView) {
                    @Override
                    public void onNext(ThemeChildListBean themeChildListBean) {
                        mView.showContent(themeChildListBean);
                    }
                })
        );
    }

    @Override
    public void insertReadToDB(int id) {
        mDataManager.insertNewsId(id);
    }
}
