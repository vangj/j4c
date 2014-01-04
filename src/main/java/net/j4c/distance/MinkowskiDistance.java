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

import java.util.Iterator;
import java.util.Set;

import net.j4c.ClusterItem;

/**
 * Minkowski distance.
 * @author Jee Vang
 *
 */
public class MinkowskiDistance implements DistanceMeasure {

	private double p = 2.0d;
	
	/**
	 * Constructor.
	 */
	public MinkowskiDistance() { }
	
	/**
	 * Constructor.
	 * @param p Order (e.g. 1 = Manhattan, 2 = Euclidean)
	 */
	public MinkowskiDistance(double p) {
		if(p > 0)
			this.p = p;
	}
	
	@Override
	public double getDistance(ClusterItem clusterItem1, ClusterItem clusterItem2) {
		Set<Integer> indexes1 = clusterItem1.getIndexes();
		Set<Integer> indexes2 = clusterItem2.getIndexes();
		
		int size1 = indexes1.size();
		int size2 = indexes2.size();
		
		Set<Integer> indexes = (size1 < size2) ? indexes1 : indexes2;
		double sum = 0.0d;
		for(Iterator<Integer> it = indexes.iterator(); it.hasNext(); ) {
			Integer index = it.next();
			double d1 = clusterItem1.getValue(index);
			double d2 = clusterItem2.getValue(index);
			double d = Math.pow(Math.abs(d1 - d2), p);
			sum += d;
		}
		
		double dist = Math.pow(sum, 1.0d / p);
		return dist;
	}

}
