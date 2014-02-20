package threading;

import histogram.WorkerData;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.PriorityQueue;

public class MasterWorkerListener implements Runnable{

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
        while (listening) {
            try {
                Socket s = this.socket.accept();
                InputStream is = s.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                WorkerData lData = (WorkerData) ois.readObject();
                String key = lData.getHostname() + lData.getPortNumber();
                if (this.hash.containsKey(key)){
                    WorkerData toRemove = this.hash.get(key);
                    this.queue.remove(toRemove);
                }
                this.queue.add(lData);
                this.hash.put(key, lData);

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
