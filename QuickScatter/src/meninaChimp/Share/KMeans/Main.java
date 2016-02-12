package meninaChimp.Share.KMeans;
import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
		KMeans kmeans = (KMeans) context.getBean("KMeans");
		JfreeChartScatter scatter = (JfreeChartScatter)context.getBean("Scatter");
		kmeans.ExecuteMethod();
		scatter.Scatter(kmeans.getCluster());
	}
}
