package com.synertone.geeknewsdemo.presenter.zhihu;



import com.synertone.geeknewsdemo.base.RxPresenter;
import com.synertone.geeknewsdemo.base.contract.zhihu.SectionContract;
import com.synertone.geeknewsdemo.model.DataManager;
import com.synertone.geeknewsdemo.model.bean.SectionListBean;
import com.synertone.geeknewsdemo.util.RxUtil;
import com.synertone.geeknewsdemo.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by codeest on 16/8/12.
 */

public class SectionPresenter extends RxPresenter<SectionContract.View> implements SectionContract.Presenter{

    private DataManager mDataManager;

    @Inject
    public SectionPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getSectionData() {
        addSubscribe(mDataManager.fetchSectionListInfo()
                .compose(RxUtil.<SectionListBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<SectionListBean>(mView) {
                    @Override
                    public void onNext(SectionListBean sectionListBean) {
                        mView.showContent(sectionListBean);
                    }
                })
        );
    }
}
