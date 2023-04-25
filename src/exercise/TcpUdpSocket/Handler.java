package exercise.TcpUdpSocket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/**
 * Class <em>Handler</em> is a class representing work thread in exercise 1. A
 * thread responsible for communicating with a single customer.
 *
 *
 * @author 陈心琢-2018303015
 */
public class Handler implements Runnable {
    private Socket socket;
    BufferedReader br;
    BufferedWriter bw;
    PrintWriter pw;
    String currentroad = "D:\\java";// Current directory
    String rootroad = new File("relative path").getAbsolutePath();// Gets the root directory of the class load
    String fileroad;

    /**
     * @param socket
     *
     */
    public Handler(Socket socket) {
        this.socket = socket;
    }

    /**
     * Initialize an input-output stream object method. The input stream reads the
     * client information. The output stream writes information to the client.
     * Decorate the output stream, true, and flushes the output buffer for each line
     * written, without flush.
     *
     * @throws IOException
     */
    public void initStream() throws IOException {
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        pw = new PrintWriter(bw, true);
    }

    /**
     * This method executes the {@code cd} command, goes to the specified directory,
     * and determines if the directory exists, and gives a prompt. After
     * determination it will change the current directory to the specified one.The
     * CD command is successfully executed when the specified directory is entered.
     *
     * @param croad The specified directory to access
     */
    public void cd(String croad) {
        File file1 = new File(currentroad);
        String[] list = file1.list();
        for (int i = 0; i < list.length; i++) {// To determine if a directory exists
            if (list[i].equals(croad)) {
                currentroad = currentroad + "\\" + list[i];// Change the current directory
                pw.println(croad + " > OK");// The CD command was successfully executed when the specified directory was
                // entered
                pw.println();
                return;
            }
        }
        pw.println("The directory does not exist");
        pw.println();
    }

    /**
     * The server returns the current directory file list (<file/dir> name size).
     * Open the current directory,then open the files in the current directory in
     * sequence. Then determine whether the file is a directory or a plain file, to
     * determine the form of the output.
     */
    public void ls() {

        File file1 = new File(currentroad);// Open the current directory
        String[] list = file1.list();
        for (int i = 0; i < list.length; i++) {
            String s = currentroad + "\\" + list[i];// Open the files in the current directory in sequence
            File file = new File(s);
            String output = "";
            if (file.isDirectory()) {// Determine whether the file is a directory or a plain file
                output = "<dir> " + list[i] + " 0";

            } else {
                output = "<dir> " + list[i] + " " + file.length();
            }
            pw.println(output);
        }
        pw.println();
    }

    /**
     * You can backtrack to a higher directory, but do not make changes when the
     * current directory is the root directory. So determines whether the current
     * directory is the Root Directory, if the root directory does not change. When
     * connecting over TCP, the server side needs to save the information of the
     * user’s current Directory (the root directory specified for the server at the
     * time of the initial connection) through {@code cd ..} . Can be backed up to a
     * higher level directory, but not changed when the current directory is the
     * root directory.
     */
    public void cdlast() {
        if (currentroad.equals(rootroad)) {// Determines whether the current directory is the Root Directory, if the
            // root directory does not change
            int last = currentroad.lastIndexOf("\\");
            int length = currentroad.length();
            pw.println(currentroad.substring(last + 1, length - 1) + " > OK");
            pw.println();
            return;
        } else {
            int last = currentroad.lastIndexOf("\\");
            currentroad = currentroad.substring(0, last);
            int l = currentroad.lastIndexOf("\\");
            pw.println(currentroad.substring(l + 1, last) + " > OK");
            pw.println();
            return;
        }
    }

    /**
     * The execution of the get command: You can first send a TCP connection to
     * determine if the parameters after get are normal files in the current
     * directory. If not, reply to the prompt, if yes, reply to client-specific
     * information (such as OK) , and the physical path and size of the file, after
     * receiving OK, the client reads the subsequent information and requests the
     * file through UDP.
     *
     * @param filename
     * @throws IOException
     * @throws InterruptedException
     */
    public void get(String filename) throws IOException, InterruptedException {
        File file = new File(currentroad);
        String[] list = file.list();
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(filename)) {
                fileroad = currentroad + "\\" + list[i];
                File f = new File(fileroad);
                if (f.isDirectory()) {
                    pw.println("unknown file");
                    pw.println();
                    return;
                } else {
                    pw.println("OK " + fileroad + " " + f.length());
                    pw.println("开始接收文件");
                    pw.println();
                    pw.println(f.length());
                    pw.println(filename);
                    getfiles();
                    return;
                }
            }
        }
        pw.println("unknown file");
        pw.println();
        return;
    }

    /**
     * When UDP transfers a file, it is read into the byte cache in batches,
     * encapsulating a {@link DatagramPacket} one at a time and sending it to the
     * client in sequence. To ensure the order of delivery, it can be sent through:
     * {@link TimeUnit#MICROSECONDS}To limit the speed of delivery; when received by
     * the client, the {@link DatagramPacket} is cycled for file size/cache size +
     * 1.
     *
     * @see DatagramPacket
     * @see TimeUnit#MICROSECONDS
     * @throws IOException
     * @throws InterruptedException
     */
    public void getfiles() throws IOException, InterruptedException {
        final int PORTudp = 2020; // UDP port
        DatagramSocket socketudp;
        socketudp = new DatagramSocket(PORTudp);
        DatagramPacket dpr = new DatagramPacket(new byte[512], 512);
        socketudp.receive(dpr); // Receiving client information
        SocketAddress socketAddres = new InetSocketAddress(dpr.getAddress(), dpr.getPort());
        byte[] out = new byte[8 * 1024];
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(fileroad)));
        DatagramPacket dps = new DatagramPacket(out, out.length, socketAddres);
        int length;
        while (true) {
            length = dis.read(out);
            if (length == -1) {
                dis.close();
                break;
            }
            dps = new DatagramPacket(out, length, socketAddres);
            TimeUnit.MICROSECONDS.sleep(1);
            socketudp.send(dps);
        }
        socketudp.close();
        dis.close();
    }

    /**
     * Content of execution.It initializes the i/o stream object and will exit if
     * the user enters “bye”. After the disconnection, the exception will be caught,
     * but the while-loop should not be interrupted.
     */
    public void run() {
        try {
            initStream();
            pw.println(":" + socket.getInetAddress() + ">" + socket.getPort() + "连接成功"); // Client information
            String info = null;
            while (null != (info = br.readLine())) {
                if (info.equals("ls")) {
                    ls();
                } else if (info.equals("cd..")) {
                    cdlast();
                } else if (info.equals("bye")) { // Exit if the user enters a “bye”
                    break;
                } else {
                    StringTokenizer tokenizer = new StringTokenizer(info, " ");
                    String command = tokenizer.nextToken();
                    if (command.equals("cd")) {
                        String f = tokenizer.nextToken();
                        if (f.equals("..")) {
                            cdlast();
                        } else
                            cd(f);
                    } else if (command.equals("get")) {
                        String f = tokenizer.nextToken();
                        get(f);
                    }
                }
            }
        } // If the client disconnects, the exception should be caught, but the while loop
        // should not be interrupted
        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
