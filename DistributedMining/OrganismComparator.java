package DistributedMining;

import java.util.Comparator;

public class OrganismComparator implements Comparator<Organism> {

    @Override
    public int compare(Organism x, Organism y){
        if (x.getFitnessVal() < y.getFitnessVal()) {
            return -1;
        }
        else if (x.getFitnessVal()> y.getFitnessVal()) {
            return 1;
        }
        return 0;
    }
}