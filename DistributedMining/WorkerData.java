package DistributedMining;

import java.io.Serializable;
import java.util.Date;

public class WorkerData implements Serializable {


    private String hostname;
    private int portNumber;
    private double workerLoad;
    private Date date;

    public WorkerData(String hostname, int portNumber, double workerLoad, Date date) {
        this.hostname = hostname;
        this.portNumber = portNumber;
        this.workerLoad = workerLoad;
        this.date = date;
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
