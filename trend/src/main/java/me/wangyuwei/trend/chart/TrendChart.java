package me.wangyuwei.trend.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.List;

import me.wangyuwei.trend.common.AxisPaint;
import me.wangyuwei.trend.common.TouchGestureDetector;
import me.wangyuwei.trend.common.TrendAreaPaint;
import me.wangyuwei.trend.common.VerticalLinePaint;
import me.wangyuwei.trend.entity.DateValueEntity;
import me.wangyuwei.trend.entity.LineEntity;

/**
 * 作者： 巴掌 on 16/4/25 23:24
 */
public class TrendChart extends Chart {

    /* Y的最大表示值 */
    private double maxValue;
    /* Y的最小表示值 */
    private double minValue;

    private float circleX;
    private float circleY;

    private List<LineEntity<DateValueEntity>> linesData;

    private TouchGestureDetector touchGestureDetector = new TouchGestureDetector();

    public TrendChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TrendChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calcDataValueRange();

        drawLines(canvas);
        drawAreas(canvas);

        drawVerticalLine(canvas);
        drawCrossCircle(canvas);

        long drawtime = (System.currentTimeMillis() - starttime);
        totalTime += drawtime;
        drawCycles += 1;
        long average = totalTime / drawCycles;


        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setColor(Color.RED);
        canvas.drawText("Drawtime: " + drawtime + " ms, average: " + average + " ms, cycles: "
                + drawCycles, dataQuadrant.getQuadrantStartX() + 20, dataQuadrant.getQuadrantHeight() - 20, paint);
//        Log.i("@=>Drawtime", "Drawtime: " + drawtime + " ms, average: " + average + " ms, cycles: "
//                + drawCycles);

    }

    protected void drawLines(Canvas canvas) {
        if (null == this.getLinesData()) {
            return;
        }
        float lineLength;
        float startX;

        for (int i = 0; i < getLinesData().size(); i++) {
            LineEntity<DateValueEntity> line = getLinesData().get(i);
            if (line == null) {
                continue;
            }
            List<DateValueEntity> lineData = line.getLineData();
            if (lineData == null) {
                continue;
            }
            Paint paintStock = new Paint();
            paintStock.setColor(line.getLineColor());
            paintStock.setStrokeWidth(line.getLineWidth());
            paintStock.setAntiAlias(true);
            lineLength = (dataQuadrant.getQuadrantPaddingWidth() / displayNumber);
            startX = dataQuadrant.getQuadrantPaddingStartX() + lineLength / 2;
            PointF ptFirst = null;
            for (int j = displayFrom; j < displayFrom + displayNumber; j++) {

                float value = lineData.get(j).getValue();
                float valueY = (float) ((1f - (value - minValue)
                        / (maxValue - minValue)) * dataQuadrant.getQuadrantPaddingHeight())
                        + dataQuadrant.getQuadrantPaddingStartY();

                if (j > displayFrom) {
                    if (ptFirst == null) continue;
                    canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY,
                            paintStock);
                }

                ptFirst = new PointF(startX, valueY);
                startX = startX + lineLength;
            }
        }
    }

    protected void drawAreas(Canvas canvas) {
        if (null == getLinesData()) {
            return;
        }

        for (int i = 0; i < getLinesData().size(); i++) {

            LineEntity<DateValueEntity> line = getLinesData().get(i);

            if (line.getTitle().equals("TREND")) {
                List<DateValueEntity> lineData = line.getLineData();

                if (lineData == null) {
                    continue;
                }

                float lineLength = (dataQuadrant.getQuadrantPaddingWidth() / displayNumber);
                float startX = dataQuadrant.getQuadrantPaddingStartX() + lineLength / 2;

                Path linePath = new Path();
                for (int j = displayFrom; j < displayFrom + displayNumber; j++) {
                    if (j < 0) continue;
                    float value = lineData.get(j).getValue();
                    float valueY = (float) ((1f - (value - minValue)
                            / (maxValue - minValue)) * dataQuadrant.getQuadrantPaddingHeight())
                            + dataQuadrant.getQuadrantPaddingStartY();
                    if (j == displayFrom) {
                        linePath.moveTo(startX, dataQuadrant.getQuadrantPaddingEndY());
                        linePath.lineTo(startX, valueY);
                    } else if (j == displayFrom + displayNumber - 1) {
                        linePath.lineTo(startX, valueY);
                        linePath.lineTo(startX, dataQuadrant.getQuadrantPaddingEndY());
                    } else {
                        linePath.lineTo(startX, valueY);
                    }
                    startX = startX + lineLength;
                }
                linePath.close();
                canvas.drawPath(linePath, new TrendAreaPaint(getMeasuredHeight()));
            }

        }
    }

    protected void drawVerticalLine(Canvas canvas) {

        if (touchPoint == null) return;

        calcCircleCoordinate(touchPoint.x);

        if (circleX < 0) return;

        float lineVLength = dataQuadrant.getQuadrantHeight() + getBorderWidth();

        canvas.drawLine(circleX, getBorderWidth(), circleX, lineVLength, new VerticalLinePaint());

    }

    protected void drawCrossCircle(Canvas canvas) {

        if (touchPoint == null) return;

        if (circleX < 0) return;

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#323232"));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.drawCircle(circleX, circleY, 14, paint);
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawCircle(circleX, circleY, 7, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return touchGestureDetector.onTouchEvent(event, this);
    }

    private void calcCircleCoordinate(float positionX) {
        if (positionX < dataQuadrant.getQuadrantPaddingStartX())
            positionX = dataQuadrant.getQuadrantPaddingStartX();
        else if (positionX > dataQuadrant.getQuadrantPaddingEndX())
            positionX = dataQuadrant.getQuadrantPaddingEndX();

        circleX = positionX;

        //TODO linesData.get(0) 这里是个坑，勿踩，大家自己根据业务来
        List<DateValueEntity> lineData = linesData.get(0).getLineData();
        float value = lineData.get(getAxisxIndex(circleX)).getValue();

        circleY = (float) ((1f - (value - minValue)
                / (maxValue - minValue)) * dataQuadrant.getQuadrantPaddingHeight())
                + dataQuadrant.getQuadrantPaddingStartY();
    }

    private int getAxisxIndex(Object value) {

        float graduate = Float.valueOf(getAxisXGraduate(value));
        int index = (int) Math.floor(graduate * displayNumber);

        if (index >= displayNumber) {
            index = displayNumber - 1;
        } else if (index < 0) {
            index = 0;
        }
        index = index + displayFrom;

        return index;

    }

    /* 计算X轴上显示的坐标刻度值 */
    private String getAxisXGraduate(Object value) {
        float valueLength = ((Float) value).floatValue() - dataQuadrant.getQuadrantPaddingStartX();
        return String.valueOf(valueLength / dataQuadrant.getQuadrantPaddingWidth());
    }

    private void calcDataValueRange() {

        double maxValue = Double.MIN_VALUE;
        double minValue = Double.MAX_VALUE;

        for (int i = 0; i < this.getLinesData().size(); i++) {
            LineEntity<DateValueEntity> line = this.getLinesData().get(i);

            if (null != line && line.getLineData().size() > 0) {

                for (int j = displayFrom; j < displayFrom + displayNumber; j++) {

                    DateValueEntity lineData = line.getLineData().get(j);

                    if (lineData.getValue() < 0) continue;

                    if (lineData.getValue() < minValue) {
                        minValue = lineData.getValue();
                    }

                    if (lineData.getValue() > maxValue) {
                        maxValue = lineData.getValue();
                    }
                }

            }
        }

        if (Math.abs(maxValue - prevClosePrice) > Math.abs(minValue - prevClosePrice)) {
            minValue = prevClosePrice - Math.abs(maxValue - prevClosePrice);
        } else {
            maxValue = prevClosePrice + Math.abs(minValue - prevClosePrice);
        }

        this.maxValue = maxValue;
        this.minValue = minValue;

    }

    public List<LineEntity<DateValueEntity>> getLinesData() {
        return linesData;
    }

    public void setLinesData(List<LineEntity<DateValueEntity>> linesData) {
        this.linesData = linesData;
    }

}
