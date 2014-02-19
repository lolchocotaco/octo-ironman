all:
	javac threading.SimpleThreadPool.java
	javac histogram.HistogramClient.java
	javac histogram.KKMultiServer.java
clean:
	rm *.class
