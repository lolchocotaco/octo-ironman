package DistributedMining;

import threading.MasterWorkerListener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Redirects requests from Histogram Client to Proper worker.
 * Has 2 threads:
 *  One thread listens for incoming client connections:
 *  One thread listens for workers.
 */
public class LoadBalancer {
    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length != 2) {
            System.err.println("Usage: LoadBalancer <port number for clients> <port number for workers>");
            System.exit(1);
        }


        Comparator<WorkerData> comparator = new WorkerDataComparator();
        PriorityQueue<WorkerData> workerQueue = new PriorityQueue<>(10, comparator);
        Map <String, WorkerData> workerHash = new HashMap<>();

//        System.out.println("Waiting for workers...");
        new MasterWorkerListener(Integer.parseInt(args[1]), workerQueue, workerHash).start();

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Waiting for clients...");
            while (listening) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Accepted New Client Connection on Master");
                    OutputStream os = socket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    WorkerData data = null;
                    do {
                        if(workerQueue.isEmpty())
                        {
                            // Murder client
                            break;
                        }
                        data = workerQueue.poll();
                    }
                    while ((new Date().getTime() - data.getDate().getTime() >= 2 * 1000));
		    
	            	data.incrementCount();
	            	workerQueue.add(data);
                    //workerHash.remove(data.getHashKey());
                    //System.out.println("Next worker: " + data);
                    oos.writeObject(data);
                    oos.close();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }

    }

    public void clientListen(){

    }
}

