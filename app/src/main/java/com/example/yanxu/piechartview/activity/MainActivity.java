package com.example.yanxu.piechartview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yanxu.piechartview.R;
import com.example.yanxu.piechartview.view.PieChartView;

public class MainActivity extends AppCompatActivity {

    private PieChartView mPieChartView;
    private float[] ratios = {0.1f, 0.2f, 0.3f, 0.4f};
    private String[] colors = {"#FFB864", "#6F7CFF", "#884EDB", "#90E3E1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mPieChartView = findViewById(R.id.pie_chart_view);
    }

    /**
     * 初始化data
     */
    private void initData() {
        mPieChartView.initSrc(ratios, colors);
        mPieChartView.startDraw();
    }

}
