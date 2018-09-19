package com.synertone.geeknewsdemo.base.contract.zhihu;
import com.synertone.geeknewsdemo.base.BasePresenter;
import com.synertone.geeknewsdemo.base.BaseView;
import com.synertone.geeknewsdemo.model.bean.DailyBeforeListBean;
import com.synertone.geeknewsdemo.model.bean.DailyListBean;

/**
 * Created by codeest on 16/8/11.
 */

public interface DailyContract {

    interface View extends BaseView {

        void showContent(DailyListBean info);

        void showMoreContent(String date,DailyBeforeListBean info);

        void doInterval(int currentCount);
    }

    interface Presenter extends BasePresenter<View> {

        void getDailyData();

        void getBeforeData(String date);

        void startInterval();

        void stopInterval();

        void insertReadToDB(int id);
    }
}
