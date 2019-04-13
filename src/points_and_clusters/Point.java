package points_and_clusters;

import java.util.Arrays;
import java.util.HashMap;

public class Point {

	public Point(double[] cooridnates, Cluster cluster) {
		this.cooridnates = cooridnates;
		this.cluster = cluster;
	}
	public Point(double[] cooridnates, String name, Cluster cluster) {
		this.cooridnates = cooridnates;
		this.name = name;
		this.cluster = cluster;
	}
	
	public double[] cooridnates;
	public String name;
	public Cluster cluster;
	public  HashMap<Point, Double> map = new HashMap<>();
	@Override
	public String toString() {
		return "Point [cooridnates=" + Arrays.toString(cooridnates) + ", name=" + name + ", cluster=" + cluster + "]" + "\n";
	}
}
