package exercise.TcpUdpSocket;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class <em>FileServer</em> is a class representing server side in exercise 1.
 * Based on {@link Socket} TCP and UDP to achieve a simple network file service
 * procedures, this for the server side, to support multiuser concurrent access.
 *
 * @author 陈心琢-2018303015
 *
 */
public class FileServer {
    String currentroad = "D:\\java";// Current directory
    String rootroad = new File("relative path").getAbsolutePath();// Gets the root directory of the class load
    ServerSocket serverSocket;
    private final int PORT1 = 2021; // TCP port
    ExecutorService executorService; // Thread Pool
    final int POOL_SIZE = 20; // Number of worker threads in a single processor thread pool

    /**
     * Create a thread pool. The {@link Runtime#availableProcessors()} returns the
     * number of processors currently available on the system. It is up to the JVM
     * to determine the number of threads, depending on the system.
     *
     * @throws IOException
     */
    public FileServer() throws IOException {
        serverSocket = new ServerSocket(PORT1); // Create A server-side tools.socket
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
        System.out.println(rootroad);
        System.out.println("服务器启动");

    }

    /**
     * Main function. Start the service.
     *
     * @param args
     * @throws IOException
     *
     */
    public static void main(String[] args) throws IOException {
        new FileServer().servic();

    }

    /**
     * Service implements. Wait and take out the user connection, and create the
     * tools.socket. Commit execution to the thread pool for maintenance.
     */
    public void servic() {
        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
                executorService.execute(new Handler(socket));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
