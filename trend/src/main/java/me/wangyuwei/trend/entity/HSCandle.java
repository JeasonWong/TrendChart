package me.wangyuwei.trend.entity;

/**
 * 作者： 巴掌 on 16/4/26 15:50
 */
public class HSCandle {

    private long min_time;
    private double open_px;
    private double high_px;
    private double low_px;
    private double close_px;
    private double business_amount;
    private double business_balance;
    private double avg_px;
    private double last_px;

    public long getMin_time() {
        return this.min_time;
    }

    public void setMin_time(long min_time) {
        this.min_time = min_time;
    }

    public double getOpen_px() {
        return this.open_px;
    }

    public void setOpen_px(double open_px) {
        this.open_px = open_px;
    }

    public double getHigh_px() {
        return this.high_px;
    }

    public void setHigh_px(double high_px) {
        this.high_px = high_px;
    }

    public double getLow_px() {
        return this.low_px;
    }

    public void setLow_px(double low_px) {
        this.low_px = low_px;
    }

    public double getClose_px() {
        return this.close_px;
    }

    public void setClose_px(double close_px) {
        this.close_px = close_px;
    }

    public double getBusiness_amount() {
        return this.business_amount;
    }

    public void setBusiness_amount(double business_amount) {
        this.business_amount = business_amount;
    }

    public double getBusiness_balance() {
        return this.business_balance;
    }

    public void setBusiness_balance(double business_balance) {
        this.business_balance = business_balance;
    }

    public double getAvg_px() {
        return this.avg_px;
    }

    public void setAvg_px(double avg_px) {
        this.avg_px = avg_px;
    }

    public double getLast_px() {
        return this.last_px;
    }

    public void setLast_px(double last_px) {
        this.last_px = last_px;
    }

}
