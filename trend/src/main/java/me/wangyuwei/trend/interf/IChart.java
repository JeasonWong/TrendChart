package me.wangyuwei.trend.interf;

import me.wangyuwei.trend.chart.Chart;

/**
 * 作者： 巴掌 on 16/4/26 09:02
 */
public interface IChart {

    /* 昨收价格 */
    Chart withPrevClosePrice(double prevClosePrice);
    Chart withMinDisplayNum(int minDisplayNum);
    Chart withMaxDisplayNum(int maxDisplayNum);
    Chart withDisplayFrom(int displayFrom);
    Chart withDisplayNumber(int displayNumber);

}
