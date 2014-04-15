package DistributedMining;




import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class JobThread extends Thread {

    private JobInfo job;
    private PriorityBlockingQueue<Organism> queue;

    public JobThread(JobInfo job, PriorityBlockingQueue<Organism> queue) {
    	super("JobThread");
        this.job = job;
        this.queue = queue;
    }

    public void run() {
        System.out.println("Processing Request");

        int population = queue.size();

        String input = job.getInput();
        String output = job.getOutput();

        Queue<Organism> currentPopulation = new ArrayBlockingQueue<Organism>(population);
        Queue<Organism> nextPopulation = new ArrayBlockingQueue<Organism>(population);
        queue.drainTo(currentPopulation, queue.size()/2);   //drain some portion of the queue eventually
        queue.clear();

        for(int j = 0; j < currentPopulation.size()/2; j++){
            Organism org1 = currentPopulation.poll();
            Organism org2 = currentPopulation.poll();

            Queue<String> children = JobInfo.breed(org1.getData(), org2.getData());

            String child1 = JobInfo.mutate(children.poll(), job.getP_mutation());
            String child2 = JobInfo.mutate(children.poll(), job.getP_mutation());

            Organism org3 = new Organism(child1, JobInfo.fitness(child1, job.getOutput()));
            Organism org4 = new Organism(child2, JobInfo.fitness(child2, job.getOutput()));

            nextPopulation.add(org1);
            nextPopulation.add(org2);
            nextPopulation.add(org3);
            nextPopulation.add(org4);
        }

        queue.addAll(nextPopulation);
    }
}
