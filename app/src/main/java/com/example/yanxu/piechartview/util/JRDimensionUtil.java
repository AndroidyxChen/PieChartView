package com.example.yanxu.piechartview.util;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @ClassName: JRDimensionUtil
 * @Description: 尺寸工具类
 * @Author: yanxu5
 * @Date: 2019/9/28
 */

public class JRDimensionUtil {

    /**
     * 工具类说明(修改工具类时请完善该注释)：
     *
     * 1.类属性/常量：（暂无）
     *
     * 2.工具方法:
     * {@link JRDimensionUtil#getScreenWidth(Context)} 获取屏幕宽度
     * {@link JRDimensionUtil#getScreenHeight(Context)} 获取屏幕高度
     * {@link JRDimensionUtil#getStatusBarHeight(Context)} 获取系统状态栏高度
     * {@link JRDimensionUtil#dp2px(Context, float)} dp转px
     * {@link JRDimensionUtil#px2dp(Context, float)}} px转dp
     * {@link JRDimensionUtil#sp2px(Context, float)} sp转px
     * {@link JRDimensionUtil#px2sp(Context, float)} px转sp
     */

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取系统状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(@NonNull Context context) {
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resId);
    }

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(@NonNull Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5F);
    }

    /**
     * px转dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2dp(@NonNull Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5F);
    }

    /**
     * sp转px
     *
     * @param context
     * @param sp
     * @return
     */
    public static int sp2px(@NonNull Context context, float sp) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5F);
    }

    /**
     * px转sp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2sp(@NonNull Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scale + 0.5F);
    }
}
