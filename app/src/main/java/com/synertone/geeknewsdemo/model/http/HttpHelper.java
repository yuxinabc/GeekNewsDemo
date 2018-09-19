package com.synertone.geeknewsdemo.model.http;
import com.synertone.geeknewsdemo.model.bean.CommentBean;
import com.synertone.geeknewsdemo.model.bean.DailyBeforeListBean;
import com.synertone.geeknewsdemo.model.bean.DailyListBean;
import com.synertone.geeknewsdemo.model.bean.DetailExtraBean;
import com.synertone.geeknewsdemo.model.bean.GankItemBean;
import com.synertone.geeknewsdemo.model.bean.GankSearchItemBean;
import com.synertone.geeknewsdemo.model.bean.GoldListBean;
import com.synertone.geeknewsdemo.model.bean.HotListBean;
import com.synertone.geeknewsdemo.model.bean.NodeBean;
import com.synertone.geeknewsdemo.model.bean.NodeListBean;
import com.synertone.geeknewsdemo.model.bean.RepliesListBean;
import com.synertone.geeknewsdemo.model.bean.SectionChildListBean;
import com.synertone.geeknewsdemo.model.bean.SectionListBean;
import com.synertone.geeknewsdemo.model.bean.ThemeChildListBean;
import com.synertone.geeknewsdemo.model.bean.ThemeListBean;
import com.synertone.geeknewsdemo.model.bean.VersionBean;
import com.synertone.geeknewsdemo.model.bean.WXItemBean;
import com.synertone.geeknewsdemo.model.bean.WelcomeBean;
import com.synertone.geeknewsdemo.model.bean.ZhihuDetailBean;
import com.synertone.geeknewsdemo.model.http.response.GankHttpResponse;
import com.synertone.geeknewsdemo.model.http.response.GoldHttpResponse;
import com.synertone.geeknewsdemo.model.http.response.MyHttpResponse;
import com.synertone.geeknewsdemo.model.http.response.WXHttpResponse;


import java.util.List;

import io.reactivex.Flowable;

/**
 * @author: Est <codeest.dev@gmail.com>
 * @date: 2017/4/21
 * @description:
 */

public interface HttpHelper {

    Flowable<DailyListBean> fetchDailyListInfo();

    Flowable<DailyBeforeListBean> fetchDailyBeforeListInfo(String date);

    Flowable<ThemeListBean> fetchDailyThemeListInfo();

    Flowable<ThemeChildListBean> fetchThemeChildListInfo(int id);

    Flowable<SectionListBean> fetchSectionListInfo();

    Flowable<SectionChildListBean> fetchSectionChildListInfo(int id);

    Flowable<ZhihuDetailBean> fetchDetailInfo(int id);

    Flowable<DetailExtraBean> fetchDetailExtraInfo(int id);

    Flowable<WelcomeBean> fetchWelcomeInfo(String res);

    Flowable<CommentBean> fetchLongCommentInfo(int id);

    Flowable<CommentBean> fetchShortCommentInfo(int id);

    Flowable<HotListBean> fetchHotListInfo();

    Flowable<GankHttpResponse<List<GankItemBean>>> fetchTechList(String tech, int num, int page);

    Flowable<GankHttpResponse<List<GankItemBean>>> fetchGirlList(int num, int page);

    Flowable<GankHttpResponse<List<GankItemBean>>> fetchRandomGirl(int num);

    Flowable<GankHttpResponse<List<GankSearchItemBean>>> fetchGankSearchList(String query, String type, int num, int page);

    Flowable<WXHttpResponse<List<WXItemBean>>> fetchWechatListInfo(int num, int page);

    Flowable<WXHttpResponse<List<WXItemBean>>> fetchWechatSearchListInfo(int num, int page, String word);

    Flowable<MyHttpResponse<VersionBean>> fetchVersionInfo();

    Flowable<GoldHttpResponse<List<GoldListBean>>> fetchGoldList(String type, int num, int page);

    Flowable<GoldHttpResponse<List<GoldListBean>>> fetchGoldHotList(String type, String dataTime, int limit);

    Flowable<NodeBean> fetchNodeInfo(String name);

    Flowable<List<NodeListBean>> fetchTopicList(String name);

    Flowable<List<NodeListBean>> fetchTopicInfo(String id);

    Flowable<List<RepliesListBean>> fetchRepliesList(String id);
}
