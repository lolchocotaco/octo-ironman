octo-ironman
============

A distributed genetic algorithm implementation in Java. This infrastructure supports multiple clients over a network of multiple worker machines.


How to Run Me
------------

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

System Performance vs Nodes
----------------------------
![alt tag](http://i.imgur.com/SeAYQLH.jpg)
