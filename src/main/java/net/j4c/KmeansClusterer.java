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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.j4c.centroid.Centroid;
import net.j4c.centroid.CentroidAnalyzer;
import net.j4c.centroid.MeanCentroidAnalyzer;
import net.j4c.distance.CosineDistance;
import net.j4c.distance.DistanceMeasure;
import net.j4c.seeding.KmppSeedInitializer;
import net.j4c.seeding.SeedInitializer;

/**
 * K-means clustering.
 * @author Jee Vang
 *
 */
public class KmeansClusterer {

	private DistanceMeasure distanceMeasure;
	private SeedInitializer seedInitializer;
	private CentroidAnalyzer centroidAnalyzer;
	
	/**
	 * Gets a list of {@link Centroid}.
	 * @param maxIterations Maximum number of iterations.
	 * @param clusterItems {@link List} of {@link ClusterItem}.
	 * @return {@link List} of {@link Centroid}.
	 */
	public List<Centroid> getClusters(int maxIterations, List<ClusterItem> clusterItems) {
		int n = clusterItems.size();
		double d = n / 2.0d;
		d = Math.sqrt(d);
		int k = (int)Math.ceil(d);
		
		return getClusters(k, maxIterations, clusterItems);
	}
	
	/**
	 * Gets a list of {@link Centroid}.
	 * @param k Number of clusters.
	 * @param maxIterations Maximum number of iterations.
	 * @param clusterItems {@link List} of {@link ClusterItem}.
	 * @return {@link List} of {@link Centroid}.
	 */
	public List<Centroid> getClusters(int k, int maxIterations, List<ClusterItem> clusterItems) {
		int numClusterItems = clusterItems.size();
		//adjust k max, in case it is greater than 
		//the number of cluster items
		int kMax = (k < numClusterItems) ? k : numClusterItems;
		
		//get the initial seeds
		SeedInitializer seedInitializer = getSeedInitializer();
		List<Centroid> centroids = seedInitializer.getSeeds(kMax, clusterItems);
		int numCentroids = centroids.size(); //should be equal to kMax
		
		DistanceMeasure distanceMeasure = getDistanceMeasure();
		CentroidAnalyzer centroidAnalyzer = getCentroidAnalyzer();
		
		Map<ClusterItem, Integer> membership = new HashMap<ClusterItem, Integer>();
		
		int iteration = 0;
		boolean membershipChanged = false;
		
		//now iterate over assignment and recomputation of centroids
		while(iteration < maxIterations) {
			for(Centroid centroid : centroids)
				centroid.clear();
			
			//assignment
			for(ClusterItem clusterItem : clusterItems) {
				double min = 0.0d;
				int centroidIndex = 0;
				
				//find the closest centroid
				for(int index=0; index < numCentroids; index++) {
					Centroid centroid = centroids.get(index);
					double dist = distanceMeasure.getDistance(centroid, clusterItem);
					if(0 == index || dist < min) {
						min = dist;
						centroidIndex = index;
					}
				}
				
				Centroid centroid = centroids.get(centroidIndex);
				centroid.add(clusterItem);
				
				//check if membership has changed
				if(membership.containsKey(clusterItem)) {
					Integer indexOfCentroid = membership.get(clusterItem);
					if(indexOfCentroid.intValue() != centroidIndex) {
						membershipChanged = true;
						membership.put(clusterItem, new Integer(centroidIndex));
					}
				} else {
					membership.put(clusterItem, new Integer(centroidIndex));
					membershipChanged = true;
				}
			}
			
			//recompute centroid
			for(Centroid centroid : centroids) {
				centroidAnalyzer.recompute(centroid);
			}
			
			//increment iteration
			iteration++;
			
			//if no membership has changed, then convergence, STOP
			if(!membershipChanged) {
				break;
			} else {
				membershipChanged = false;
			}
		}
		
		return centroids;
	}

	/**
	 * Gets the {@link DistanceMeasure}.
	 * @return {@link DistanceMeasure}.
	 */
	public DistanceMeasure getDistanceMeasure() {
		if(null == distanceMeasure)
			distanceMeasure = new CosineDistance();
		return distanceMeasure;
	}

	public void setDistanceMeasure(DistanceMeasure distanceMeasure) {
		this.distanceMeasure = distanceMeasure;
	}

	/**
	 * Gets the {@link SeedInitializer}.
	 * @return {@link SeedInitializer}.
	 */
	public SeedInitializer getSeedInitializer() {
		if(null == seedInitializer)
			seedInitializer = new KmppSeedInitializer();
		return seedInitializer;
	}

	public void setSeedInitializer(SeedInitializer seedInitializer) {
		this.seedInitializer = seedInitializer;
	}

	/**
	 * Gets the {@link CentroidAnalyzer}.
	 * @return {@link CentroidAnalyzer}.
	 */
	public CentroidAnalyzer getCentroidAnalyzer() {
		if(null == centroidAnalyzer)
			centroidAnalyzer = new MeanCentroidAnalyzer();
		return centroidAnalyzer;
	}

	public void setCentroidAnalyzer(CentroidAnalyzer centroidAnalyzer) {
		this.centroidAnalyzer = centroidAnalyzer;
	}
}
