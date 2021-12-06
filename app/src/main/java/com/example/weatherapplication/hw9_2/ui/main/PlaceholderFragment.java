package com.example.weatherapplication.hw9_2.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatherapplication.R;
import com.example.weatherapplication.hw9_2.Statics;
import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.common.HIGradient;
import com.highsoft.highcharts.common.HIStop;
import com.highsoft.highcharts.common.hichartsclasses.HIArearange;
import com.highsoft.highcharts.common.hichartsclasses.HIBackground;
import com.highsoft.highcharts.common.hichartsclasses.HICSSObject;
import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HIData;
import com.highsoft.highcharts.common.hichartsclasses.HIEvents;
import com.highsoft.highcharts.common.hichartsclasses.HILegend;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPane;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HISolidgauge;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;
import com.highsoft.highcharts.core.HIFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    private String renderIconsString = "function renderIcons() {" +
            "                            if(!this.series[0].icon) {" +
            "                               this.series[0].icon = this.renderer.path(['M', -8, 0, 'L', 8, 0, 'M', 0, -8, 'L', 8, 0, 0, 8]).attr({'stroke': '#303030','stroke-linecap': 'round','stroke-linejoin': 'round','stroke-width': 2,'zIndex': 10}).add(this.series[2].group);}this.series[0].icon.translate(this.chartWidth / 2 - 10,this.plotHeight / 2 - this.series[0].points[0].shapeArgs.innerR -(this.series[0].points[0].shapeArgs.r - this.series[0].points[0].shapeArgs.innerR) / 2); if(!this.series[1].icon) {this.series[1].icon = this.renderer.path(['M', -8, 0, 'L', 8, 0, 'M', 0, -8, 'L', 8, 0, 0, 8,'M', 8, -8, 'L', 16, 0, 8, 8]).attr({'stroke': '#ffffff','stroke-linecap': 'round','stroke-linejoin': 'round','stroke-width': 2,'zIndex': 10}).add(this.series[2].group);}this.series[1].icon.translate(this.chartWidth / 2 - 10,this.plotHeight / 2 - this.series[1].points[0].shapeArgs.innerR -(this.series[1].points[0].shapeArgs.r - this.series[1].points[0].shapeArgs.innerR) / 2); if(!this.series[2].icon) {this.series[2].icon = this.renderer.path(['M', 0, 8, 'L', 0, -8, 'M', -8, 0, 'L', 0, -8, 8, 0]).attr({'stroke': '#303030','stroke-linecap': 'round','stroke-linejoin': 'round','stroke-width': 2,'zIndex': 10}).add(this.series[2].group);}this.series[2].icon.translate(this.chartWidth / 2 - 10,this.plotHeight / 2 - this.series[2].points[0].shapeArgs.innerR -(this.series[2].points[0].shapeArgs.r - this.series[2].points[0].shapeArgs.innerR) / 2);}";


    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        if(pageViewModel.getIndex() == 1) {
            View root = inflater.inflate(R.layout.details_fragment_1, container, false);
            GridView gridView = root.findViewById(R.id.gv_test);
            gridView.setAdapter(new GridListAdapter());
            return root;
        } else if(pageViewModel.getIndex() == 2) {

            View root = inflater.inflate(R.layout.details_fragment_2, container, false);
            HIChartView chartView = root.findViewById(R.id.highchart);

            HIOptions options = new HIOptions();

            HIChart chart = new HIChart();
            chart.setType("arearange");
            chart.setZoomType("x");
            options.setChart(chart);

            HITitle title = new HITitle();
            title.setText("Temperature variation by day");
            options.setTitle(title);

            HIXAxis xaxis = new HIXAxis();
            xaxis.setType("datetime");
            options.setXAxis(new ArrayList<HIXAxis>(){{add(xaxis);}});

            HIYAxis yaxis = new HIYAxis();
            yaxis.setTitle(new HITitle());
            options.setYAxis(new ArrayList<HIYAxis>(){{add(yaxis);}});

            HITooltip tooltip = new HITooltip();
            tooltip.setShadow(true);
            tooltip.setValueSuffix("°C");
            options.setTooltip(tooltip);

            HILegend legend = new HILegend();
            legend.setEnabled(false);
            options.setLegend(legend);

            HIArearange series = new HIArearange();
            series.setName("Temperatures");
            HIGradient gradient = new HIGradient(0, 0, 0, 1);
            LinkedList<HIStop> stops = new LinkedList<>();
            stops.add(new HIStop(0, HIColor.initWithName("Orange")));
            stops.add(new HIStop(1, HIColor.initWithName("SkyBlue")));
            series.setFillColor(HIColor.initWithLinearGradient(gradient, stops));

            Object[][] seriesData = new Object[16][3];

            try {
                JSONArray jsonArray = new JSONArray(Statics.weatherDetails);
                for(int i=0; i<jsonArray.length(); i++) {
                    JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());

                    String pk = jsonObject.getString("date");
                    Date date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(pk.replaceAll("Z$", "+0000"));

                    seriesData[i][0] = date.getTime();
                    seriesData[i][1] = Double.parseDouble(jsonObject.getString("tempmin"));
                    seriesData[i][2] = Double.parseDouble(jsonObject.getString("tempmax"));
                }
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }


            series.setData(new ArrayList<>(Arrays.asList(seriesData)));
            options.setSeries(new ArrayList<>(Arrays.asList(series)));

            chartView.setOptions(options);
            return root;

        } else {
            View root = inflater.inflate(R.layout.details_fragment_3, container, false);
            HIChartView chartView = root.findViewById(R.id.highchart);

            int a = 0, b = 0, c = 0;

            try {
                JSONArray jsonArray = new JSONArray(Statics.weatherDetails);
                JSONObject jsonObject = new JSONObject(jsonArray.get(0).toString());

                a = Integer.parseInt(jsonObject.getString("cloud"));
                b = Integer.parseInt(jsonObject.getString("precipitation"));
                c = Integer.parseInt(jsonObject.getString("humidity"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            HIOptions options = new HIOptions();

            HIChart chart = new HIChart();
            chart.setType("solidgauge");
            chart.setEvents(new HIEvents());
            chart.getEvents().setRender(new HIFunction(renderIconsString));
            options.setChart(chart);

            HITitle title = new HITitle();
            title.setText("Activity");
            title.setStyle(new HICSSObject());
            title.getStyle().setFontSize("24px");
            options.setTitle(title);

            HITooltip tooltip = new HITooltip();
            tooltip.setBorderWidth(0);
            tooltip.setBackgroundColor(HIColor.initWithName("none"));
            tooltip.setShadow(false);
            tooltip.setStyle(new HICSSObject());
            tooltip.getStyle().setFontSize("16px");
            tooltip.setPointFormat("{series.name}<br><span style=\"font-size:2em; color: {point.color}; font-weight: bold\">{point.y}%</span>");
            tooltip.setPositioner(
                    new HIFunction(
                            "function (labelWidth) {" +
                                    "   return {" +
                                    "       x: (this.chart.chartWidth - labelWidth) /2," +
                                    "       y: (this.chart.plotHeight / 2) + 15" +
                                    "   };" +
                                    "}"
                    ));
            options.setTooltip(tooltip);

            HIPane pane = new HIPane();
            pane.setStartAngle(0);
            pane.setEndAngle(360);

            HIBackground paneBackground1 = new HIBackground();
            paneBackground1.setOuterRadius("112%");
            paneBackground1.setInnerRadius("88%");
            paneBackground1.setBackgroundColor(HIColor.initWithRGBA(102,204,0, 0.35));
            paneBackground1.setBorderWidth(0);

            HIBackground paneBackground2 = new HIBackground();
            paneBackground2.setOuterRadius("87%");
            paneBackground2.setInnerRadius("63%");
            paneBackground2.setBackgroundColor(HIColor.initWithRGBA(102,178,255, 0.35));
            paneBackground2.setBorderWidth(0);

            HIBackground paneBackground3 = new HIBackground();
            paneBackground3.setOuterRadius("62%");
            paneBackground3.setInnerRadius("38%");
            paneBackground3.setBackgroundColor(HIColor.initWithRGBA(255,102,102, 0.35));
            paneBackground3.setBorderWidth(0);

            pane.setBackground(new ArrayList<>(Arrays.asList(paneBackground1, paneBackground2, paneBackground3)));
            options.setPane(pane);

            HIYAxis yaxis = new HIYAxis();
            yaxis.setMin(0);
            yaxis.setMax(100);
            yaxis.setLineWidth(0);
            yaxis.setTickPositions(new ArrayList<>()); // to remove ticks from the chart
            options.setYAxis(new ArrayList<>(Collections.singletonList(yaxis)));

            HIPlotOptions plotOptions = new HIPlotOptions();
            plotOptions.setSolidgauge(new HISolidgauge());
            plotOptions.getSolidgauge().setLinecap("round");
            plotOptions.getSolidgauge().setStickyTracking(false);
            plotOptions.getSolidgauge().setRounded(true);
            options.setPlotOptions(plotOptions);

            HISolidgauge solidgauge1 = new HISolidgauge();
            solidgauge1.setName("Cloud Cover");
            HIData data1 = new HIData();
            data1.setColor(HIColor.initWithRGB(102,204,0));
            data1.setRadius("112%");
            data1.setInnerRadius("88%");
            data1.setY(a);
            solidgauge1.setData(new ArrayList<>(Collections.singletonList(data1)));

            HISolidgauge solidgauge2 = new HISolidgauge();
            solidgauge2.setName("Precipitation");
            HIData data2 = new HIData();
            data2.setColor(HIColor.initWithRGB(102,178,255));
            data2.setRadius("87%");
            data2.setInnerRadius("63%");
            data2.setY(b);
            solidgauge2.setData(new ArrayList<>(Collections.singletonList(data2)));

            HISolidgauge solidgauge3 = new HISolidgauge();
            solidgauge3.setName("Humidity");
            HIData data3 = new HIData();
            data3.setColor(HIColor.initWithRGB(255,102,102));
            data3.setRadius("62%");
            data3.setInnerRadius("38%");
            data3.setY(c);
            solidgauge3.setData(new ArrayList<>(Collections.singletonList(data3)));

            options.setSeries(new ArrayList<>(Arrays.asList(solidgauge1, solidgauge2, solidgauge3)));

            chartView.setOptions(options);

            return root;
        }

    }
}