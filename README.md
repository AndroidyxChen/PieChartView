# PieChartView
安卓自定义View之带动画的饼状图
##### 因为是金融类的app，包含有很多资产数据，需求是使用饼状图展示总资产的不同资产组成，不同资产间有白色间隔线，较大的资产会有放大的动画效果，整体进行动画绘制展示，效果如下：
![image](https://img-blog.csdnimg.cn/20191101162728360.gif)
##### 1.在XML中进行引用
```
<!-- 扇形图 -->
    <com.example.yanxu.piechartview.view.PieChartView
        android:id="@+id/pie_chart_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```
##### 2.在项目中的使用
```
private float[] ratios = {0.1f, 0.2f, 0.3f, 0.4f};
private String[] colors = {"#FFB864", "#6F7CFF", "#884EDB", "#90E3E1"};

    /**
     * 初始化data
     */
    private void initData() {
        mPieChartView.initSrc(ratios, colors);
        mPieChartView.startDraw();
    }
```
