package exercise.exercise2;

import java.io.*;
import java.net.Socket;

/**
 * This is concurrent server bind with the server end. Each time a new client
 * connects to the server, an instance of this class will be implemented.
 * Besides, it handles every request initiated by the client to the server.
 *
 * @author 陈心琢-2018303015
 *
 **/
public class ConcurrentServer implements Runnable {
    private final Socket socket;

    private StringBuilder request;

    private BufferedInputStream clientInput;

    private BufferedOutputStream serverOutput;

    private String firstLine;

    /**
     * {@value} Specified file root directory in server end.
     */
    public static final String ROOT = "D:\\exercise2\\server\\";

    /**
     * Constructor of this class to initialize the {@code socket} each time an
     * instance is implemented.
     *
     * @param socket
     *            Specified socket for assignment.
     */
    public ConcurrentServer(Socket socket) {
        System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + ">Connected.");
        this.socket = socket;
    }

    /**
     * Accept two methods including {@code GET} and {@code PUT} and them process
     * them.
     */
    @Override
    public void run() {
        try {
            init();
            processRequest();
            String method;
            String path ;
            String version ;
            try {
                String[] cmd = firstLine.split(" ");
                method = cmd[0];
                path = cmd[1];
                version = cmd[2].split("/")[1];
                if (method.equals("GET")) {
                    new HttpResponseConstructor(serverOutput).constructResponse(200, Float.parseFloat(version), path);
                } else if (method.equals("PUT")) {// PUT方法
                    File file = new File(ROOT + path.replaceFirst("/", "").replaceAll("/", "\\\\"));
                    if (!file.exists()) {// 要传递的文件不存在
                        new HttpResponseConstructor(serverOutput).constructResponse(201, Float.parseFloat(version), path);
                    } else {// 要传递的文件已经存在
                        new HttpResponseConstructor(serverOutput).constructResponse(200, Float.parseFloat(version), path);
                    }
                    receiveFile(file);
                } else {
                    new HttpResponseConstructor(serverOutput).constructResponse(400, Float.parseFloat(version), path);
                }
            } catch (Exception e) {
                new HttpResponseConstructor(serverOutput).constructResponse(400, (float)1.0, "path");
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                clientInput.close();
                serverOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Receive a file from the client. This is used when the HTTP request has
     * {@code PUT} method.
     *
     * @param file
     *            The file to receive.
     * @throws IOException
     *             If any IO exception occurs.
     */
    private void receiveFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        int c;
        while (clientInput.available() > 0 && (c = clientInput.read()) != -1) {
            fos.write(c);
        }
        fos.flush();
        fos.close();
    }

    /*
     * This is a private method to initialize IO stream and request.
     */
    private void init() throws IOException {
        request = new StringBuilder();
        clientInput = new BufferedInputStream(socket.getInputStream());
        serverOutput = new BufferedOutputStream(socket.getOutputStream());
    }

    /*
     * This is a private method to precess the request.
     */
    private void processRequest() throws IOException {
        int last = 0, c;
        boolean inHeader = true;
        boolean flag = false;
        StringBuilder mark = new StringBuilder();
        while (inHeader && (c = clientInput.read()) != -1) {
            switch (c) {
                case '\r':
                    break;
                case '\n':
                    if (c == last) {
                        inHeader = false;
                        break;
                    }
                    last = c;
                    request.append(mark).append("\n");
                    if (!flag) {
                        firstLine = mark.toString();
                    }
                    mark = new StringBuilder();
                    flag = true;
                    break;
                default:
                    last = c;
                    mark.append((char) c);
            }
        }
    }
}
