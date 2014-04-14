package DistributedMining;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class JobInfo implements Serializable {

    private String input;
    private String output;
    private String estimate;
    private int    n_iter;
    private double p_mutation;

    public JobInfo(String input, String output, double p_mutation) {
        this.input = input;
        this.output = output;
        this.p_mutation = p_mutation;
        this.n_iter = 0;
        this.estimate = "";
    }

    public static int fitness(String in, String out){
        int fitval = 0;
        for(int i = 0; i < in.length(); i++){
            fitval += Math.pow( in.charAt(i) - out.charAt(i), 2);
        }
//        System.out.println("Fitness Val: " + fitval);
        return fitval;
    }

    public static String mutate(String string1, double p_mutation){
        char charArray[] = string1.toCharArray();
        Random rand = new Random();

        for(int i = 0; i < charArray.length; i++){
            if(Math.random() < p_mutation) {
                charArray[i] += (rand.nextInt(2)-1);
            }
        }
        return String.valueOf(charArray);
    }

    public static Queue<String> breed(String string1, String string2){
        Random rand = new Random();
        int crossPoint = rand.nextInt(string1.length());
        Queue<String> children = new ArrayBlockingQueue<String>(2);
        children.add(string1.substring(0, crossPoint) + string2.substring(crossPoint, string2.length()));
        children.add( string2.substring(0,crossPoint) + string1.substring(crossPoint,string1.length()) );
//        Pair<String, String> children = new Pair(string1.substring(0,crossPoint) + string2.substring(crossPoint,string2.length()),
//                                                 string2.substring(0,crossPoint) + string1.substring(crossPoint,string1.length()) );
        return children;
    }

    public double getP_mutation() {
        return p_mutation;
    }

    public String getInput(){
		return this.input;
	}

    public String getOutput(){
        return this.output;
    }

    public void setIter (int n_iter){
        this.n_iter = n_iter;
    }
    public int getIter (){
        return this.n_iter;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }
}
