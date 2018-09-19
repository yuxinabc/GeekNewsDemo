package com.synertone.geeknewsdemo.base.contract.zhihu;
import com.synertone.geeknewsdemo.base.BasePresenter;
import com.synertone.geeknewsdemo.base.BaseView;
import com.synertone.geeknewsdemo.model.bean.CommentBean;

/**
 * Created by codeest on 16/8/18.
 */

public interface CommentContract {

    interface View extends BaseView {

        void showContent(CommentBean commentBean);

    }

    interface Presenter extends BasePresenter<View> {

        void getCommentData(int id,int commentKind);

    }
}
