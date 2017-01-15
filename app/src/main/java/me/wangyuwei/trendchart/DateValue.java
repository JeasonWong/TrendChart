package me.wangyuwei.trendchart;

/**
 * 巴掌
 * https://github.com/JeasonWong
 */

public class DateValue {

  private double mValue;
  private long mDate;

  public DateValue(double value, long date) {
    mValue = value;
    mDate = date;
  }

  public double getValue() {
    return mValue;
  }

  public long getDate() {
    return mDate;
  }
}
