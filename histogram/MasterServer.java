package histogram;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Redirects requests from Histogram Client to Proper worker.
 * Has 2 threads:
 *  One thread listens for incoming client connections:
 *  One thread listens for workers.
 */
public class MasterServer {
    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length != 1) {
            System.err.println("Usage: java histogram.MasterServer <port number>");
            System.exit(1);
        }

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

