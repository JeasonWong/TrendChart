package me.wangyuwei.trendchart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  private TrendView mTrendView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mTrendView = (TrendView) findViewById(R.id.trend_view);
    mTrendView
        .withLine(new Line(Util.getTrend()))
        .withPrevClose(40.2)
        .withDisplayFrom(0)
        .withDisplayNumber(Util.getTrend().size())
        .show();
  }
}
