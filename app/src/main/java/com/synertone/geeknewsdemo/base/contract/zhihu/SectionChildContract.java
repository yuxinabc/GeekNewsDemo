package com.synertone.geeknewsdemo.base.contract.zhihu;
import com.synertone.geeknewsdemo.base.BasePresenter;
import com.synertone.geeknewsdemo.base.BaseView;
import com.synertone.geeknewsdemo.model.bean.SectionChildListBean;

/**
 * Created by codeest on 16/8/28.
 */

public interface SectionChildContract {

    interface View extends BaseView {

        void showContent(SectionChildListBean sectionChildListBean);
    }

    interface Presenter extends BasePresenter<View> {

        void getThemeChildData(int id);

        void insertReadToDB(int id);
    }
}
