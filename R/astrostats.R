# taken from http://astrostatistics.psu.edu/su09/lecturenotes/clus2.html

#import data
dat = read.table("sat.dat")
names(dat) = c("red","green","blue")
attach(dat)

#specify colors
mycol = rgb(red,green,blue,max=255)

#specify size of image
rows = 1:75
columns = 1:40

#create matrix
z = matrix(1:3000,nrow=75)

#visualize original image
jpeg(filename="clusters-original.jpg")
image(rows,columns,z,col=mycol)
dev.off()

#visualize clusters
for (i in 2:6) {
	fname = paste("clusters-",i,".jpg",sep="")
	jpeg(filename=fname)
	cl = kmeans(dat, i)
	c = rgb(cl$cen[,1], cl$cen[,2], cl$cen[,3], max=255)
	image(rows, columns, matrix(cl$clus, 75, 40), col=c)
	dev.off()
}