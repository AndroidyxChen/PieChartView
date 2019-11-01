package com.example.yanxu.piechartview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.yanxu.piechartview.util.JRDimensionUtil;


/**
 * @ClassName: PieChart
 * @Description: 自定义的总资产扇形图
 * @Author yanxu5
 * @Date: 2019/5/26
 */
public class PieChartView extends View {
    private float[] item;// 每一项的值
    private float total;// 总共的值
    private String[] colors;// 传过来的颜色
    private float[] itemsAngle;// 每一项所占的角度
    private float[] itemsBeginAngle;// 每一项的起始角度
    private float mRadius;// 半径
    private float animatedValue;// AnimatedValue
    private static final String[] DEFAULT_ITEMS_COLORS = {"#FF8200", "#EF4352"};// 默认颜色
    private float mStartX, mStartY, mCenterXY;// 起始值
    private Paint mPaint, mLinePaint;// paint
    private RectF mOval;// RectF
    private RectF mOvalMax;// RectF
    private int mTagCount;// 大于0的个数

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPieChart();
    }

    /**
     * 初始化
     */
    private void initPieChart() {
        mRadius = JRDimensionUtil.dp2px(getContext(), 110);
        mStartX = JRDimensionUtil.dp2px(getContext(), 110);
        mStartY = JRDimensionUtil.dp2px(getContext(), 110);
        mCenterXY = mRadius;
        float addRadius = JRDimensionUtil.dp2px(getContext(), 5);
        float leftTop = 0;
        float rightBottom = 2 * mRadius;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(JRDimensionUtil.dp2px(getContext(), 2));// 设置线宽
        mLinePaint.setColor(Color.parseColor("#FFFFFF"));
        mOval = new RectF(leftTop + addRadius, leftTop + addRadius, rightBottom - addRadius, rightBottom - addRadius);
        mOvalMax = new RectF(leftTop, leftTop, rightBottom, rightBottom); // 关键思路：逆向思维，实际上先定义的大扇形矩形，再反推的小扇形矩形
    }

    /**
     * 设置每一项的值
     *
     * @param item item
     */
    public void setItem(float[] item) {
        if (item != null && item.length > 0) {
            calculateTotal();
            refreshItemsAngles();
            colors = DEFAULT_ITEMS_COLORS;
        }
    }

    /**
     * 初始化所有
     *
     * @param item   item
     * @param colors 颜色
     */
    public void initSrc(float[] item, String[] colors) {
        this.item = item;
        mTagCount = 0;// 每次初始化重置个数
        for (float anItem : item) {
            if (anItem > 0) mTagCount++;
        }
        calculateTotal();
        if (total > 0) {
            setItem(item);
            setColors(colors);
            notifyDraw();
        } else if (total == 0) {
            for (int i = 0; i < item.length; i++) {
                item[i] = 1;// 当为0的时候将不会画圆
            }
            initSrc(item, colors);
        }
    }

    /**
     * 设置每一项的颜色
     *
     * @param colors 颜色
     */
    public void setColors(String[] colors) {
        if (colors != null && colors.length > 0) {
            this.colors = colors;
        }
    }

    /**
     * onDraw方法
     *
     * @param canvas canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (item == null || item.length == 0) {// 如无数据
            // 画外圆
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.parseColor("#F5F5F5"));
            canvas.drawCircle(mCenterXY, mCenterXY, JRDimensionUtil.dp2px(getContext(), 105), mPaint);
            // 画内圆
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.parseColor("#FFFFFF"));
            canvas.drawCircle(mCenterXY, mCenterXY, JRDimensionUtil.dp2px(getContext(), 85), mPaint);
        } else {
            // 画扇形
            for (int i = 0; i < item.length; i++) {
                mPaint.setColor(Color.parseColor(colors[i]));
                if (Math.min(itemsAngle[i], animatedValue - 90 - itemsBeginAngle[i]) >= 0) {
                    canvas.drawArc(mOval, itemsBeginAngle[i], Math.min(itemsAngle[i], animatedValue - 90 - itemsBeginAngle[i]), true, mPaint);
                }
            }
            // 画变大的扇形 //TODO 占比较大模块放大的效果暂时取消
            /*if (animatedValue == 360 && !isEquality(item)) {
                int i = compareNumber(item);
                mPaint.setColor(Color.parseColor(colors[i]));
                canvas.drawArc(mOvalMax, itemsBeginAngle[i], Math.min(itemsAngle[i], animatedValue - 90 - itemsBeginAngle[i]), true, mPaint);
            }*/
            // 画间隔白线
            for (int i = 0; i < item.length; i++) {
                if (item.length > 1 && mTagCount > 1)
                    canvas.drawLine(mStartX, mStartY, mStartX + (float) Math.sin(Math.toRadians(90 + itemsBeginAngle[i])) * mStartX, mStartY - (float) Math.cos(Math.toRadians(90 + itemsBeginAngle[i])) * mStartY, mLinePaint);
            }
            // 画内圆
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.parseColor("#FFFFFF"));
            canvas.drawCircle(mCenterXY, mCenterXY, JRDimensionUtil.dp2px(getContext(), 85), mPaint);
        }
    }

    /**
     * 开始绘制
     */
    public void startDraw() {
        if (item != null && item.length > 0 && colors != null && colors.length > 0) {
            initAnimator();
        }
    }

    /**
     * 初始化动画
     */
    private void initAnimator() {
        ValueAnimator anim = ValueAnimator.ofFloat(0, 360);
        anim.setDuration(1500);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }

    /**
     * 计算总数
     */
    private void calculateTotal() {
        total = 0;
        for (float i : item) {
            total += i;
        }
    }

    /**
     * 根据每个item的大小，获得item所占的角度和起始角度
     */
    private void refreshItemsAngles() {
        if (item != null && item.length > 0) {
            float[] itemsRate = new float[item.length];// 每一块占的比例
            itemsBeginAngle = new float[item.length];// 每一个角度临界点
            itemsAngle = new float[item.length];// 每一个角度临界点
            float beginAngle = -90;// 初始角度-90度
            for (int i = 0; i < item.length; i++) {
                itemsRate[i] = (float) (item[i] * 1.0 / total * 1.0);
            }
            for (int i = 0; i < itemsRate.length; i++) {
                if (i >= 1) {
                    beginAngle = 360 * itemsRate[i - 1] + beginAngle;
                }
                itemsBeginAngle[i] = beginAngle;
                itemsAngle[i] = 360 * itemsRate[i];
            }
        }
    }

    /**
     * 通知开始绘画
     */
    public void notifyDraw() {
        invalidate();
    }

    /**
     * 控件可获得的空间
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float widthHeight = 2 * (mRadius);
        setMeasuredDimension((int) widthHeight, (int) widthHeight);
    }

    /**
     * 判断各组成是否相等
     *
     * @param item 资产组成
     * @return 是否相等
     */
    private boolean isEquality(float[] item) {
        for (int i = 1; i < item.length; i++) {
            if (item[i] != item[0]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 返回最大值的索引
     *
     * @param item 资产组成
     * @return 最大的资产的角标
     */
    private int compareNumber(float[] item) {
        float max = item[0];
        int maxPosition = 0;
        for (int i = 1; i < item.length; i++) {
            if (item[i] > max) {
                max = item[i];
                maxPosition = i;
            }
        }
        return maxPosition;
    }

}
