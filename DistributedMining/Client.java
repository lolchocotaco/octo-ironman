package DistributedMining;

import java.io.*;
import java.net.*;


// Listen for response from server to connect to correct worker
public class Client {
    public static void main(String[] args) throws IOException {

        if (args.length != 5) {
            System.err.println(
                "Usage: java Client <LB Host Name> <LB port number> <input> <output> <p_mutation>");
            System.exit(1);
        }

        if( args[2].length() != args[3].length()){
            System.err.println("Input and Output string must be the same length");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        JobInfo job = new JobInfo(args[2],args[3],Double.parseDouble(args[4]));


        try {
            Socket socket = new Socket(hostName, portNumber);
            System.out.println("Connection Established to load balancer");

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            WorkerData data = (WorkerData) ois.readObject();
            System.out.println("Received data: " + data);
            ois.close();
            is.close();
            socket.close();

            System.out.println(data.toString());
            System.out.println("Connecting to worker...");
            Socket workerSocket = new Socket(data.getHostname(), data.getPortNumber());
            System.out.println(workerSocket.toString());

            OutputStream os = workerSocket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(os);

            System.out.println("Giving job to worker...");
            out.writeObject(job);
//            out.flush();

            System.out.println("Job sent over socket");

            is = workerSocket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(is);

            JobInfo result = (JobInfo)in.readObject();
            System.out.println("String sent    : "+ job.getInput());
            System.out.println("Result received: "+ result.getEstimate());
            System.out.println("Goal State     : "+ job.getOutput());

            out.close();
            os.close();
            in.close();
            is.close();

            workerSocket.close();


        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
