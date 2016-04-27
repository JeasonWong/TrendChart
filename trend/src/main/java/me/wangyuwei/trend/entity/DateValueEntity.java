package me.wangyuwei.trend.entity;

/**
 * 作者： 巴掌 on 16/4/25 23:26
 */
public class DateValueEntity {

    private long date;
    private float value;

    public DateValueEntity(float value, long date) {
        this.value = value;
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}
