package com.synertone.geeknewsdemo.ui.zhihu.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.synertone.geeknewsdemo.R;
import com.synertone.geeknewsdemo.base.RootFragment;
import com.synertone.geeknewsdemo.base.contract.zhihu.SectionContract;
import com.synertone.geeknewsdemo.model.bean.SectionListBean;
import com.synertone.geeknewsdemo.presenter.zhihu.SectionPresenter;
import com.synertone.geeknewsdemo.ui.zhihu.adapter.SectionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by codeest on 2016/8/11.
 */
public class SectionFragment extends RootFragment<SectionPresenter> implements SectionContract.View {

    @BindView(R.id.view_main)
    RecyclerView rvSectionList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    List<SectionListBean.DataBean> mList;
    SectionAdapter mAdapter;

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
        mList = new ArrayList<>();
        mAdapter = new SectionAdapter(mContext,mList);
        rvSectionList.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvSectionList.setAdapter(mAdapter);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getSectionData();
            }
        });
        mPresenter.getSectionData();
        stateLoading();
    }

    @Override
    public void stateError() {
        super.stateError();
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void showContent(SectionListBean sectionListBean) {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        stateMain();
        mList.clear();
        mList.addAll(sectionListBean.getData());
        mAdapter.notifyDataSetChanged();
    }
}
