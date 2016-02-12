package meninaChimp.Share.KMeans;
import java.util.ArrayList;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.ChartFactory;  
import org.jfree.data.xy.DefaultXYDataset;
import java.awt.*;
import org.jfree.chart.renderer.xy.XYItemRenderer;

public class JfreeChartScatter {
	 
	/** The data. */
    private double[][] data = new double[2][100];
    
    /*
     * Ĭ�Ϲ��캯��
     */
    public JfreeChartScatter()
    {
    	
    }
    
    /*
     * ����JfreeChart����ɢ��ͼ
     */
	public void Scatter(ArrayList<ArrayList<double[]>> cluster)
	{
		DefaultXYDataset xyDataset = new DefaultXYDataset();
		for(int i = 0; i < cluster.size(); i++)
		{
			for(int j = 0; j < cluster.get(i).size(); j++)
			{
				this.data[0][j] = cluster.get(i).get(j)[0];
				this.data[1][j] = cluster.get(i).get(j)[1];
			}
			
			/** ���� ��ӵ�����򼯺�  ��������  ���Լ�����3�����鶼�����һ�������ֵ  ��������**/
			xyDataset.addSeries("Cluster" + (i + 1), this.data);
			
			///// ��ָ��ָ����һ���ڴ�����   ���� ��ӵ�����򼯺�  ��������  ���Լ�����3�����鶼�����һ�������ֵ
			this.data = new double[2][100];;
		}
		
		JFreeChart jfree = ChartFactory.createScatterPlot("KMeans", "X", "Y", xyDataset);
		XYPlot xyPlot = (XYPlot)jfree.getPlot();
		XYItemRenderer renderer = xyPlot.getRenderer();
		renderer.setSeriesOutlineStroke(0, new BasicStroke(0.05f));
		renderer.setSeriesPaint(0, Color.BLUE);
		renderer.setSeriesOutlineStroke(1, new BasicStroke(0.05f));
		renderer.setSeriesPaint(1, Color.RED);
		renderer.setSeriesOutlineStroke(2, new BasicStroke(0.05f));
		renderer.setSeriesPaint(2, Color.ORANGE);
		renderer.setSeriesVisible(0, true);
		renderer.setSeriesVisible(1, true);
		renderer.setSeriesVisible(2, true);
		jfree.setBackgroundPaint(Color.WHITE);
        ChartFrame frame = new ChartFrame("Kmeans",jfree);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
	}
}
