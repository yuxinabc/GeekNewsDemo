package com.synertone.geeknewsdemo.presenter.zhihu;


import com.synertone.geeknewsdemo.base.RxPresenter;
import com.synertone.geeknewsdemo.base.contract.zhihu.SectionChildContract;
import com.synertone.geeknewsdemo.model.DataManager;
import com.synertone.geeknewsdemo.model.bean.SectionChildListBean;
import com.synertone.geeknewsdemo.util.RxUtil;
import com.synertone.geeknewsdemo.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by codeest on 16/8/28.
 */

public class SectionChildPresenter extends RxPresenter<SectionChildContract.View> implements SectionChildContract.Presenter{

    private DataManager mDataManager;

    @Inject
    public SectionChildPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getThemeChildData(int id) {
        addSubscribe(mDataManager.fetchSectionChildListInfo(id)
                .compose(RxUtil.<SectionChildListBean>rxSchedulerHelper())
                .map(new Function<SectionChildListBean, SectionChildListBean>() {
                    @Override
                    public SectionChildListBean apply(SectionChildListBean sectionChildListBean) {
                        List<SectionChildListBean.StoriesBean> list = sectionChildListBean.getStories();
                        for(SectionChildListBean.StoriesBean item : list) {
                            item.setReadState(mDataManager.queryNewsId(item.getId()));
                        }
                        return sectionChildListBean;
                    }
                })
                .subscribeWith(new CommonSubscriber<SectionChildListBean>(mView) {
                    @Override
                    public void onNext(SectionChildListBean sectionChildListBean) {
                        mView.showContent(sectionChildListBean);
                    }
                })
        );
    }

    @Override
    public void insertReadToDB(int id) {
        mDataManager.insertNewsId(id);
    }
}
