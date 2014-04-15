package DistributedMining;




import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class JobThread extends Thread {

    private JobInfo job;
    private Queue<Organism> currentPopulation;
    private Queue<Organism> nextPopulation;
    private PriorityBlockingQueue<Organism> queue;

    public JobThread(JobInfo job, Queue<Organism> currentPopulation, Queue<Organism> nextPopulation, PriorityBlockingQueue<Organism> queue) {
    	super("JobThread");
        this.job = job;
        this.currentPopulation = currentPopulation;
        this.nextPopulation = nextPopulation;
        this.queue = queue;
    }

    public void run() {
//        String input = job.getInput();
//        String output = job.getOutput();


        for(int j = 0; j < this.currentPopulation.size()/2; j++){
            Organism org1 = this.currentPopulation.poll();
            Organism org2 = this.currentPopulation.poll();

            Queue<String> children = JobInfo.breed(org1.getData(), org2.getData());

            String child1 = JobInfo.mutate(children.poll(), job.getP_mutation());
            String child2 = JobInfo.mutate(children.poll(), job.getP_mutation());

            Organism org3 = new Organism(child1, JobInfo.fitness(child1, job.getOutput()));
            Organism org4 = new Organism(child2, JobInfo.fitness(child2, job.getOutput()));

            this.nextPopulation.add(org1);
            this.nextPopulation.add(org2);
            this.nextPopulation.add(org3);
            this.nextPopulation.add(org4);
        }

        this.queue.addAll(this.nextPopulation);
    }
}
