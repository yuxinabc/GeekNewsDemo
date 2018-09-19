package com.synertone.geeknewsdemo.ui.zhihu.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.synertone.geeknewsdemo.R;
import com.synertone.geeknewsdemo.app.Constants;
import com.synertone.geeknewsdemo.base.RootFragment;
import com.synertone.geeknewsdemo.base.contract.zhihu.ThemeContract;
import com.synertone.geeknewsdemo.model.bean.ThemeListBean;
import com.synertone.geeknewsdemo.presenter.zhihu.ThemePresenter;
import com.synertone.geeknewsdemo.ui.zhihu.adapter.ThemeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by codeest on 2016/8/11.
 */
public class ThemeFragment extends RootFragment<ThemePresenter> implements ThemeContract.View {

    @BindView(R.id.view_main)
    RecyclerView rvThemeList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    ThemeAdapter mAdapter;
    List<ThemeListBean.OthersBean> mList = new ArrayList<>();

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_common_list;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mAdapter = new ThemeAdapter(mContext, mList);
        rvThemeList.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvThemeList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ThemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
               /* Intent intent = new Intent();
                intent.setClass(mContext, ThemeActivity.class);
                intent.putExtra(Constants.IT_ZHIHU_THEME_ID, id);
                mContext.startActivity(intent);*/
            }
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getThemeData();
            }
        });
        mPresenter.getThemeData();
        stateLoading();
    }

    @Override
    public void showContent(ThemeListBean themeListBean) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        stateMain();
        mList.clear();
        mList.addAll(themeListBean.getOthers());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void stateError() {
        super.stateError();
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }
}
