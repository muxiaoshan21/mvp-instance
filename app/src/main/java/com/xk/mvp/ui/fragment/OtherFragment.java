package com.xk.mvp.ui.fragment;

import com.xk.mvp.R;
import com.xk.mvp.presenter.BasePresenter;
import com.xk.mvp.ui.fragment.base.BaseFragment;

/**
 * Created by xk on 2017/9/19.
 */

public class OtherFragment extends BaseFragment{
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_other;
    }

    @Override
    protected void loadData() {

    }
}
