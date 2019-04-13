package uitls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import points_and_clusters.Cluster;
import points_and_clusters.Point;

public class Utilities {
	static List<Integer> randomList = new ArrayList<>();
	private static final Random RANDOM = new Random();

	public static Point generatePoint(String line, List<Cluster> listofClusters) {
		String[] values = line.split(",");

		Predicate<String> pred = e -> e.matches("^[0-9]+\\.[0-9]+");

		double[] val = Arrays.stream(values).filter(pred).mapToDouble(Double::parseDouble).toArray();
		String name = Arrays.stream(values).filter(pred.negate()).findFirst().get();
		if (randomList.isEmpty()) {
			IntStream.range(0, listofClusters.size()).forEach(e -> randomList.add(e));
			// System.out.println(randomList);
		}

		// int cluster = randomList.get(RANDOM.nextInt(listofClusters.size()));
		// System.out.println(cluster);
		Point point = new Point(val, name, listofClusters.get(RANDOM.nextInt(listofClusters.size())));
		// randomList.remove((Object)cluster);

		return point;
	}

	public static List<Cluster> generateClusters(int k) {
		List<Cluster> list = new ArrayList<>();
		for (int i = 1; i <= k; i++) {
			list.add(new Cluster("" + i));
		}
		return list;
	}

	public static Point calculateCentroid(Cluster cluster, List<Point> listOfPoints) {
		List<Point> belongingToCluster = listOfPoints.stream().filter(e -> e.cluster.equals(cluster))
				.collect(Collectors.toList());
		double[] centroidValues = new double[listOfPoints.get(0).cooridnates.length];
		// double[] centroidValues = new double[4];
		
			for (int i = 0; i < centroidValues.length; i++) {
				centroidValues[i] = calculateSum(i, belongingToCluster);
			}
			System.out.println(belongingToCluster.size());
			for (int i = 0; i < centroidValues.length; i++) {

				centroidValues[i] = centroidValues[i] / belongingToCluster.size();
			}
		
		// centroidValues = Arrays.stream(centroidValues).map(e -> e /
		// belongingToCluster.size()).toArray();
		return new Point(centroidValues, "Centroid for cluster " + cluster, cluster);
	}

	private static double calculateSum(int position, List<Point> list) {
		return list.stream().mapToDouble(e -> e.cooridnates[position]).sum();
	}

	public static void calculateSquareDistances(Point from, List<Point> centroid) {
		from.map=new HashMap<>();
		for (Point point : centroid) {

			double sum = 0;
			for (int i = 0; i < from.cooridnates.length; i++) {
				sum += Math.pow((from.cooridnates[i] - point.cooridnates[i]), 2);
			}
			// from.map.replace(point, sum);

			from.map.put(point, sum);
		}

	}
}
