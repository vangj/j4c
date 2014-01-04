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
package net.j4c.seeding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.j4c.ClusterItem;
import net.j4c.centroid.Centroid;
import net.j4c.distance.CosineDistance;
import net.j4c.distance.DistanceMeasure;

/**
 * K-means++ seed initializer.
 * @author Jee Vang
 *
 */
public class KmppSeedInitializer implements SeedInitializer {

	/**
	 * Probability class.
	 * @author Jee Vang
	 *
	 */
	public class Probability {
		public double prob;
		public int index;
		
		/**
		 * Constructor.
		 * @param prob Probability.
		 * @param index Index.
		 */
		public Probability(double prob, int index) {
			this.prob = prob;
			this.index = index;
		}
		
		public String toString() {
			return (new StringBuffer())
				.append("[")
				.append(index)
				.append(",")
				.append(prob)
				.append("]")
				.toString();
		}
	}
	
	private Random random;
	private DistanceMeasure distanceMeasure;
	
	@Override
	public List<Centroid> getSeeds(int k, List<ClusterItem> clusterItems) {
		List<Centroid> seeds = new ArrayList<Centroid>();
		
		int total = clusterItems.size();
		int kMax = (k <= total) ? k : total;
		Random random = getRandom();
		
		while(seeds.size() < kMax) {
			if(0 == seeds.size()) {
				//for the first seed, pick it randomly
				int index = random.nextInt(total);
				ClusterItem clusterItem = clusterItems.get(index);
				Centroid centroid = new Centroid(""+seeds.size(), clusterItem);
				seeds.add(centroid);
			} else {
				DistanceMeasure distanceMeasure = getDistanceMeasure();
				List<Number> distances = new ArrayList<Number>();
				double sumOfDistances = 0.0d;
				int numSeeds = seeds.size();
				
				//for each cluster item, compute the distance
				//from it to the nearest centroid
				for(int clusterItemIndex=0; clusterItemIndex < total; clusterItemIndex++) {
					ClusterItem clusterItem = clusterItems.get(clusterItemIndex);
					double min = 0.0d;
					
					for(int index=0; index < numSeeds; index++) {
						ClusterItem seed = seeds.get(index);
						double dist = distanceMeasure.getDistance(clusterItem, seed);
						if(0 == index || dist < min)
							min = dist;
					}
					
					distances.add(new Double(min));
					sumOfDistances += min;
				}
				
				//convert the distance of each cluster item to a probability
				List<Probability> probs =  new ArrayList<Probability>();
				double cummulative = 0.0d;
				
				for(int index=0; index < total; index++) {
					Number distance = distances.get(index);
					double prob = (distance.doubleValue()) / sumOfDistances;
					cummulative += prob;
					
					Probability probability = new Probability(cummulative, index);
					probs.add(probability);
				}
				
				//now, pick randomly from the probability distribution
				double p = random.nextDouble();
				boolean addedSeed = false;
				for(Probability prob : probs) {
					if(prob.prob >= p) {
						ClusterItem clusterItem = clusterItems.get(prob.index);
						Centroid seed = new Centroid(""+seeds.size(), clusterItem);
						seeds.add(seed);
						addedSeed = true;
						
						break;
					}
				}
				
				//if we still haven't added a seed, just pick
				//one randomly
				if(!addedSeed) {
					int index = random.nextInt(total);
					ClusterItem clusterItem = clusterItems.get(index);
					Centroid seed = new Centroid(""+seeds.size(), clusterItem);
					seeds.add(seed);
				}
			}
		}
		
		return seeds;
	}

	/**
	 * Gets random number generator.
	 * @return {@link Random}.
	 */
	public Random getRandom() {
		if(null == random)
			random = new Random(37L);
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	/**
	 * Gets distance measure.
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

}
