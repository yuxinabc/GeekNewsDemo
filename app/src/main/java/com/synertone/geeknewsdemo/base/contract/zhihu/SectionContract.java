package com.synertone.geeknewsdemo.base.contract.zhihu;
import com.synertone.geeknewsdemo.base.BasePresenter;
import com.synertone.geeknewsdemo.base.BaseView;
import com.synertone.geeknewsdemo.model.bean.SectionListBean;

/**
 * Created by codeest on 16/8/12.
 */

public interface SectionContract {

    interface View extends BaseView {

        void showContent(SectionListBean sectionListBean);

    }

    interface Presenter extends BasePresenter<View> {

        void getSectionData();
    }
}
