all:
	javac threading/SimpleThreadPool.java
	javac histogram/HistogramClient.java
	javac histogram/Worker.java
clean:
	rm *.class
