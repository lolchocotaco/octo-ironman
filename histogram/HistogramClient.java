package histogram;

import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


// TODO: Connect to MasterServer
// Listen for response from server to connect to correct worker
public class HistogramClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 3) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number> <Image File>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try {
            Socket socket = new Socket(hostName, portNumber);
            System.out.println("Connection Established");

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            WorkerData data = (WorkerData) ois.readObject();
            System.out.println("Received data: " + data);
            ois.close();
            is.close();
            socket.close();

            Socket workerSocket = new Socket(data.getHostname(), data.getPortNumber());


            DataInputStream in = new DataInputStream(workerSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(workerSocket.getOutputStream());
            BufferedImage bimg = ImageIO.read(new File(args[2]));
            System.out.println("Image read in");
            //ImageIO.write(bimg, "JPG", out);

            ByteArrayOutputStream byteArrayO = new ByteArrayOutputStream();
            ImageIO.write(bimg,"JPG",byteArrayO);
            byte [] byteArray = byteArrayO.toByteArray();
            out.writeInt(byteArray.length);
            out.write(byteArray);
            //out.flush();
            System.out.println("Image sent over socket");
            BufferedImage result = ImageIO.read(in);
            System.out.println("Result received");
            ImageIO.write(result, "JPG", new File(args[2]+"-new.jpg"));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
