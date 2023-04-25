package socket.tcp.echo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerMultiThread {
    //private int PORT = 8080;
    private final int  PORT = 8080;
    private int backlog = 10;
    ServerSocket serverSocket;

    public EchoServerMultiThread() throws IOException {
        serverSocket = new ServerSocket(PORT, backlog);
        //serverSocket.setSoTimeout(7000);//设置等待客户连接时间不超过7秒
        System.out.println("服务器启动。");
    }

    public void service(){
        //Socket socket = null;
        //Thread thread = null;
        while (true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("新连接，连接地址："+socket.getInetAddress()+":"+socket.getPort());//!!!
                Thread work = new Thread(new Handler(socket));
                work.start();
            }catch (IOException exception){
                exception.printStackTrace();
            }finally {

            }
        }
    }

    public static void main(String[] args) throws IOException {
        //socket.tcp.echo.EchoServer echoServer =new socket.tcp.echo.EchoServer();
        //echoServer.service();
        new EchoServerMultiThread().service(); // 启动服务!!!
    }




}
