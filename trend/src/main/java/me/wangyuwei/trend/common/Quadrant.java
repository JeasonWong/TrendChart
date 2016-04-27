package me.wangyuwei.trend.common;

import me.wangyuwei.trend.chart.Chart;
import me.wangyuwei.trend.interf.IQuadrant;

/**
 * 作者： 巴掌 on 16/4/26 15:13
 */
public abstract class Quadrant implements IQuadrant {

    protected Chart inChart;

    public Quadrant(Chart inChart) {
        this.inChart = inChart;
    }

    /* 轴线上边距 */
    protected float paddingTop = DEFAULT_PADDING_TOP;

    /* 轴线右边距 */
    protected float paddingLeft = DEFAULT_PADDING_LEFT;
    protected float paddingBottom = DEFAULT_PADDING_BOTTOM;

    /* 轴线右边距 */
    protected float paddingRight = DEFAULT_PADDING_RIGHT;

    public Chart getInChart() {
        return inChart;
    }

    public void setInChart(Chart inChart) {
        this.inChart = inChart;
    }

    @Override
    public float getPaddingTop() {
        return paddingTop;
    }

    @Override
    public void setPaddingTop(float paddingTop) {
        this.paddingTop = paddingTop;
    }

    @Override
    public float getPaddingLeft() {
        return paddingLeft;
    }

    @Override
    public void setPaddingLeft(float paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    @Override
    public float getPaddingBottom() {
        return paddingBottom;
    }

    @Override
    public void setPaddingBottom(float paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    @Override
    public float getPaddingRight() {
        return paddingRight;
    }

    @Override
    public void setPaddingRight(float paddingRight) {
        this.paddingRight = paddingRight;
    }

    public void setQuadrantPadding(float padding) {
        this.paddingTop = padding;
        this.paddingLeft = padding;
        this.paddingBottom = padding;
        this.paddingRight = padding;
    }

    public void setQuadrantPadding(float topnbottom, float leftnright) {
        this.paddingTop = topnbottom;
        this.paddingLeft = leftnright;
        this.paddingBottom = topnbottom;
        this.paddingRight = leftnright;
    }

    public void setQuadrantPadding(float top, float right, float bottom,
                                   float left) {
        this.paddingTop = top;
        this.paddingLeft = right;
        this.paddingBottom = bottom;
        this.paddingRight = left;
    }

    public float getQuadrantEndX() {
        return getQuadrantStartX() + getQuadrantWidth();
    }

    public float getQuadrantEndY() {
        return getQuadrantStartY() + getQuadrantHeight();
    }

    public float getQuadrantPaddingStartX() {
        return getQuadrantStartX() + paddingLeft;
    }

    public float getQuadrantPaddingEndX() {
        return getQuadrantEndX() - paddingRight;
    }

    public float getQuadrantPaddingStartY() {
        return getQuadrantStartY() + paddingTop;
    }

    public float getQuadrantPaddingEndY() {
        return getQuadrantEndY() - paddingBottom;
    }

    public float getQuadrantPaddingWidth() {
        return getQuadrantWidth() - paddingLeft
                - paddingRight;
    }

    public float getQuadrantPaddingHeight() {
        return getQuadrantHeight() - paddingTop
                - paddingBottom;
    }

}
