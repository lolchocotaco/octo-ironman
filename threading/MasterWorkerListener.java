package threading;

import DistributedMining.WorkerData;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.PriorityQueue;

public class MasterWorkerListener extends Thread{

    int portNumber;
    ServerSocket socket;
    PriorityQueue<WorkerData> queue;
    Map<String, WorkerData> hash;

    public MasterWorkerListener(int port, PriorityQueue<WorkerData> queue, Map<String, WorkerData> hash) throws IOException {
        this.portNumber = port;
        this.socket = new ServerSocket(port);
        this.queue = queue;
        this.hash = hash;
    }

    public void run() {
        boolean listening = true;
        //System.out.println("Waiting for workers on master");
        while (listening) {
            try {
                Socket s = this.socket.accept();
                //System.out.println("Accepted New Worker Connection on Master");
                InputStream is = s.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                WorkerData lData = (WorkerData) ois.readObject();
                //System.out.println("Got Data Object:" + lData.toString());
                String key = lData.getHashKey();
                //System.out.println(this.queue);
                if (this.hash.containsKey(key)){
                    WorkerData toRemove = this.hash.get(key);
                    this.queue.remove(toRemove);
                }
                this.queue.add(lData);
                this.hash.put(key, lData);
                //System.out.println(this.queue);

                is.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
