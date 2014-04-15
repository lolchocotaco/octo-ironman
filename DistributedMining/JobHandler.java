package DistributedMining;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
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

            //set up
            int n_threads = 3;
            int population = job.getPopulation_size();
            OrganismComparator orgCompare = new OrganismComparator();
            PriorityBlockingQueue<Organism> queue = new PriorityBlockingQueue<Organism>(population, orgCompare);
            ArrayList<Thread> threads = new ArrayList<>(n_threads);


            for( int i = 0; i < population; i++){
                String tmp1 = JobInfo.mutate(job.getInput(), job.getP_mutation());
                Organism temp = new Organism(tmp1, JobInfo.fitness(tmp1,job.getOutput()));
                queue.add(temp);
            }

            for(int i = 0; i < 10000; i++){

                //termination condition
                if(job.getOutput().equals(queue.peek().getData()))
                    break;

                //farm out portions of the current population
                for(int j = 0; j < n_threads; j++){
                    Queue<Organism> currentPopulation = new ArrayBlockingQueue<Organism>(population);
                    Queue<Organism> nextPopulation = new ArrayBlockingQueue<Organism>(population);
                    queue.drainTo(currentPopulation, population / n_threads / 2);   //drain some portion of the queue eventually
                    threads.add(new JobThread(job, currentPopulation, nextPopulation, queue));
                }

                //kill off the unfit organisms
                queue.clear();

                //breed, mutate, and compute fitness
                for(int j = 0; j < n_threads; j++){
                    threads.get(j).start();
                    threads.get(j).join();
                }

                threads.clear();
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
