package me.wangyuwei.trendchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * date： 2017/1/7 17:58
 */

public class PathView extends View {

  private Paint mPaint;
  private Path mPath;

  public PathView(Context context) {
    super(context);
    initView();
  }

  public PathView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView();
  }

  private void initView() {
    mPaint = new Paint();
    //抗锯齿
    mPaint.setAntiAlias(true);
    //设置成红色
    mPaint.setColor(Color.RED);
    //设置宽度
    mPaint.setStrokeWidth(10);
    //设置成空心
    mPaint.setStyle(Paint.Style.STROKE);

    mPath = new Path();

  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    mPath.reset();
    mPath.moveTo(100, 500);
    mPath.lineTo(200, 300);
    mPath.lineTo(300, 600);
    mPath.lineTo(400, 200);
    mPath.lineTo(600, 200);
    canvas.drawPath(mPath, mPaint);

  }
}
