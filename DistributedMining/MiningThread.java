//package DistributedMining;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//
//public class MiningThread extends Thread {
//    private int minVal;
//    private int maxVal;
//    private String hashString;
//
//    public MiningThread(String hashString, int minVal, int maxVal) {
//    	super("MiningThread");
//        this.minVal = minVal;
//        this.maxVal = maxVal;
//        this.hashString = hashString;
//    }
//
//    public void run() {
//        try {
//            System.out.println("Processing Request");
////            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
////            StringContainer curJob = (StringContainer)ois.readObject();
//
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//
//            String result = null;
//
//            long x = 0;
//            while(x >= 0)
//            {
////            	String start = curJob.GetString();
////            	start.concat(Long.toString(x));
//
//            	byte[] hash = digest.digest(start.getBytes("UTF-8"));
//            	StringBuffer hexString = new StringBuffer();
//
//            	for (int i = 0; i < hash.length; i++) {
//            		String hex = Integer.toHexString(0xff & hash[i]);
//            		if(hex.length() == 1) hexString.append('0');
//            		hexString.append(hex);
//            	}
//
//            	String out = hexString.toString();
//            	if(out.length() > 10)
//            	{
//            		int i;
//            		for(i = 0; i < 10; i++)
//            		{
//            			if(out.charAt(i) == '0')
//            				continue;
//            			else
//            				break;
//            		}
//            		if(i == 10)
//            		{
//            			result = out;
//            			break;
//            		}
//            	}
//
//            	x++;
//            }
//
//
//            StringContainer resultCont = new StringContainer(result);
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            oos.writeObject(resultCont);
//            oos.flush();
//
//            socket.close();
//        } catch (IOException | NoSuchAlgorithmException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}
