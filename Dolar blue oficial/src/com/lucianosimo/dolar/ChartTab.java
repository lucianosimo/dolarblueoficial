package com.lucianosimo.dolar;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ChartTab extends Fragment{
	
	private GraphicalView mChartView;

    public ChartTab() {

    }
    
    private TimeSeries serieOficial;
    private TimeSeries serieBlue;
    private XYSeriesRenderer rendererOficial;
    private XYSeriesRenderer rendererBlue;
    private XYMultipleSeriesDataset mSeriesDataset;
    private XYMultipleSeriesRenderer mSeriesRendered;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup conteiner, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chart_tab, conteiner, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        displayChart();
    }



    private void initializeChartValues() {
        int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        double[] oficialY = {5.3, 5.4, 5.6, 5.7, 6.0, 6.1, 6.5, 6.6, 6.7, 6.9, 7.1, 7.4};
        double[] blueY = {8.1, 8.3, 8.8, 8.6, 8.9, 9.3, 9.6, 9.4, 9.9, 10.5, 10.7, 11.2};

        serieOficial = new TimeSeries("Valor promedio oficial (ultimos 12 meses)");
        for (int i = 0; i < x.length; i++) {
        	serieOficial.add(x[i], oficialY[i]);
        }
        
        serieBlue = new TimeSeries("Valor promedio blue (ultimos 12 meses)");
        for (int i = 0; i < x.length; i++) {
        	serieBlue.add(x[i], blueY[i]);
        }
        
        rendererOficial = new XYSeriesRenderer();
        rendererOficial.setColor(getResources().getColor(R.color.chartGreen));
        rendererOficial.setPointStyle(PointStyle.CIRCLE);
        rendererOficial.setFillPoints(true);
        rendererOficial.setLineWidth(3);
        
        rendererBlue = new XYSeriesRenderer();
        rendererBlue.setColor(getResources().getColor(R.color.chartBlue));
        rendererBlue.setPointStyle(PointStyle.CIRCLE);
        rendererBlue.setFillPoints(true);
        rendererBlue.setLineWidth(3);

        mSeriesDataset = new XYMultipleSeriesDataset();
        mSeriesDataset.addSeries(serieOficial);
        mSeriesDataset.addSeries(serieBlue);
        
        mSeriesRendered = new XYMultipleSeriesRenderer();
        
        mSeriesRendered.setPanEnabled(false, false);
        
        mSeriesRendered.setApplyBackgroundColor(true);
        mSeriesRendered.setMarginsColor(Color.WHITE);
        mSeriesRendered.setBackgroundColor(Color.WHITE);
        mSeriesRendered.setAxesColor(Color.BLACK);
        
        mSeriesRendered.setXLabels(12);
        mSeriesRendered.setYLabels(12);
        mSeriesRendered.setXAxisMin(0.0);
        mSeriesRendered.setXAxisMax(13.0);
        mSeriesRendered.setYAxisMin(5.0);
        mSeriesRendered.setYAxisMax(12.0);
        mSeriesRendered.setXLabelsColor(Color.BLACK);
        mSeriesRendered.setYLabelsColor(0, Color.BLACK);
        mSeriesRendered.setYLabelsAlign(Align.RIGHT);
        mSeriesRendered.setYLabelsPadding(1);
        
        mSeriesRendered.addSeriesRenderer(rendererOficial);
        mSeriesRendered.addSeriesRenderer(rendererBlue);
    }



    private void displayChart() {
    	
        initializeChartValues();

        LinearLayout chartContainer;
        chartContainer = (LinearLayout) getActivity().findViewById(R.id.graphContainer);

        mChartView = ChartFactory.getLineChartView(getActivity(), mSeriesDataset, mSeriesRendered);
        chartContainer.addView(mChartView);

    }
}
