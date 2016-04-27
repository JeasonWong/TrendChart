package me.wangyuwei.trendchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import me.wangyuwei.trend.Util;
import me.wangyuwei.trend.chart.TrendChart;
import me.wangyuwei.trend.entity.DateValueEntity;
import me.wangyuwei.trend.entity.LineEntity;

/**
 * 作者： 巴掌 on 16/4/25 23:22
 */
public class MainActivity extends AppCompatActivity {

    private TrendChart trend_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trend_chart = (TrendChart) findViewById(R.id.trend_chart);

        List<LineEntity<DateValueEntity>> lines = new ArrayList<>();
        lines.add(getLineEntity(Util.getTrend(), "TREND", Color.parseColor("#6cafff")));
        lines.add(getLineEntity(Util.getTrendAvg(), "TREND_AVG", Color.parseColor("#ffd124")));
        trend_chart.setLinesData(lines);
        trend_chart.withDisplayFrom(0)
                .withDisplayNumber(241)
                .withMaxDisplayNum(241)
                .withMinDisplayNum(241)
                .withPrevClosePrice(40.2);

    }

    public LineEntity<DateValueEntity> getLineEntity(List<DateValueEntity> entities, String title, int lineColor) {
        LineEntity<DateValueEntity> lineEntity = new LineEntity<>();
        lineEntity.setTitle(title);
        lineEntity.setLineColor(lineColor);
        lineEntity.setLineData(entities);
        return lineEntity;
    }

}
