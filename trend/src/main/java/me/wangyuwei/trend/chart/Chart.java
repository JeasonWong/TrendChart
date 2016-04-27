package me.wangyuwei.trend.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import me.wangyuwei.trend.common.AxisPaint;
import me.wangyuwei.trend.common.LongitudeLatitudePaint;
import me.wangyuwei.trend.common.Quadrant;
import me.wangyuwei.trend.interf.IChart;
import me.wangyuwei.trend.interf.IQuadrant;

/**
 * 作者： 巴掌 on 16/4/25 23:24
 */
public class Chart extends View implements IChart {

    private float borderWidth = 1f;
    protected double prevClosePrice;
    protected int minDisplayNum = 10;
    protected int maxDisplayNum = 500;
    protected int displayFrom = 0;
    protected int displayNumber = 0;

    protected PointF touchPoint;
    /* 经线数 */
    private int longitudeNum = 5;
    /* 纬线数 */
    private int latitudeNum = 5;

    protected IQuadrant dataQuadrant = new Quadrant(this) {
        @Override
        public float getQuadrantWidth() {
            return getMeasuredWidth() - getBorderWidth() * 2;
        }

        @Override
        public float getQuadrantHeight() {
            return getMeasuredHeight() - getBorderWidth() * 2;
        }

        @Override
        public float getQuadrantStartX() {
            return getBorderWidth();
        }

        @Override
        public float getQuadrantStartY() {
            return getBorderWidth();
        }
    };

    public Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // for performance tracking
    protected long totalTime = 0;
    protected long drawCycles = 0;
    protected long starttime = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        starttime = System.currentTimeMillis();

        drawLatitudeLine(canvas);

        drawLongitudeLine(canvas);

        drawXAxis(canvas);

        drawYAxis(canvas);

    }

    /* 绘制X轴 */
    protected void drawXAxis(Canvas canvas) {
        canvas.drawLine(dataQuadrant.getQuadrantStartX(), dataQuadrant.getQuadrantHeight(), dataQuadrant.getQuadrantWidth(), dataQuadrant.getQuadrantHeight(), new AxisPaint());
        canvas.drawLine(dataQuadrant.getQuadrantStartX(), dataQuadrant.getQuadrantStartY(), dataQuadrant.getQuadrantWidth(), dataQuadrant.getQuadrantStartY(), new AxisPaint());
    }

    /* 绘制Y轴 */
    protected void drawYAxis(Canvas canvas) {
        canvas.drawLine(dataQuadrant.getQuadrantStartX(), getBorderWidth(), dataQuadrant.getQuadrantStartX(), dataQuadrant.getQuadrantHeight(), new AxisPaint());
        canvas.drawLine(dataQuadrant.getQuadrantWidth(), getBorderWidth(), dataQuadrant.getQuadrantWidth(), dataQuadrant.getQuadrantHeight(), new AxisPaint());
    }

    /* 绘制经线 */
    protected void drawLongitudeLine(Canvas canvas) {
        float postOffset = longitudePostOffset();
        float offset = longitudeOffset();
        for (int i = 0; i < longitudeNum; i++) {
            Path path = new Path();
            path.moveTo(offset + i * postOffset, dataQuadrant.getQuadrantStartX());
            path.lineTo(offset + i * postOffset, dataQuadrant.getQuadrantHeight());
            canvas.drawPath(path, new LongitudeLatitudePaint());
        }
    }

    protected void drawLatitudeLine(Canvas canvas) {

        LongitudeLatitudePaint paint = new LongitudeLatitudePaint();
        float postOffset = (dataQuadrant.getQuadrantPaddingHeight() - 10) / (latitudeNum - 1);
        float offset = dataQuadrant.getQuadrantPaddingHeight();
        float startFrom = dataQuadrant.getQuadrantStartX();

        for (int i = 0; i < latitudeNum; i++) {
            Path path = new Path();
            path.moveTo(startFrom, offset - i * postOffset);
            path.lineTo(startFrom + dataQuadrant.getQuadrantWidth(), offset - i * postOffset);
            if (i == (latitudeNum - 1) / 2) {
                paint.setColor(Color.parseColor("#1478f0"));
            } else {
                paint.setColor(Color.BLACK);
            }
            canvas.drawPath(path, paint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    public float longitudePostOffset() {
        float stickWidth = dataQuadrant.getQuadrantPaddingWidth() / displayNumber;
        return (this.dataQuadrant.getQuadrantPaddingWidth() - stickWidth) / (longitudeNum - 1);
    }

    public float longitudeOffset() {
        float stickWidth = dataQuadrant.getQuadrantPaddingWidth() / displayNumber;
        return dataQuadrant.getQuadrantPaddingStartX() + stickWidth / 2;
    }

    /**
     * 每个元素的宽度/间隔宽度
     *
     * @return
     */
    public float getElementWidth(){
        return dataQuadrant.getQuadrantPaddingWidth() / displayNumber;
    }

    public void touchDown(PointF pt) {
        this.touchPoint = pt;
        this.postInvalidate();
    }

    public void touchMoved(PointF pt) {
        this.touchPoint = pt;
        this.postInvalidate();
    }

    public void touchUp() {
        this.touchPoint = null;
        this.postInvalidate();
    }

    @Override
    public Chart withPrevClosePrice(double prevClosePrice) {
        this.prevClosePrice = prevClosePrice;
        return this;
    }

    @Override
    public Chart withMinDisplayNum(int minDisplayNum) {
        this.minDisplayNum = minDisplayNum;
        return this;
    }

    @Override
    public Chart withMaxDisplayNum(int maxDisplayNum) {
        this.maxDisplayNum = maxDisplayNum;
        return this;
    }

    @Override
    public Chart withDisplayFrom(int displayFrom) {
        this.displayFrom = displayFrom;
        return this;
    }

    @Override
    public Chart withDisplayNumber(int displayNumber) {
        this.displayNumber = displayNumber;
        return this;
    }

    public float getBorderWidth() {
        return borderWidth;
    }
}
