package com.xk.mvp.utils;

import java.util.List;

/**
 * Created by xk on 2017/9/21.
 */

public class ListUtils {

    public static boolean isEmpty(List list){
        if (list == null){
            return true;
        }
        return list.size() == 0;
    }

}
