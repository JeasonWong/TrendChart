package me.wangyuwei.trend.common;

import android.graphics.PointF;
import android.view.MotionEvent;

import me.wangyuwei.trend.chart.Chart;

/**
 * 作者： 巴掌 on 16/4/27 10:23
 */
public class TouchGestureDetector {

    private PointF pointFTouch;

    public boolean onTouchEvent(MotionEvent event, Chart chart) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                pointFTouch = new PointF(event.getX(), event.getY());
                chart.touchDown(pointFTouch);

                break;
            case MotionEvent.ACTION_UP:

                pointFTouch = null;
                chart.touchUp();

                break;

            case MotionEvent.ACTION_MOVE:

                PointF pointF = new PointF(event.getX(), event.getY());
                if (calcDistance(pointF, pointFTouch) > chart.getElementWidth()){
                    pointFTouch = pointF;
                    chart.touchMoved(new PointF(event.getX(), event.getY()));
                }

                break;
        }
        return true;
    }

    /* 两点间X坐标距离 */
    protected float calcDistance(PointF start, PointF end) {
        return Math.abs(start.x - end.x);
    }

}
