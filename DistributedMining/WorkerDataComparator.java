package DistributedMining;

import java.util.Comparator;

public class WorkerDataComparator implements Comparator<WorkerData>{

    @Override
    public int compare(WorkerData x, WorkerData y){
        if (x.getWorkerLoad() < y.getWorkerLoad()) {
            return -1;
        }
        else if (x.getWorkerLoad() > y.getWorkerLoad()) {
            return 1;
        }
        return 0;
    }
}
