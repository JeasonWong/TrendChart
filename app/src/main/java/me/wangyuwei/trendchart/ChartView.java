package me.wangyuwei.trendchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 巴掌
 * https://github.com/JeasonWong
 */

public class ChartView extends View {

  //纬线数量
  private final int LATITUDE_NUM = 5;
  //经线数量
  private final int LONGITUDE_NUM = 5;

  private Paint mBorderPaint;
  private Paint mLinePaint;

  protected Quadrant mQuadrant = new Quadrant(dp2px(5), dp2px(5), dp2px(5), dp2px(5)) {
    @Override public float getQuadrantStartX() {
      return dp2px(10);
    }

    @Override public float getQuadrantStartY() {
      return dp2px(10);
    }

    @Override public float getQuadrantWidth() {
      return getMeasuredWidth() - dp2px(10) * 2;
    }

    @Override public float getQuadrantHeight() {
      return getMeasuredHeight() - dp2px(10) * 2;
    }
  };

  public ChartView(Context context) {
    super(context);
    initView();
  }

  public ChartView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  public ChartView(Context context, AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView();
  }

  private void initView() {
    setupPaint();
  }

  /**
   * 创建画笔
   */
  protected void setupPaint() {
    mBorderPaint = new Paint();
    mBorderPaint.setAntiAlias(true);
    mBorderPaint.setColor(Color.BLACK);
    mLinePaint = new Paint();
    mLinePaint.setAntiAlias(true);
    mLinePaint.setColor(0xFFCCCCCC);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(measureSize(dp2px(200), widthMeasureSpec),
        measureSize(dp2px(300), heightMeasureSpec));
  }

  /**
   * 测量得到最后大小
   *
   * @param size 默认值
   * @param measureSpec 传递来的值
   * @return 计算之后的大小
   */
  private int measureSize(int size, int measureSpec) {
    int result = size;

    int specMode = MeasureSpec.getMode(measureSpec);
    int specSize = MeasureSpec.getSize(measureSpec);
    switch (specMode) {
      //具体的值
      case MeasureSpec.EXACTLY:
        result = specSize;
        break;
      //父控件给子View一个最大的特定值
      case MeasureSpec.AT_MOST:
        result = Math.min(size, specSize);
        break;
      //默认值
      case MeasureSpec.UNSPECIFIED:
        result = size;
    }

    return result;
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawLatitudeBorder(canvas);
    drawLongitudeBorder(canvas);
    drawLatitudeLine(canvas);
    drawLongitudeLine(canvas);
  }

  /**
   * 绘制纬线边框（上、下）
   */
  private void drawLatitudeBorder(Canvas canvas) {
    canvas.drawLine(mQuadrant.getQuadrantStartX(), mQuadrant.getQuadrantStartY(),
        mQuadrant.getQuadrantEndX(), mQuadrant.getQuadrantStartY(), mBorderPaint);
    canvas.drawLine(mQuadrant.getQuadrantStartX(), mQuadrant.getQuadrantEndY(),
        mQuadrant.getQuadrantEndX(), mQuadrant.getQuadrantEndY(), mBorderPaint);
  }

  /**
   * 绘制经线边框（左、右）
   */
  private void drawLongitudeBorder(Canvas canvas) {
    canvas.drawLine(mQuadrant.getQuadrantStartX(), mQuadrant.getQuadrantStartY(),
        mQuadrant.getQuadrantStartX(), mQuadrant.getQuadrantEndY(), mBorderPaint);
    canvas.drawLine(mQuadrant.getQuadrantEndX(), mQuadrant.getQuadrantStartY(),
        mQuadrant.getQuadrantEndX(), mQuadrant.getQuadrantEndY(), mBorderPaint);
  }

  /**
   * 绘制纬线
   */
  private void drawLatitudeLine(Canvas canvas) {
    float offset =
        (mQuadrant.getQuadrantPaddingEndY() - mQuadrant.getQuadrantPaddingStartY()) / (LATITUDE_NUM
            - 1);
    for (int i = 0; i < LATITUDE_NUM; i++) {
      canvas.drawLine(mQuadrant.getQuadrantStartX(),
          offset * i + mQuadrant.getQuadrantPaddingStartY(), mQuadrant.getQuadrantEndX(),
          offset * i + mQuadrant.getQuadrantPaddingStartY(), mLinePaint);
    }
  }

  /**
   * 绘制经线
   */
  private void drawLongitudeLine(Canvas canvas) {
    float offset = (mQuadrant.getQuadrantPaddingEndX() - mQuadrant.getQuadrantPaddingStartX()) / (
        LONGITUDE_NUM - 1);
    for (int i = 0; i < LONGITUDE_NUM; i++) {
      canvas.drawLine(offset * i + mQuadrant.getQuadrantPaddingStartX(),
          mQuadrant.getQuadrantStartY(), offset * i + mQuadrant.getQuadrantPaddingStartX(),
          mQuadrant.getQuadrantEndY(), mLinePaint);
    }
  }

  protected int dp2px(float dpValue) {
    final float scale = getContext().getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }
}
