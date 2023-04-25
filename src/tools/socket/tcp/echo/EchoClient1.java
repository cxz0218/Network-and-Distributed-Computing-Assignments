package socket.tcp.echo;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;


public class EchoClient1 {
    static final int PORT = 8080;
    static final String HOST = "127.0.0.1";
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    public EchoClient1() throws IOException {
        //socket = new Socket(HOST, PORT);
        socket = new Socket();
        socket.connect(new InetSocketAddress(HOST,PORT),3000);
        System.out.println("连接成功！");
    }

    public static void main(String args[]) throws IOException {
        new EchoClient().send();
    }
    public void send() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            PrintWriter printWriter = new PrintWriter(bufferedWriter, true);
            Scanner in = new Scanner(System.in);
            String msg = null;
//        msg = in.next();
//        while(msg!=null){
//            if (msg.equals("quit")){//quit需要发过去
//                break;
//            }
//            else{
//                printWriter.println(msg);
//                msg = in.next();
//            }
//        }
            while ((msg = in.next()) != null) {
                printWriter.println(msg);
                System.out.println(bufferedReader.readLine());//输出服务器返回的消息
                if (msg.equals("quit"))
                    break;
            }
        }catch (IOException exception){
            exception.printStackTrace();
        }
        finally {
            try {
                if (socket != null)
                    socket.close();
                if (bufferedReader != null)
                    bufferedReader.close();
                if (printWriter != null)
                    printWriter.close();
            }catch (IOException exception){
                exception.printStackTrace();
            }


        }

    }


}
