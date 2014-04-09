package DistributedMining;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MiningThread extends Thread {
    private int minVal;
    private int maxVal;
    private String hashString;
    private StringContainer result;

    public MiningThread(String hashString, StringContainer result, int minVal, int maxVal) {
    	super("MiningThread");
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.hashString = hashString;
        this.result = result;
    }

    public void run() {
        try {
            System.out.println("Processing Request");
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

//            String result = null;
            String out = null;
            long x = 0;
            while(x >= minVal && x <=maxVal)
            {

            	byte[] hash = digest.digest(hashString.getBytes("UTF-8"));
            	StringBuffer hexString = new StringBuffer();

            	for (int i = 0; i < hash.length; i++) {
            		String hex = Integer.toHexString(0xff & hash[i]);
            		if(hex.length() == 1)
                        hexString.append('0');
            		hexString.append(hex);
            	}

            	out = hexString.toString();
//                System.out.println(out);
            	if(out.length() > 10)
            	{
            		int i;
            		for(i = 0; i < 10; i++)
            		{
            			if(out.charAt(i) == '0')
            				continue;
            			else
            				break;
            		}
            		if(i == 10)
            		{
            			result.setString(out);
            			break;
            		}
            	}

            	x++;
            }
            result.setString(out);
           // Have result containing hash'ed
            System.out.println("Hash'd Result: " + result.GetString());
//            this.notifyAll();
//            this.join();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
