package DistributedMining;

import java.net.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MineHandler extends Thread {
    private Socket socket = null;

    public MineHandler(Socket socket) {
        super("DistributedMining.MineHandler");
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println("Setting up connection to worker/ waiting for hash");
            InputStream is = socket.getInputStream();

            ObjectInputStream in = new ObjectInputStream(is);

            //BufferedImage imBuff = ImageIO.read(in);
            System.out.println(socket.toString());


            StringContainer hashString = (StringContainer)in.readObject();

            System.out.println("Hash received: "+ hashString.GetString());
            // Set up threadpools with mining threads.
            // run mining threads
            StringContainer result = new StringContainer("");
            Thread miningThread = new MiningThread(hashString.GetString(),result, 0, 1000);
            miningThread.start();
            miningThread.join();

            System.out.println("Result from thread: " + result.GetString());
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.writeObject(result);
//            out.flush();
            in.close();
            is.close();

            out.close();
            os.close();

            socket.close();
            System.out.println("Socket Connection Closed");
//            this.join();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
