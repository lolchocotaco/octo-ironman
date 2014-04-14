package DistributedMining;

import java.net.*;
import java.io.*;

public class JobHandler extends Thread {
    private Socket socket = null;

    public JobHandler(Socket socket) {
        super("DistributedMining.JobHandler");
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println("Setting up connection to worker/ waiting for hash");
            InputStream is = socket.getInputStream();

            ObjectInputStream in = new ObjectInputStream(is);

            //BufferedImage imBuff = ImageIO.read(in);
            System.out.println(socket.toString());


            JobInfo job = (JobInfo)in.readObject();

            System.out.println("Job received: "+ job.toString());
            // Set up threadpools with mining threads.
            // run mining threads

//            JobInfo result = new JobInfo("");
            Thread jobThread = new JobThread(job);
            jobThread.start();
            jobThread.join();

            OutputStream os = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.writeObject(job);
//            out.flush();
            in.close();
            is.close();

            out.close();
            os.close();

            socket.close();
            System.out.println("Socket Connection Closed");
//            this.join();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
