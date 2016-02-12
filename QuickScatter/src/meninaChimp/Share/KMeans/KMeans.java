package meninaChimp.Share.KMeans;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class KMeans {
	private static int ClusterNum;
	private static ArrayList<ArrayList<double[]>> cluster;
	private static double[][] center = new double[ClusterNum][2];
	private static double[][] lastCenter = new double[ClusterNum][2];
	private ArrayList<double[]> dataSet = new ArrayList<double[]>();
	
	/*
	 * 构造函数
	 */
	public KMeans(int clusterNum)
	{
		ClusterNum = clusterNum;
	}
	
	/*
	 * 主执行方法
	 */
	public void ExecuteMethod() throws IOException
	{
		LoadDataSet();
		initCenters();
		do
		{
			initCluster();
			AllocateCluster();
			lastCenter = center;
			setNewCenter();
		}
		while(this.IsCenterChanged(center));
	}
	
	/*
	 * 获取簇
	 */
	public ArrayList<ArrayList<double[]>> getCluster()
	{
		return cluster;
	}
	
	/*
	 * 装载数据
	 */
	private void LoadDataSet() throws IOException
	{
		String fileName = "E:" + File.separator + "testSet.txt";
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		String line = bufferReader.readLine();
		while(!(line.isEmpty() || line == null))
		{
			double[] data  = new double[2];
			String[] input = line.split("\t");
			data[0] = Double.parseDouble(input[0]);
			data[1] = Double.parseDouble(input[1]);
			line = bufferReader.readLine();
			dataSet.add(data);
		}
		
		fileReader.close();
		bufferReader.close();
	}
	
	/*
	 * 判断簇中心点是否改变  作为算法结束条件
	 */
	private boolean IsCenterChanged(double[][] center)
	{
		for(int i = 0; i < center.length; i++)
		{
			if(center[i][0] != lastCenter[i][0] || center[i][1] != lastCenter[i][1])
			{
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * 初始化簇中心
	 */
	private void initCenters()
	{
		//// 中心点的设置会导致分类失败
		center = new double[][]{{-1,-2},{-3,2},{2,4}};
	}
	
	/*
	 * 初始化簇容器
	 */
	private void initCluster()
	{
		ArrayList<ArrayList<double[]>> initCluster = new ArrayList<ArrayList<double[]>>();
		for(int i = 0; i < ClusterNum; i++)
		{
			initCluster.add(new ArrayList<double[]>());
		}
		
		if(cluster != null)
		{
			cluster.clear();
		}
		
		cluster = initCluster;
	}
	
	/*
	 * 计算欧式距离  用以根据距离完成分类
	 */
	private double CalcDistance(double[] element, double[] center)
	{
		double x = element[0] - center[0];
		double y = element[1] - center[1];
		double z = x*x + y*y;
		return (double)Math.sqrt(z);
	}
	
	/*
	 * 获取这个节点属于哪个簇
	 */
	private int getClusterIndex(double[] distance)
	{
		double minDistance = distance[0];
		int clusterIndex = 0;
		for(int i = 0; i < distance.length; i++)
		{
			if(distance[i] < minDistance)
			{
				minDistance = distance[i];
				clusterIndex = i;
			}
		}
		
		return clusterIndex;
	}
	
	/*
	 * 分配簇
	 */
	private void AllocateCluster()
	{
		double[] distance = new double[ClusterNum];
		for(double[] data : dataSet)
		{
			for(int j = 0; j < ClusterNum; j++)
			{
				distance[j] = this.CalcDistance(data, center[j]);
			}
			
			int clusterIndex = this.getClusterIndex(distance);
			
			/*
			 * 如果用ArrayList<double[][]>来描述簇也是可行的 但是在这里会很不好处理   不可能为每一个簇都保存一个索引值
			 */
			cluster.get(clusterIndex).add(data);
		}
	}
	
	/*
	 * 设置新的簇中心
	 */
	private void setNewCenter()
	{
		center = new double[ClusterNum][2];
		for(int i = 0; i < center.length; i++)
		{
			if(cluster.get(i).size() != 0)
			{
				double[] newCenter = new double[2];
				for(int j = 0; j < cluster.get(i).size(); j++)
				{
					newCenter[0] += cluster.get(i).get(j)[0];
					newCenter[1] += cluster.get(i).get(j)[1];
				}
				
				center[i][0] = newCenter[0]/cluster.get(i).size();
				center[i][1] = newCenter[1]/cluster.get(i).size();
			}
		}
	}
}
