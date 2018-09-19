package com.synertone.geeknewsdemo.ui.main.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.synertone.geeknewsdemo.R;
import com.synertone.geeknewsdemo.app.App;
import com.synertone.geeknewsdemo.app.Constants;
import com.synertone.geeknewsdemo.base.BaseActivity;
import com.synertone.geeknewsdemo.base.contract.main.MainContract;
import com.synertone.geeknewsdemo.presenter.main.MainPresenter;
import com.synertone.geeknewsdemo.ui.zhihu.fragment.ZhihuMainFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by codeest on 16/8/9.
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View{

    @BindView(R.id.drawer)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.navigation)
    NavigationView mNavigationView;
    @BindView(R.id.view_search)
    MaterialSearchView mSearchView;
    ZhihuMainFragment mZhihuFragment;
    MenuItem mLastMenuItem;
    MenuItem mSearchMenuItem;
    ActionBarDrawerToggle mDrawerToggle;

    private int hideFragment = Constants.TYPE_ZHIHU;
    private int showFragment = Constants.TYPE_ZHIHU;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            showFragment = mPresenter.getCurrentItem();
            hideFragment = Constants.TYPE_ZHIHU;
            showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
            mNavigationView.getMenu().findItem(R.id.drawer_zhihu).setChecked(false);
            mToolbar.setTitle(mNavigationView.getMenu().findItem(getCurrentItem(showFragment)).getTitle().toString());
            hideFragment = showFragment;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(mToolbar,"知乎日报");
        mZhihuFragment = new ZhihuMainFragment();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mLastMenuItem = mNavigationView.getMenu().findItem(R.id.drawer_zhihu);
        loadMultipleRootFragment(R.id.fl_main_content,0,mZhihuFragment);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.drawer_zhihu:
                        showFragment = Constants.TYPE_ZHIHU;
                        mSearchMenuItem.setVisible(false);
                        break;
                    case R.id.drawer_gank:
                        showFragment = Constants.TYPE_GANK;
                        mSearchMenuItem.setVisible(true);
                        break;
                    case R.id.drawer_wechat:
                        showFragment = Constants.TYPE_WECHAT;
                        mSearchMenuItem.setVisible(true);
                        break;
                    case R.id.drawer_gold:
                        showFragment = Constants.TYPE_GOLD;
                        mSearchMenuItem.setVisible(false);
                        break;
                    case R.id.drawer_vtex:
                        showFragment = Constants.TYPE_VTEX;
                        mSearchMenuItem.setVisible(false);
                        break;
                    case R.id.drawer_setting:
                        showFragment = Constants.TYPE_SETTING;
                        mSearchMenuItem.setVisible(false);
                        break;
                    case R.id.drawer_like:
                        showFragment = Constants.TYPE_LIKE;
                        mSearchMenuItem.setVisible(false);
                        break;
                    case R.id.drawer_about:
                        showFragment = Constants.TYPE_ABOUT;
                        mSearchMenuItem.setVisible(false);
                        break;
                }
                if(mLastMenuItem != null) {
                    mLastMenuItem.setChecked(false);
                }
                mLastMenuItem = menuItem;
                mPresenter.setCurrentItem(showFragment);
                menuItem.setChecked(true);
                mToolbar.setTitle(menuItem.getTitle());
                mDrawerLayout.closeDrawers();
                showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                hideFragment = showFragment;
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        mSearchView.setMenuItem(item);
        mSearchMenuItem = item;
        return true;
    }

    @Override
    public void onBackPressedSupport() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            showExitDialog();
        }
    }

    private void showExitDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出GeekNews吗");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                App.getInstance().exitApp();
            }
        });
        builder.show();
    }

    private SupportFragment getTargetFragment(int item) {
        switch (item) {
            case Constants.TYPE_ZHIHU:
                return mZhihuFragment;
        }
        return mZhihuFragment;
    }

    private int getCurrentItem(int item) {
        switch (item) {
            case Constants.TYPE_ZHIHU:
                return R.id.drawer_zhihu;
            case Constants.TYPE_GANK:
                return R.id.drawer_gank;
            case Constants.TYPE_WECHAT:
                return R.id.drawer_wechat;
            case Constants.TYPE_GOLD:
                return R.id.drawer_gold;
            case Constants.TYPE_VTEX:
                return R.id.drawer_vtex;
            case Constants.TYPE_LIKE:
                return R.id.drawer_like;
            case Constants.TYPE_SETTING:
                return R.id.drawer_setting;
            case Constants.TYPE_ABOUT:
                return R.id.drawer_about;
        }
        return R.id.drawer_zhihu;
    }

    @Override
    public void showUpdateDialog(String versionContent) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("检测到新版本!");
        builder.setMessage(versionContent);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkPermissions();
            }
        });
        builder.show();
    }

    @Override
    public void startDownloadService() {
       // startService(new Intent(mContext, UpdateService.class));
    }

    public void checkPermissions() {
        mPresenter.checkPermissions(new RxPermissions(this));
    }
}
