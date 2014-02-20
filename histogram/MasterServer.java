package histogram;

import threading.MasterWorkerListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Redirects requests from Histogram Client to Proper worker.
 * Has 2 threads:
 *  One thread listens for incoming client connections:
 *  One thread listens for workers.
 */
public class MasterServer {
    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length != 2) {
            System.err.println("Usage: java histogram.MasterServer <port number for clients> <port number for workers>");
            System.exit(1);
        }


        Comparator<WorkerData> comparator = new WorkerDataComparator();
        PriorityQueue<WorkerData> workerQueue = new PriorityQueue<>(10, comparator);
        Map <String, WorkerData> workerHash = new HashMap<>();

        new MasterWorkerListener(Integer.parseInt(args[1]), workerQueue, workerHash).run();

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                System.out.println("New Connection");
                // TODO: Determine which worker to connect to
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }

    }

    public void clientListen(){

    }
}

