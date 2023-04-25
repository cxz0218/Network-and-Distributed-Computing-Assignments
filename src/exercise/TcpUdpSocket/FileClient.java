package exercise.TcpUdpSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Class <em>FileClient</em> is a class representing server side in exercise 1.
 * In Java Socket TCP and UDP to achieve a simple network file service
 * procedures, including server-side file server and client-side file client,
 * this is the client.
 *
 * @author 陈心琢-2018303015
 *
 */
public class FileClient {
    static final int PORT1 = 2021; // TCP connection port
    static final int PORT2 = 2020; // UDP connection port
    static final String HOST = "127.0.0.1"; // Connection address
    Socket socket = new Socket();

    /**
     * Create the client {@link Socket}.
     *
     * @throws UnknownHostException
     * @throws IOException
     */
    public FileClient() throws UnknownHostException, IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(HOST, PORT1));
    }

    /**
     * main function.
     *
     * @param args
     * @throws UnknownHostException
     * @throws IOException
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        new FileClient().send();
    }

    /**
     * The loop outputs the message returned by the server. TCP Connection, the
     * client may receive more than one row of data at a time, to read from the
     * input stream, the server can output more than one empty row at the end of
     * each output, the client will judge the end of each input based on the empty
     * row.
     *
     * @param br
     * @throws IOException
     */
    public void readmsg(BufferedReader br) throws IOException {
        while (true) {
            String out = br.readLine();
            if (out.equals(""))
                break;
            System.out.println(out);
        }
    }

    /**
     * Create the packet, specify the server address and then send a packet to the
     * server. When the client receives it, it loops over {@link DatagramPacket} for
     * file size/cache size + 1.
     *
     * @param br
     * @param msg
     * @throws IOException
     */
    public void getfile(BufferedReader br, String msg) throws IOException {
        String filelength = br.readLine();
        String fsuffix = br.readLine();
        FileOutputStream fos = new FileOutputStream(new File("D://exercise5_" + fsuffix));
        DatagramSocket ds = new DatagramSocket();
        SocketAddress socketAddres = new InetSocketAddress(HOST, PORT2);// Server-side address
        String s = msg;
        byte[] info = s.getBytes();
        // create the packet, specify the server address
        DatagramPacket dps = new DatagramPacket(info, info.length, socketAddres);
        ds.send(dps); // Send a packet to the server
        int len = Integer.parseInt(filelength);
        int time;
        if (len % (8 * 1024) != 0) {
            time = len / (8 * 1024) + 1;
        } else
            time = len / (8 * 1024);
        for (int i = 0; i < time; i++) {
            byte[] receive = new byte[8 * 1024];
            DatagramPacket dpr = new DatagramPacket(receive, receive.length);
            ds.receive(dpr);
            fos.write(dpr.getData());
            // fos.flush();
        }
        System.out.println("文件接收完毕");
    }

    /**
     * Send implements The client outputs the Stream and sends a message to the
     * server The client input stream receives the server message Decorative output
     * streams are refreshed in a timely manner. Judge the user input command, and
     * preliminary judge whether the command is valid, and then send the command to
     * the server.
     *
     */
    public void send() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(bw, true);
            System.out.println(br.readLine());
            Scanner in = new Scanner(System.in); // Receiving user information
            String msg = null;
            while ((msg = in.nextLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(msg, " ");
                String command = tokenizer.nextToken();
                if (command.equals("ls")) {
                    pw.println(msg); // send to the server
                    readmsg(br);
                } else if (command.equals("cd")) {
                    pw.println(msg);
                    readmsg(br);
                } else if (command.equals("cd..")) {
                    pw.println(msg);
                    readmsg(br);
                } else if (command.equals("get")) {
                    pw.println(msg);
                    String out;
                    int judge = 1;
                    while (true) {
                        out = br.readLine();
                        if (out.equals("unknown file"))
                            judge = 0;
                        if (out.equals(""))
                            break;
                        System.out.println(out);
                    }
                    if (judge == 1) {
                        getfile(br, msg);
                    }
                } else if (msg.equals("bye")) {
                    break; // Quit
                } else {
                    System.out.println("unknown cmd");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != socket) {
                try {
                    socket.close(); // Disconnect
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
