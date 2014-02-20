all:
	javac histogram/HistogramClient.java
	javac histogram/Worker.java
	javac histogram/MasterServer.java
	javac threading/MyMonitorThread.java
	javac threading/MasterWorkerListener.java
clean:
	rm *.class
