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
 * Cosine distance. This uses the cosine-similarity
 * measure, which produces a value in the range [0, 1],
 * where a value closer to 1 means more similarity. Whatever
 * the value is, we return (1 - cosine_similarity) as
 * a distance measure/value.
 * @author Jee Vang
 *
 */
public class CosineDistance implements DistanceMeasure {

	@Override
	public double getDistance(ClusterItem clusterItem1, ClusterItem clusterItem2) {
		double ab = getDotProduct(clusterItem1, clusterItem2);
		double aa = getDotProduct(clusterItem1, clusterItem1);
		double bb = getDotProduct(clusterItem2, clusterItem2);
		
		aa = Math.sqrt(aa);
		bb = Math.sqrt(bb);
		
		double denom = aa * bb;
		double similarity = (0.0d == denom) ? 0.0d : (ab / denom);
		double dist = 1.0d - similarity;
		return dist;
	}
	
	/**
	 * Computes the dot-product between two {@link ClusterItem}.
	 * @param clusterItem1 {@link ClusterItem}.
	 * @param clusterItem2 {@link ClusterItem}.
	 * @return Dot-product.
	 */
	public double getDotProduct(ClusterItem clusterItem1, ClusterItem clusterItem2) {
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
			double d = d1 * d2;
			sum += d;
		}
		
		return sum;
	}

}
