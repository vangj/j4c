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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.j4c.ClusterItem;
import net.j4c.IndexValue;

/**
 * A centroid.
 * @author Jee Vang
 *
 */
public class Centroid extends ClusterItem {

	private List<ClusterItem> clusterItems;
	private Set<Integer> indices;
		
	/**
	 * Constructor.
	 * @param id Id.
	 */
	public Centroid(String id) {
		super(id);
		
		clusterItems = new ArrayList<ClusterItem>();
		indices = new HashSet<Integer>();
	}
	
	public Centroid(String id, ClusterItem clusterItem) {
		super(id);
		
		clusterItems = new ArrayList<ClusterItem>();
		indices = new HashSet<Integer>();
		
		Set<Integer> indexes = clusterItem.getIndexes();
		for(Iterator<Integer> it = indexes.iterator(); it.hasNext(); ) {
			Integer index = it.next();
			IndexValue indexValue = clusterItem.getIndexValue(index);
			
			add(new IndexValue(index.intValue(), indexValue.getValue()));
		}
	}
	
	/**
	 * Gets the sum of the variances.
	 * @return double.
	 */
	public double getSumOfVariances() {
		double[] variances = getVariances();
		double sum = 0.0d;
		for(double variance : variances) {
			sum += variance;
		}
		return sum;
	}
	
	/**
	 * Gets the variances for each column. Note: not
	 * all variances for all columns will be returned.
	 * Only columns for which there are values will
	 * be returned.
	 * @return Variances.
	 */
	public double[] getVariances() {
		int numIndexes = indices.size();
		double[] variances = new double[numIndexes];
		double[] sums = new double[numIndexes];
		
		int i = 0;
		for(Iterator<Integer> it = indices.iterator(); it.hasNext(); i++) {
			Integer index = it.next();
			for(ClusterItem clusterItem : clusterItems) {
				sums[i] += clusterItem.getValue(index);
			}
		}
		
		i = 0;
		double n = clusterItems.size();
		for(Iterator<Integer> it = indices.iterator(); it.hasNext(); i++) {
			Integer index = it.next();
			double mean = sums[i] / n;
			for(ClusterItem clusterItem : clusterItems) {
				double x = clusterItem.getValue(index);
				double diff = x - mean;
				double sq = diff * diff;
				variances[i] += sq;
			}
		}
		
		return variances;
	}
	
	public Set<Integer> getIndices() {
		return indices;
	}
	
	public ClusterItem getClusterItem(int index) {
		return clusterItems.get(index);
	}
	
	public int getNumOfClusterItems() {
		return clusterItems.size();
	}
	
	public void add(ClusterItem clusterItem) {
		clusterItems.add(clusterItem);
		indices.addAll(clusterItem.getIndexes());
	}
	
	public void clear() {
		clusterItems.clear();
		indices.clear();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(' ');
		sb.append(getNumOfClusterItems());
		return sb.toString();
	}
}
