package com.xk.mvp.ui.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.chaychan.lib.SlidingLayout;
import com.github.nukc.stateview.StateView;
import com.xk.mvp.application.AppApplication;
import com.xk.mvp.listener.PermissionListener;
import com.xk.mvp.presenter.BasePresenter;
import com.xk.mvp.ui.activity.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import butterknife.ButterKnife;

/**
 * Created by xk on 2017/9/19.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T mPresenter;
    protected StateView mStateView;
    public PermissionListener mPermissionListener;
    protected Bundle saveInstanceState;
    private static Activity mCurrentActivity;  //  管理activity
    public static List<Activity> mActivitys = new LinkedList();
    private static long exitTime;
    protected Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(enableSlideClose()){
            SlidingLayout slidingLayout = new SlidingLayout(this);
            slidingLayout.bindActivity(this);
        }

        this.saveInstanceState = savedInstanceState;
        mContext = this;
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        synchronized (mActivitys){
            mActivitys.add(this);
        }

        mPresenter = createPresenter();

        ButterKnife.bind(this);
        initView();
        initData();
        initzlistener();
    }


    public void initView(){}

    public void initData(){}

    public void initzlistener(){}

    /**
     * 是否允许策划关闭
     * @return
     */
    public boolean enableSlideClose(){
        return true;
    }


    /**
     * 创建Presenter和判断是否使用MVP模式(由子类实现)
     * @return
     */
    protected  abstract T createPresenter();

    /**
     * 获取当前界面的布局文件id(由子类实现)
     *
     * @return
     */
    protected abstract int getContentViewLayoutID();

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivitys){
            mActivitys.remove(this);
        }
        if(mPresenter != null){
            mPresenter.detachView();
        }
    }


    /**
     * 退出应用
     */
    public static void exitApplication(){
        ListIterator<Activity> iterator = mActivitys.listIterator();
        while(iterator.hasNext()){
            Activity next = iterator.next();
            next.finish();
        }
    }

    @Override
    public void onBackPressed() {
        // 如果是应用的主页
        if(mCurrentActivity instanceof MainActivity){
            if(System.currentTimeMillis() - exitTime > 2000){ // 两次点击间隔大于两秒
                exitTime = System.currentTimeMillis();
                Toast.makeText(AppApplication.getContext(),"再次点击，退出应用",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        super.onBackPressed();

    }

    /**
     * 判断是否注册EventBus
     * @param subscribe
     * @return
     */
    public boolean isEventBusRegisted(Object subscribe){
        return EventBus.getDefault().isRegistered(subscribe);
    }

    /**
     * 如果没有注册EventBus,进行注册操作
     * @param subscrbie
     */
    public void registerEventBus(Object subscrbie){
        if(!isEventBusRegisted(subscrbie)){
            EventBus.getDefault().register(subscrbie);
        }
    }

    /**
     * 如果已经注册EventBus,则进行解除注册
     * @param subscribe
     */
    public void unregisterEventBus(Object subscribe){
        if(isEventBusRegisted(subscribe)){
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 运行时申请权限
     * @param permissions
     * @param permissionListener
     */
    public void requestRuntimePermissioln(String[] permissions,PermissionListener permissionListener){
        mPermissionListener = permissionListener;
        List<String> permissionList = new ArrayList<>();
        for(String permission : permissions){
            if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(permission);
            }
        }

        if(!permissionList.isEmpty()){
            ActivityCompat.requestPermissions(this,permissionList.toArray(new String[permissionList.size()]),1);
        }else{
            permissionListener.onGranted();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 1:
                if(grantResults.length > 0){
                    List<String> deniedPermissions = new ArrayList<>();
                    for(int i = 0;i < grantResults.length;i++){
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if(grantResult != PackageManager.PERMISSION_GRANTED){
                            deniedPermissions.add(permission);
                        }
                    }

                    if(deniedPermissions.isEmpty()){
                        mPermissionListener.onGranted();
                    }else{
                        mPermissionListener.onDenied(deniedPermissions);
                    }

                }
            break;

        }
    }


    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }


}

















