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
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.j4c.ClusterItem;
import net.j4c.centroid.Centroid;

/**
 * {@link SeedInitializer} that is based on random
 * selection.
 * @author Jee Vang
 *
 */
public class RandomSeedInitializer implements SeedInitializer {
	
	private Random random = new Random(37L);

	@Override
	public List<Centroid> getSeeds(int k, List<ClusterItem> clusterItems) {
		List<Centroid> seeds = new ArrayList<Centroid>();
		
		int total = clusterItems.size();
		int kMax = (k <= total) ? k : total;
		
		Random random = getRandom();
		Set<Integer> indexCache = new HashSet<Integer>();
		
		int id = 0;
		while(indexCache.size() < kMax) {
			Integer index = new Integer(random.nextInt(total));
			if(!indexCache.contains(index)) {
				indexCache.add(index);
				
				ClusterItem clusterItem = clusterItems.get(index.intValue());
				Centroid centroid = new Centroid(""+id, clusterItem);
				seeds.add(centroid);
				
				id++;
			}
		}
		
		return seeds;
	}

	/**
	 * Gets the random number generator.
	 * @return {@link Random}.
	 */
	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

}
