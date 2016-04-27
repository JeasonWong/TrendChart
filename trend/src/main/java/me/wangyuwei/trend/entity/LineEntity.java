package me.wangyuwei.trend.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 巴掌 on 16/4/25 23:27
 */
public class LineEntity<T> {

    /* 线表示数据 */
    private List<T> lineData;
    /* 线的标题，用于标识别这条线 */
    private String title;
    /* 线的颜色 */
    private int lineColor;
    /* 线条宽度 */
    private float lineWidth = 3f;

    public List<T> getLineData() {
        return lineData;
    }

    public void setLineData(List<T> lineData) {
        this.lineData = lineData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }
}
