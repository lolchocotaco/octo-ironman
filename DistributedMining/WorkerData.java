package DistributedMining;

import java.io.Serializable;
import java.util.Date;

public class WorkerData implements Serializable {


    private String hostname;
    private int portNumber;
    private double workerLoad;
    private int clientCount;
    private Date date;

    public WorkerData(String hostname, int portNumber, double workerLoad, int clientCount, Date date) {
        this.hostname = hostname;
        this.portNumber = portNumber;
        this.workerLoad = workerLoad;
        this.clientCount = clientCount;
        this.date = date;
    }

    public void incrementCount() {
	this.clientCount++;
    }

    public String getHostname() {
        return this.hostname;
    }

    public int getPortNumber() {
        return this.portNumber;
    }

    public double getWorkerLoad() {
        return this.workerLoad;
    }

    public int getClientCount() {
	return this.clientCount;
    }

    public String getHashKey(){
        return this.hostname + ":" + this.portNumber;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString(){
        return this.hostname + ":" + this.portNumber + " load: " + this.workerLoad;
    }
}
