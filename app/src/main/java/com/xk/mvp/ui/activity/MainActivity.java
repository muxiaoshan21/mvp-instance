package com.xk.mvp.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xk.mvp.R;
import com.xk.mvp.presenter.BasePresenter;
import com.xk.mvp.statusbar.Eyes;
import com.xk.mvp.statusbar.StatusBarUtil;
import com.xk.mvp.ui.activity.base.BaseActivity;
import com.xk.mvp.ui.fragment.HomeFragment;
import com.xk.mvp.ui.fragment.OtherFragment;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.alphaIndicator)
    AlphaTabsIndicator alphaTabsIndicator;

    @Bind(R.id.mViewPager)
    ViewPager mViewPger;

    private List<Fragment> fragments = new ArrayList<>();


    @Override
    public boolean enableSlideClose() {
        return false;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    public void initView() {

//        StatusBarUtil.transparencyBar(this);  //  状态栏透明
//        StatusBarUtil.StatusBarLightMode(this); // 状态栏 字体 黑色

        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        mViewPger.setAdapter(mainAdapter);
        mViewPger.setOffscreenPageLimit(4);
        mViewPger.addOnPageChangeListener(mainAdapter);

        alphaTabsIndicator.setViewPager(mViewPger);

    }

    private class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

        public MainAdapter(FragmentManager fm) {
            super(fm);

            fragments.add(new HomeFragment());
            fragments.add(new OtherFragment());
            fragments.add(new OtherFragment());
            fragments.add(new OtherFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setStatusBarColor(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void setStatusBarColor(int position) {

        if(position == 3){
            //如果是我的页面，状态栏设置为透明状态栏
            Eyes.translucentStatusBar(MainActivity.this,true);
            StatusBarUtil.StatusBarLightMode(this); // 状态栏 字体 黑色
        }else{
            Eyes.setStatusBarColor(MainActivity.this, R.color.color_48A5FF);
        }

    }

}
