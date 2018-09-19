package com.synertone.geeknewsdemo.di.component;

import android.app.Activity;
import com.synertone.geeknewsdemo.di.module.FragmentModule;
import com.synertone.geeknewsdemo.di.scope.FragmentScope;
import com.synertone.geeknewsdemo.ui.zhihu.fragment.DailyFragment;
import com.synertone.geeknewsdemo.ui.zhihu.fragment.HotFragment;
import com.synertone.geeknewsdemo.ui.zhihu.fragment.SectionFragment;
import com.synertone.geeknewsdemo.ui.zhihu.fragment.ThemeFragment;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(DailyFragment dailyFragment);

    void inject(ThemeFragment themeFragment);

    void inject(SectionFragment sectionFragment);

    void inject(HotFragment hotFragment);

}
