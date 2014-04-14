package DistributedMining;

public class Organism {

    private String data;
    private int    fitnessVal;

    public Organism(String data, int fitnessVal) {
        this.data = data;
        this.fitnessVal = fitnessVal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getFitnessVal() {
        return fitnessVal;
    }

    public void setFitnessVal(int fitnessVal) {
        this.fitnessVal = fitnessVal;
    }

    @Override
    public String toString(){
        return "Data: " + this.data + "--FitVal: " + this.fitnessVal;
    }
}
