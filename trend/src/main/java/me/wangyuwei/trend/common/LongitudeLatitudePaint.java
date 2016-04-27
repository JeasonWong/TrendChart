package me.wangyuwei.trend.common;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

/**
 * 作者： 巴掌 on 16/4/27 15:25
 */
public class LongitudeLatitudePaint extends Paint {

    public LongitudeLatitudePaint() {
        setStyle(Paint.Style.STROKE);
        setColor(Color.BLACK);
        setStrokeWidth(2);
        setAntiAlias(true);
        setPathEffect(new DashPathEffect(new float[]{12, 6, 12, 6}, 1));
    }
}
