package me.wangyuwei.trend.common;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

/**
 * 作者： 巴掌 on 16/4/27 15:34
 */
public class TrendAreaPaint extends Paint {

    public TrendAreaPaint(int height) {
        Shader shader = new LinearGradient(0, 0, 0, height,
                new int[]{Color.parseColor("#A06289E4"), Color.parseColor("#206289E4")},
                null, Shader.TileMode.REPEAT);
        setShader(shader);
        setAntiAlias(true);

    }
}
