all:
	javac DistributedMining/*.java
	javac threading/*.java
clean:
	rm DistributedMining/*.class
	rm threading/*.class	
