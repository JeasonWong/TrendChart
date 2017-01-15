package me.wangyuwei.trendchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 巴掌
 * https://github.com/JeasonWong
 */

public class TrendView extends ChartView {

  private int mDisplayFrom;
  private int mDisplayNumber;
  //昨收价格
  private double mPrvClose;
  //当前canvas中最大值
  private double mMaxValue;
  //当前canvas中最小值
  private double mMinValue;
  //线条信息
  private Line mLine;
  //手指当前坐标
  private float mCurrentX = -1;

  private Path mPath;
  private Paint mTrendLinePaint;
  private Paint mAreaPaint;
  private Paint mVerticalPaint;

  public TrendView(Context context) {
    super(context);
  }

  public TrendView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TrendView(Context context, AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void setupPaint() {
    super.setupPaint();
    mPath = new Path();
    mTrendLinePaint = new Paint();
    mTrendLinePaint.setAntiAlias(true);
    mTrendLinePaint.setStyle(Paint.Style.STROKE);
    mAreaPaint = new Paint();
    mVerticalPaint = new Paint();
    mVerticalPaint.setAntiAlias(true);
    mVerticalPaint.setColor(Color.RED);
    mVerticalPaint.setStrokeWidth(dp2px(1.5f));
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    Shader shader = new LinearGradient(0, 0, 0, h, new int[] { 0xA0688FDB, 0x20688FDB }, null,
        Shader.TileMode.REPEAT);
    mAreaPaint.setShader(shader);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        mCurrentX = filterX(event.getX());
        break;
      case MotionEvent.ACTION_UP:
        mCurrentX = -1;
        break;
      case MotionEvent.ACTION_MOVE:
        mCurrentX = filterX(event.getX());
        break;
    }
    invalidate();
    return true;
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    calcMaxNMin();
    //绘制分时折线
    drawTrend(canvas);
    drawArea(canvas);

    if (mCurrentX > 0) {
      drawVerticalLine(canvas);
      drawVerticalCircle(canvas);
    }
  }

  /**
   * 绘制折线
   */
  private void drawTrend(Canvas canvas) {
    mPath.reset();
    for (int i = mDisplayFrom + 1; i < mDisplayFrom + mDisplayNumber; i++) {
      if (i == mDisplayFrom + 1) {
        mPath.moveTo(getValueX(i - 1), getValueY(i - 1));
      }
      mPath.lineTo(getValueX(i), getValueY(i));
    }
    canvas.drawPath(mPath, mTrendLinePaint);
  }

  /**
   * 绘制从上往下的阴影
   */
  private void drawArea(Canvas canvas) {
    mPath.reset();
    for (int i = mDisplayFrom; i < mDisplayFrom + mDisplayNumber; i++) {
      if (i == mDisplayFrom) {
        mPath.moveTo(getValueX(i), mQuadrant.getQuadrantPaddingEndY());
        mPath.lineTo(getValueX(i), getValueY(i));
      } else if (i == mDisplayFrom + mDisplayNumber - 1) {
        mPath.lineTo(getValueX(i), getValueY(i));
        mPath.lineTo(getValueX(i), mQuadrant.getQuadrantPaddingEndY());
        mPath.lineTo(getValueX(mDisplayFrom), mQuadrant.getQuadrantPaddingEndY());
      } else {
        mPath.lineTo(getValueX(i), getValueY(i));
      }
    }
    canvas.drawPath(mPath, mAreaPaint);
  }

  /**
   * 绘制竖线
   */
  private void drawVerticalLine(Canvas canvas) {
    canvas.drawLine(mCurrentX, mQuadrant.getQuadrantStartY(), mCurrentX,
        mQuadrant.getQuadrantEndY(), mVerticalPaint);
  }

  /**
   * 绘制小圆圈
   */
  private void drawVerticalCircle(Canvas canvas) {
    canvas.drawCircle(mCurrentX, getValueY(getIndex()), dp2px(4), mVerticalPaint);
  }

  /**
   * 计算当前canvas中的最大值和最小值
   */
  private void calcMaxNMin() {
    double maxValue = Double.MIN_VALUE;
    double minValue = Double.MAX_VALUE;
    for (int i = mDisplayFrom; i < mDisplayFrom + mDisplayNumber; i++) {
      double value = getValue(i);
      if (value < minValue) {
        minValue = value;
      }
      if (value > maxValue) {
        maxValue = value;
      }
    }
    double diff = Math.abs(maxValue - mPrvClose) > Math.abs(minValue - mPrvClose) ? Math.abs(
        maxValue - mPrvClose) : Math.abs(minValue - mPrvClose);
    mMaxValue = mPrvClose + diff;
    mMinValue = mPrvClose - diff;
  }

  /**
   * 计算第index个数据点的x坐标
   * X = （当前index – from）*（象限宽度 / (number-1)）+startX
   */
  private float getValueX(int index) {
    return (index - mDisplayFrom) * (mQuadrant.getQuadrantPaddingWidth() / (mDisplayNumber - 1))
        + mQuadrant.getQuadrantPaddingStartX();
  }

  /**
   * 计算第index个数据点的y坐标
   * Y = (1 - (value-min) / (max-min)) * height + startY
   */
  private float getValueY(int index) {
    return (float) (1 - ((getValue(index) - mMinValue) / (mMaxValue - mMinValue)))
        * mQuadrant.getQuadrantPaddingHeight()
        + mQuadrant.getQuadrantPaddingStartY();
  }

  /**
   * 第index个数据点所对应的价格
   */
  private double getValue(int index) {
    return mLine.getLineData().get(index).getValue();
  }

  /**
   * 过滤x坐标
   */
  private float filterX(float x) {
    if (x < mQuadrant.getQuadrantPaddingStartX()) {
      x = mQuadrant.getQuadrantPaddingStartX();
    } else if (x > mQuadrant.getQuadrantPaddingEndX()) {
      x = mQuadrant.getQuadrantPaddingEndX();
    }
    return x;
  }

  /**
   * 获得mCurrentX所对应的index
   */
  private int getIndex() {
    //ratio = (x - startX) / width
    float ratio =
        (mCurrentX - mQuadrant.getQuadrantPaddingStartX()) / mQuadrant.getQuadrantPaddingWidth();
    //index = ratio * number + from
    int index = (int) (ratio * mDisplayNumber) + mDisplayFrom;
    if (index < mDisplayFrom) {
      index = mDisplayFrom;
    } else if (index > mDisplayFrom + mDisplayNumber - 1) {
      index = mDisplayFrom + mDisplayNumber - 1;
    }
    return index;
  }

  public TrendView withDisplayFrom(int from) {
    mDisplayFrom = from;
    return this;
  }

  public TrendView withDisplayNumber(int number) {
    mDisplayNumber = number;
    return this;
  }

  public TrendView withPrevClose(double prevClose) {
    mPrvClose = prevClose;
    return this;
  }

  public TrendView withLine(Line line) {
    mLine = line;
    mTrendLinePaint.setColor(line.getLineColor());
    mTrendLinePaint.setStrokeWidth(line.getLineWidth());
    return this;
  }

  public void show() {
    invalidate();
    setVisibility(VISIBLE);
  }
}
