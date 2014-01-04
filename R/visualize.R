#  Copyright 2014 Jee Vang
#
#   Licensed under the Apache License, Version 2.0 (the "License");
#   you may not use this file except in compliance with the License.
#   You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.
 
data <- read.csv("out.data",header=FALSE);
names(data) <- c("id","x","y");
plot(density(data$x,kernel="gaussian",bw=0.5));
plot(density(data$y,kernel="gaussian",bw=0.5));

library(MASS);
contour(kde2d(data$x,data$y,n=100,h=c(1.5,1.5)));