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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Cluster item.
 * @author Jee Vang
 *
 */
public class ClusterItem {

	private String id;
	private Map<Integer, IndexValue> indexValues;
	
	/**
	 * Constructor.
	 */
	public ClusterItem() { 
		this("");
	}
	
	/**
	 * Constructor.
	 * @param id Id.
	 */
	public ClusterItem(String id) {
		this.id = id;
		this.indexValues = new HashMap<Integer, IndexValue>();
	}
			
	/**
	 * Gets the value at the specified index.
	 * @param index Index.
	 * @return double.
	 */
	public double getValue(Integer index) {
		if(indexValues.containsKey(index))
			return indexValues.get(index).getValue();
		return 0.0d;
	}
	
	/**
	 * Gets the {@link IndexValue} specified by the
	 * index.
	 * @param index {@link Integer}.
	 * @return {@link IndexValue}.
	 */
	public IndexValue getIndexValue(Integer index) {
		if(indexValues.containsKey(index))
			return indexValues.get(index);
		throw new IllegalArgumentException("cannot find IndexValue for index = " + index);
	}
	
	/**
	 * Gets the set of indexes.
	 * @return {@link Set} of {@link Integer}.
	 */
	public Set<Integer> getIndexes() {
		return indexValues.keySet();
	}
		
	/**
	 * Adds an {@link IndexValue}.
	 * @param indexValue {@link IndexValue}.
	 */
	public void add(IndexValue indexValue) {
		indexValues.put(indexValue.getIndex(), indexValue);
	}
	
	/**
	 * Gets the number of values.
	 * @return int.
	 */
	public int size() {
		return indexValues.size();
	}

	public String getId() {
		return id;
	}
	
	public String toString() {
		List<IndexValue> list = new ArrayList<IndexValue>();
		list.addAll(indexValues.values());
		Collections.sort(list, new Comparator<IndexValue>() {
			@Override
			public int compare(IndexValue o1, IndexValue o2) {
				Integer i1 = new Integer(o1.getIndex());
				Integer i2 = new Integer(o2.getIndex());
				return i1.compareTo(i2);
			}
		});
		
		StringBuilder sb = new StringBuilder();
		int size = list.size();
		
		sb.append('[');
		for(int i=0; i < size; i++) {
			IndexValue indexValue = list.get(i);
			
			sb.append(indexValue.getIndex());
			sb.append(':');
			sb.append(indexValue.getValue());
			
			if(i < size-1) 
				sb.append(',');
		}
		sb.append(']');
		
		return sb.toString();
	}

	public void setId(String id) {
		this.id = id;
	}
}
