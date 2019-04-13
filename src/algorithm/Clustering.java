package algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import points_and_clusters.Cluster;
import points_and_clusters.Point;
import uitls.Utilities;

public class Clustering {

	public List<Cluster> listOfClusters;
	public List<Point> listOfPoints = new ArrayList<>();
	int k;
	List<Cluster> listOfClustersToCompare;

	public Clustering(String filePath) throws IOException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter k-value ");
		k = scan.nextInt();
		scan.close();

		listOfClusters = Utilities.generateClusters(k);

		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line;
		while ((line = reader.readLine()) != null) {
			listOfPoints.add(Utilities.generatePoint(line, listOfClusters));
		}
		reader.close();

		// for (Point point : listOfPoints) {
		// System.out.println(point);
		// }
	}

	public void cluster() {
	
		do {
			listOfClustersToCompare = listOfPoints.stream().map(e -> e.cluster).collect(Collectors.toList());
			List<Point> centroids = new ArrayList<>();
			for (Cluster cluster : listOfClusters) {
				centroids.add(Utilities.calculateCentroid(cluster, listOfPoints));

			}
			System.out.println(centroids);
			System.out.println("<--------------------------------------------->");
			System.err.println("before Reassigning Clusters");
			 System.out.println(listOfPoints);
			System.out.println("<--------------------------------------------->");
			for (Point point : listOfPoints) {
				Utilities.calculateSquareDistances(point, centroids);
				// System.out.println(point.map);
				// System.out.println("<next POINT>");
			}
			reassignValues();
			System.err.println("after Reassigning Clusters");
			 System.out.println(listOfPoints);
			// System.out.println(listOfPoints.stream().map(e ->
			// e.cluster).collect(Collectors.toList())
			// .equals(listOfClustersToCompare));
			// System.out.println(centroids.toString());
			sumOfDistances(centroids);
			
			System.out.println(totalSumOfDistances(centroids));
			purity();
			
			
		} while (!listOfPoints.stream().map(e -> e.cluster).collect(Collectors.toList())
				.equals(listOfClustersToCompare));

	}

	public void reassignValues() {
		for (Point point : listOfPoints) {
			double minDistance = point.map.values().stream().min(Comparator.naturalOrder()).get();
			point.cluster = point.map.keySet().stream().filter(e -> point.map.get(e) == minDistance).map(e -> e.cluster)
					.findFirst().get();
		}
	}

	private void sumOfDistances(List<Point> centroids) {
		for (Point point : centroids) {
			System.out.println(listOfPoints.stream().filter(e -> e.cluster.equals(point.cluster))
					.mapToDouble(e -> e.map.get(point)).sum() + " " + point);
		}
	}

	private double totalSumOfDistances(List<Point> centroids) {
		double sum = 0;
		for (Point point : centroids) {
			sum += listOfPoints.stream().filter(e -> e.cluster.equals(point.cluster)).mapToDouble(e -> e.map.get(point))
					.sum();
		}
		return sum;
	}

	private void purity() {
		List<String> labels = listOfPoints.stream().map(e -> e.name).distinct().collect(Collectors.toList());
		System.out.println(labels.size());
		for (Cluster cluster : listOfClusters) {
			long totalCount = listOfPoints.stream().filter(e -> e.cluster.equals(cluster)).count();
			for (String name : labels) {
				long labelCount = listOfPoints.stream().filter(e -> e.cluster.equals(cluster))
						.filter(e -> e.name.equals(name)).count();
				System.out.println(
						"In " + cluster + " there are " + labelCount + " of " + name + " out of " + totalCount);
			}

		}
	}

}
