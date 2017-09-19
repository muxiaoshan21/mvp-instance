package com.xk.mvp.listener;

import java.util.List;

/**
 * Created by xk on 2017/9/19.
 * 权限申请回调的接口
 */

public interface PermissionListener {

    void onGranted();

    void onDenied(List<String> deniedPermissionsList);

}
