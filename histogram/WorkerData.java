package histogram;

import java.io.Serializable;

public class WorkerData implements Serializable {

    private String hostname;
    private int portNumber;
    private double workerLoad;

    public WorkerData(String hostname, int portNumber, double workerLoad) {
        this.hostname = hostname;
        this.portNumber = portNumber;
        this.workerLoad = workerLoad;
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

    @Override
    public String toString(){
        return this.hostname + ":" + this.portNumber + " load: " + this.workerLoad;
    }
}
