/**
 * Copyright 2014 Jee Vang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package net.j4c;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.j4c.centroid.Centroid;
import net.j4c.distance.DistanceMeasure;

/**
 * Silhouette k-means clustering.
 * @author Jee Vang
 *
 */
public class SilhouetteClusterer extends KmeansClusterer {

	@Override
	public List<Centroid> getClusters(int maxIterations, List<ClusterItem> clusterItems) {
		List<Centroid> results = null;
		int iteration = 0;
		int k = 2;
		int numItems = clusterItems.size();
		
		DistanceMeasure distanceMeasure = getDistanceMeasure();
		double silhoutte = 0.0d;
		
		while(iteration < maxIterations && k < numItems) {
			List<Centroid> centroids = getClusters(k, maxIterations, clusterItems);
			int numClusters = centroids.size();
			double sumOfS = 0.0d;
			double n = 0.0d;
			
			for(int i=0; i < numClusters; i++) {
				Centroid centroid = centroids.get(i);				
				int numClusterItems = centroid.getNumOfClusterItems();
				
				for(int itemIndex=0; itemIndex < numClusterItems; itemIndex++) {
					ClusterItem clusterItem = centroid.getClusterItem(itemIndex);
					double a = distanceMeasure.getDistance(clusterItem, centroid);
					double b = 0.0d;
					boolean first = true;
					
					for(int j=0; j < numClusters; j++) {
						if(j == i)
							continue;
						Centroid cent = centroids.get(j);
						double c = distanceMeasure.getDistance(clusterItem, cent);
						if(first || c < b) {
							first = false;
							b = c;
						}
					}
					
					double s = (b - a) / Math.max(a, b);
					sumOfS += s;
					n += 1.0d;
				}
			}
			
			double avgOfS = sumOfS / n;
			
			if(0 == iteration || avgOfS < silhoutte) {
				silhoutte = avgOfS;
				results = centroids;
			} else {
				break;
			}
			
			iteration++;
			k++;
		}
		
		List<Centroid> finalResults = new ArrayList<Centroid>();
		for(Centroid centroid : results) {
			if(centroid.getNumOfClusterItems() > 0) {
				finalResults.add(centroid);
			}
		}
		
		return finalResults;
	}
	
	public static void main(String[] args) {
		List<ClusterItem> clusterItems = new ArrayList<ClusterItem>();
		int rows = 100;
		int cols = 5;
		
		Random random = new Random(37L);
		
		for(int i=0; i < rows; i++) {
			ClusterItem clusterItem = new ClusterItem(""+i);
			
			for(int j=0; j < cols; j++) {
				IndexValue indexValue = new IndexValue(j, random.nextDouble());
				clusterItem.add(indexValue);
			}
			
			clusterItems.add(clusterItem);
		}
		
		SilhouetteClusterer clusterer = new SilhouetteClusterer();
		List<Centroid> centroids = clusterer.getClusters(100, clusterItems);
		
		for(Centroid centroid : centroids) {
			System.out.println(centroid.toString());
		}
	}
	
	static void demo1() {
		List<ClusterItem> clusterItems = new ArrayList<ClusterItem>();
		int total = 1000;
		
		for(int i=0; i < total; i++) {
			ClusterItem clusterItem = new ClusterItem(""+i);
			
			if(i < total/2) {
				clusterItem.add(new IndexValue(0, 1.0d));
				clusterItem.add(new IndexValue(1, 1.0d));
			} else {
				clusterItem.add(new IndexValue(0, 40.0d));
				clusterItem.add(new IndexValue(1, 20.0d));
			}
			
			clusterItems.add(clusterItem);
		}
		
		SilhouetteClusterer clusterer = new SilhouetteClusterer();
		List<Centroid> centroids = clusterer.getClusters(100, clusterItems);
		
		for(Centroid centroid : centroids) {
			System.out.println(centroid.toString());
		}
	}
}
