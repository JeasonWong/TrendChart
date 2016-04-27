package me.wangyuwei.trend.interf;

/**
 * 作者： 巴掌 on 16/4/26 09:06
 */
public interface IQuadrant {

    static final float DEFAULT_PADDING_TOP = 5f;
    static final float DEFAULT_PADDING_BOTTOM = 5f;
    static final float DEFAULT_PADDING_LEFT = 5f;
    static final float DEFAULT_PADDING_RIGHT = 5f;

    float getPaddingTop();

    float getPaddingLeft();

    float getPaddingBottom();

    float getPaddingRight();

    void setPaddingTop(float value);

    void setPaddingLeft(float value);

    void setPaddingBottom(float value);

    void setPaddingRight(float value);

    float getQuadrantWidth();

    float getQuadrantHeight();

    float getQuadrantStartX();

    float getQuadrantStartY();

    float getQuadrantEndX();

    float getQuadrantEndY();

    float getQuadrantPaddingStartX();

    float getQuadrantPaddingEndX();

    float getQuadrantPaddingStartY();

    float getQuadrantPaddingEndY();

    float getQuadrantPaddingWidth();

    float getQuadrantPaddingHeight();

}
