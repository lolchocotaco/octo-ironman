package threading;

import DistributedMining.WorkerData;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

/*
 * ThreadPoolExecutor provides several methods using which we can
 * find out the current state of executor, pool size, active thread
 * count and task count. So have a monitor thread that will print
 * the executor information at certain time interval.
 */

public class MyMonitorThread extends Thread {
    private ThreadPoolExecutor executor;
    private int seconds;
    private boolean run=true;
    private String hostname;
    private int portNumber;
    private int clientPortNumber;

 
    public MyMonitorThread(ThreadPoolExecutor executor, int delay, String hostname, int port, int clientPort) throws IOException {
        this.executor = executor;
        this.seconds=delay;
        this.hostname = hostname;
        this.portNumber=port;
        this.clientPortNumber = clientPort;
    }
     
    public void shutdown(){
        this.run=false;
    }

    public void run()
    {
        while(run){
            System.out.println(
                String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
                    this.executor.getPoolSize(),
                    this.executor.getCorePoolSize(),
                    this.executor.getActiveCount(),
                    this.executor.getCompletedTaskCount(),
                    this.executor.getTaskCount(),
                    this.executor.isShutdown(),
                    this.executor.isTerminated()));
            try {
                OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
                double load = osBean.getSystemLoadAverage();
                Socket socket = new Socket(this.hostname, this.portNumber);
                WorkerData data = new WorkerData(socket.getLocalAddress().getHostName(), this.clientPortNumber, load, new Date());
                OutputStream os = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(data);
                oos.close();
                os.close();
                System.out.println("MyMonitor sent data: " + data.toString());
                Thread.sleep(seconds*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(seconds*1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
             
    }
}