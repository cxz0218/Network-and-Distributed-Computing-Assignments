package exercise.HttpWebServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The server end of the whole system. There is a thread pool defined in this
 * class, with the limitation of {@code 6 threads} to run at the same time.
 *
 * @author 陈心琢-2018303015
 * @see ExecutorService
 * @see ServerSocket
 */
public class HttpServer {
    private ServerSocket serverSocket = null;
    private ExecutorService executorService;

    /**
     * {@value} The port used for {@code TCP} communication.
     */
    public static final int TCP_PORT = 80;

    /**
     * The constructor to initialize an instance of {@link ServerSocket}, bind with
     * the specified TCP port . The thread pool capacity is set to {@code 6}.
     *
     */
    public HttpServer() {
        try {
            serverSocket = new ServerSocket(TCP_PORT);
            executorService = Executors.newFixedThreadPool(6);
            System.out.println("Server started on port:" + serverSocket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new HttpServer().run();
    }

    /**
     * The method that appoints the child thread.
     *
     * @see ConcurrentServer
     */
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                executorService.execute(new ConcurrentServer(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

