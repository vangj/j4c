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
package net.j4c.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import net.j4c.ClusterItem;
import net.j4c.IndexValue;
import net.j4c.SilhouetteClusterer;
import net.j4c.centroid.Centroid;
import net.j4c.distance.MinkowskiDistance;

/**
 * Tests k-means algorithm on generated
 * data from <a href="http://astrostatistics.psu.edu/su09/lecturenotes/clus2.html">
 * http://astrostatistics.psu.edu/su09/lecturenotes/clus2.html</a>.
 * @author Jee Vang
 *
 */
public class TestAstroData {

	public static void main(String[] args) {
		String pathname = "C:\\Users\\root\\Downloads\\sat.dat"; //change this to your actual location
		List<ClusterItem> items = getClusterItems(pathname);
		SilhouetteClusterer clusterer = new SilhouetteClusterer();
		clusterer.setDistanceMeasure(new MinkowskiDistance(2.0d));
		
		List<Centroid> centroids = clusterer.getClusters(100, items);
		for(Centroid centroid : centroids) {
			System.out.println(centroid);
		}
	}
	
	/**
	 * Gets a {@link List} of {@link ClusterItem} from a 
	 * file (of generated data).
	 * @param pathname Pathname to file.
	 * @return {@link List} of {@link ClusterItem}.
	 */
	private static final List<ClusterItem> getClusterItems(String pathname) {
		List<ClusterItem> items = new ArrayList<ClusterItem>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(pathname)));
			String line = null;
			int id = 0;
			while(null != (line = reader.readLine())) {
				String[] tokens = line.split(" ");
				ClusterItem item = new ClusterItem(String.valueOf(id));
				for(int i=0; i < tokens.length; i++) {
					item.add(new IndexValue(i, Double.parseDouble(tokens[i])));
				}
				items.add(item);
				id++;
			}
		} catch(Exception ex) { 
			throw new RuntimeException(ex);
		} finally {
			if(null != reader) {
				try { reader.close(); }
				catch(Exception ex) { }
			}
		}
		return items;
	}

}
