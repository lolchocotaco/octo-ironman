package DistributedMining;

import java.net.*;
import java.io.*;
import java.util.concurrent.PriorityBlockingQueue;

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

            System.out.println(socket.toString());

            JobInfo job = (JobInfo)in.readObject();

            System.out.println("Job received: "+ job.toString());
            // Set up threadpools with mining threads.
            // run mining threads

            //set up queue
            int population = 12;
            OrganismComparator orgCompare = new OrganismComparator();
            PriorityBlockingQueue<Organism> queue = new PriorityBlockingQueue<Organism>(population, orgCompare);

            for( int i = 0; i < population; i++){
                String tmp1 = JobInfo.mutate(job.getInput(), job.getP_mutation());
                Organism temp = new Organism(tmp1, JobInfo.fitness(tmp1,job.getOutput()));
                queue.add(temp);
            }


            for(int i = 0; i < 10000; i++){
                Thread jobThread = new JobThread(job, queue);
                jobThread.start();
                jobThread.join();
            }

            System.out.println("Done Evolving");
            System.out.println(queue.peek().toString());
            job.setEstimate(queue.peek().getData());

            OutputStream os = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.writeObject(job);
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
