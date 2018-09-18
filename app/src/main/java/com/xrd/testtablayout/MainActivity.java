package com.xrd.testtablayout;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] titles = {"条目1", "条目2","条目3", "条目4","我是条目5", "条目6","条目7", "条目8",};
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        initView();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            fragments.add(new MyFragment());
        }
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.vp);
        VpAdapter vpAdapter = new VpAdapter(getSupportFragmentManager());
        viewPager.setAdapter(vpAdapter);
        tabLayout.setupWithViewPager(viewPager);
        vpAdapter.getFragment(fragments);
        for (int i = 0; i < fragments.size(); i++) {
            tabLayout.getTabAt(i).setCustomView(getCustomView(i));
        }

        TabUtils.getInstance().initEvent(tabLayout);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                TabUtils.getInstance().setIndicator(tabLayout,0,0);
            }
        });

    }

    private View getCustomView(final int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item, tabLayout, false);
        ImageView ivTab = (ImageView) view.findViewById(R.id.iv_tab);
        TextView tvTab = (TextView) view.findViewById(R.id.tv_tab);
        tvTab.setText(titles[position]);
        if(position==0){
            tvTab.setTextColor(Color.parseColor("#0EA73C"));
            ivTab.setVisibility(View.VISIBLE);
        }else{
            tvTab.setTextColor(Color.parseColor("#7f7f7f"));
            ivTab.setVisibility(View.INVISIBLE);
            TabUtils.getInstance().setAnim(view,1,0.8f);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(position);
            }
        });
        return view;
    }
}
