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
package net.j4c.centroid;

import java.util.Iterator;
import java.util.Set;

import net.j4c.IndexValue;

/**
 * Recomputes the centroid by adjusting to mean/average.
 * @author Jee Vang
 *
 */
public class MeanCentroidAnalyzer implements CentroidAnalyzer {

	@Override
	public void recompute(Centroid centroid) {
		Set<Integer> indexes = centroid.getIndexes();
		int numIndexes = indexes.size();
		double[] sums = new double[numIndexes];
		
		int i = 0;
		int numClusterItems = centroid.getNumOfClusterItems();
		for(Iterator<Integer> it = indexes.iterator(); it.hasNext(); i++) {
			Integer index = it.next();
			for(int j=0; j < numClusterItems; j++) {
				sums[i] += centroid.getClusterItem(j).getValue(index);
			}
		}
		
		i = 0;
		double[] means = new double[numIndexes];
		double n = centroid.getNumOfClusterItems();
		
		for(Iterator<Integer> it = indexes.iterator(); it.hasNext(); i++) {
			Integer index = it.next();
			
			if(n > 0) {
				means[i] = sums[i] / n;
			}
			
			IndexValue indexValue = new IndexValue(index.intValue(), means[i]);
			centroid.add(indexValue);
		}
	}

}
