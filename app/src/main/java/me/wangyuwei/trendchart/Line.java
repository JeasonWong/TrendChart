package me.wangyuwei.trendchart;

import java.util.List;

/**
 * 巴掌
 * https://github.com/JeasonWong
 */

public class Line {

  //线条数据源集合
  private List<DateValue> mLineData;
  //线条颜色
  private int mLineColor = 0xFF688FDB;
  //线条宽度
  private float mLineWidth = 3f;

  public Line(List<DateValue> lineData) {
    mLineData = lineData;
  }

  public List<DateValue> getLineData() {
    return mLineData;
  }

  public int getLineColor() {
    return mLineColor;
  }

  public float getLineWidth() {
    return mLineWidth;
  }
}
