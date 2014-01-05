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

/**
 * A {@link ClusterItem} that has a class associated.
 * @author Jee Vang
 *
 */
public class ClusterItemClass extends ClusterItem {

	private String clazz;
	
	public ClusterItemClass() {
		super();
	}
	
	public ClusterItemClass(String id) {
		super(id);
	}
	
	public ClusterItemClass(String id, String clazz) {
		this(id);
		setClazz(clazz);
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}
