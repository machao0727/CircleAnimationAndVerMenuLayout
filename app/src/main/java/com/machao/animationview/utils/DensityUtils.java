package com.machao.animationview.utils;

import android.content.Context;

/**
 * Created by chao on 2016/10/11.
 */

public class DensityUtils {
    //    dp:设备独立像素
//    px:像素
//    dp=px/设备密度
    public static int dpTopx(float dp, Context context){
        float density = context.getResources().getDisplayMetrics().density;//获取屏幕的设备密度
        int px= (int) (dp*density+0.5f);//加0.5可以四舍五入
        return px;
    }
    public static float pxTodp(int px, Context context){
        float density = context.getResources().getDisplayMetrics().density;
        float dp = px / density;
        return dp;
    }
}

