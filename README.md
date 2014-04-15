octo-ironman
============

A distributed [genetic algorithm](http://en.wikipedia.org/wiki/Genetic_algorithm) implementation in Java. This infrastructure supports multiple clients over a network of multiple worker machines.

The [algorithm implemented](http://www.electricmonk.nl/log/2011/09/28/evolutionary-algorithm-evolving-hello-world/) to test the system involves iterating a "population" of arbitrary strings through several generations until they converge to a desired output string. At each stage, half of the population is selected to continue to the next generation depending on the "fitness" of each string (how close its ASCII representation is to the target string).

The system has one load balancer, which maintains multiple workers in a priority queue sorted by worker load (the number of clients that are being processed at any given time). Each worker spawns a new JobHandler thread that establishes a socket connection with the client and receives a job. The JobHandler then distributes the production of each generation of the genetic algorithm to several additional threads until a termination condition.

How to Run Me
------------
compile the java code
```
make
```

run the load balancer
```
java DistributedMining.LoadBalancer <port number for clients> <port number for workers>
```

run one or more workers
```
java DistributedMining.Worker <port number for clients> <hostname of LB> <port number of LB>
```

run one or more client jobs
```
java DistributedMining.Client <LB Host Name> <LB port number> <input> <output> <population_size> <p_mutation>
```

System Performance
------------------

The performance of the system was measured by timing how long a set of 10 jobs took to complete as the number of nodes was increased. The following graph shows a sharp increase in performance (red) until the jobs were well distributed among worker threads.

![alt tag](http://i.imgur.com/SeAYQLH.jpg)
