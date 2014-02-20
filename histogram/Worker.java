package histogram;

import threading.MyMonitorThread;
import threading.RejectionHandler;

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

/**
 * Handles 2 Images at a time
 * Queues up 4 images to be processed
 */



public class Worker {
    protected static final int THREAD_POOL_SIZE = 2;
    protected static final int MAX_POOL_SIZE = 4;
    protected static final int KEEP_ALIVE_TIME = 10;
    protected static final int WORK_Q_SIZE = 2;



    public static void main(String[] args) throws IOException, InterruptedException {

    if (args.length != 3) {
        System.err.println("Usage: java histogram.Worker <port number for clients> <hostname of master> <port number of master>");
        System.exit(1);
    }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;


        //RejectedExecutionHandler implementation
        RejectionHandler rejectionHandler = new RejectionHandler();
        //Get the ThreadFactory implementation to use
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //creating the ThreadPoolExecutor
        ThreadPoolExecutor executorPool =
                new ThreadPoolExecutor(THREAD_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(WORK_Q_SIZE), threadFactory, rejectionHandler);
//
//        start the monitoring thread
        MyMonitorThread monitor = new MyMonitorThread(executorPool, 10, args[1], Integer.parseInt(args[2]), portNumber);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();


        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Listening for Client Connections on worker");
            while (listening) {
                System.out.println("Waiting for next connection");
                Socket s = serverSocket.accept();
                System.out.println("New Connection");
                executorPool.execute( new ImageThread(s) );
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }

        Thread.sleep(5000);
        executorPool.shutdown();
    }
}
