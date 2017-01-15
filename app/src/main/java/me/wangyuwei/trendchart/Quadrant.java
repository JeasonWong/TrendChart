package me.wangyuwei.trendchart;

/**
 * 巴掌
 * https://github.com/JeasonWong
 */

public abstract class Quadrant {

  private float mPaddingLeft;
  private float mPaddingTop;
  private float mPaddingRight;
  private float mPaddingBottom;

  public Quadrant(float paddingLeft, float paddingTop, float paddingRight, float paddingBottom) {
    mPaddingLeft = paddingLeft;
    mPaddingTop = paddingTop;
    mPaddingRight = paddingRight;
    mPaddingBottom = paddingBottom;
  }

  public float getPaddingLeft() {
    return mPaddingLeft;
  }

  public float getPaddingTop() {
    return mPaddingTop;
  }

  public float getPaddingRight() {
    return mPaddingRight;
  }

  public float getPaddingBottom() {
    return mPaddingBottom;
  }

  public abstract float getQuadrantStartX();

  public abstract float getQuadrantStartY();

  public abstract float getQuadrantWidth();

  public abstract float getQuadrantHeight();

  public float getQuadrantEndX() {
    return getQuadrantStartX() + getQuadrantWidth();
  }

  public float getQuadrantEndY() {
    return getQuadrantStartY() + getQuadrantHeight();
  }

  public float getQuadrantPaddingStartX() {
    return getQuadrantStartX() + mPaddingLeft;
  }

  public float getQuadrantPaddingEndX() {
    return getQuadrantEndX() - mPaddingRight;
  }

  public float getQuadrantPaddingStartY() {
    return getQuadrantStartY() + mPaddingTop;
  }

  public float getQuadrantPaddingEndY() {
    return getQuadrantEndY() - mPaddingBottom;
  }

  public float getQuadrantPaddingWidth() {
    return getQuadrantWidth() - mPaddingLeft - mPaddingRight;
  }

  public float getQuadrantPaddingHeight() {
    return getQuadrantHeight() - mPaddingTop - mPaddingBottom;
  }
}
