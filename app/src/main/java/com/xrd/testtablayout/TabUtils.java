package com.xrd.testtablayout;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by user on 2018/9/18.
 */

public class TabUtils {
    private static TabUtils instance;

    private TabUtils(){}
    public static TabUtils getInstance(){
        if(instance==null){
            synchronized (TabUtils.class){
                if(instance==null){
                    instance=new TabUtils();
                }
            }
        }
        return instance;
    }
    public void initEvent(TabLayout tabLayout) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabStatus(tab,true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabStatus(tab,false);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    /**
     * 设置tab的属性动画
     * @param view
     * @param start
     * @param end
     */
    public void setAnim(View view,float start,float end){

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", start, end);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", start, end);
        scaleY.start();
        scaleX.start();
    }
    /**
     * 改变tab状态
     * @param tab
     * @param selected
     */
    private void changeTabStatus(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
        final ImageView imgTitle = (ImageView) view.findViewById(R.id.iv_tab);
        TextView txtTitle = (TextView) view.findViewById(R.id.tv_tab);
        imgTitle.setVisibility(View.VISIBLE);
        if (selected) {
            txtTitle.setTextColor(Color.parseColor("#0EA73C"));
            setAnim(view,0.8f,1);
        } else {
            txtTitle.setTextColor(Color.parseColor("#7f7f7f"));
            imgTitle.setVisibility(View.INVISIBLE);
            setAnim(view,1,0.8f);
        }
    }

    /**
     * 通过反射获取tabitem的宽度
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
