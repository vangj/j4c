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
package net.j4c.distance;

import net.j4c.ClusterItem;

/**
 * Distance measure.
 * @author Jee Vang
 *
 */
public interface DistanceMeasure {

	/**
	 * Computes the distance between two cluster items.
	 * @param clusterItem1 {@link ClusterItem}.
	 * @param clusterItem2 {@link ClusterItem}.
	 * @return double.
	 */
	public double getDistance(ClusterItem clusterItem1, ClusterItem clusterItem2);
}
