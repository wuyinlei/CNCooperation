package com.cainiao.factory.model.home;

import java.util.List;

/**
 * Created by wuyinlei on 2017/8/1.
 *
 * @function 首页推荐
 */

public class HomeIndex {



    private FestivalBean festival;
    private TitleLeftMenuBean title_left_menu;
    private List<TabsBean> tabs;

    public FestivalBean getFestival() {
        return festival;
    }

    public void setFestival(FestivalBean festival) {
        this.festival = festival;
    }

    public TitleLeftMenuBean getTitle_left_menu() {
        return title_left_menu;
    }

    public void setTitle_left_menu(TitleLeftMenuBean title_left_menu) {
        this.title_left_menu = title_left_menu;
    }

    public List<TabsBean> getTabs() {
        return tabs;
    }

    public void setTabs(List<TabsBean> tabs) {
        this.tabs = tabs;
    }


}
