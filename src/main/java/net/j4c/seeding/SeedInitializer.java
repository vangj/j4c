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

import java.util.List;

import net.j4c.ClusterItem;
import net.j4c.centroid.Centroid;

/**
 * Initializes centroid seeds.
 * @author Jee Vang
 *
 */
public interface SeedInitializer {

	/**
	 * Gets the initial seeds for k-means.
	 * @param k Number of seeds.
	 * @param clusterItems {@link List} of {@link ClusterItem}.
	 * @return {@link List} of {@link Centroid}.
	 */
	public List<Centroid> getSeeds(int k, List<ClusterItem> clusterItems);
}
