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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

/**
 * Generates random data. The columns/fields
 * are as follows.
 * <ul>
 *  <li>id</li>
 *  <li>x</li>
 *  <li>y</li>
 * </ul>
 * There are 2 centers at (0,0) and (10,10). When
 * the data is generated, a delta in the range [0,1]
 * is added to one of these centers.
 * @author Jee Vang
 *
 */
public class GenerateData {

	private static final Random _r = new Random(37L);
	
	public static void main(String[] args) {
		final int n = 100000;
		double p = 0.5d;
		double c1x = 0.0d;
		double c1y = 0.0d;
		double c2x = 10.0d;
		double c2y = 10.0d;
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File("out.data")));
			StringBuilder sb = new StringBuilder();
			final String nl = System.getProperty("line.separator");
			
			for(int i=0; i < n; i++) {
				double t = _r.nextDouble();
				double cx = c1x;
				double cy = c1y;
				
				if(t >= p) {
					cx = c2x;
					cy = c2y;
				}
				
				double dx = _r.nextDouble();
				double dy = _r.nextDouble();
				cx += dx;
				cy += dy;
				
				sb.setLength(0);
				sb.append(i);
				sb.append(',');
				sb.append(cx);
				sb.append(',');
				sb.append(cy);
				sb.append(nl);
				writer.write(sb.toString());
			}
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if(null != writer) { 
				try { writer.close(); }
				catch(Exception ex) { }
			}
		}
		
		
	}

}
