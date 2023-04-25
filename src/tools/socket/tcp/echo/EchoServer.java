package tools.socket.tcp.echo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class EchoServer {
    private int PORT = 8080;
    private int backlog = 10;//客户端连接请求的队列长度
    ServerSocket serverSocket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    public EchoServer() throws IOException {
        //InetAddress ia = InetAddress.getLocalHost();
        serverSocket = new ServerSocket(PORT, backlog);//系统默认队列长度 50
        serverSocket.setSoTimeout(7000);//设置等待客户连接时间不超过7秒

        System.out.println("服务器启动。");

    }

    public static void main(String[] args) throws IOException {
        //tools.socket.tcp.echo.EchoServer echoServer =new tools.socket.tcp.echo.EchoServer();
        //echoServer.service();
        new EchoServer().service(); // 启动服务!!!
    }

    public void service() {
        Socket socket = null;
        while(true){
            try{
                socket = serverSocket.accept();
                System.out.println("新连接，连接地址："+socket.getInetAddress()+":"+socket.getPort());//!!!
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                PrintWriter printWriter = new PrintWriter(bufferedWriter,true);
                String info = null;
                while ((info=bufferedReader.readLine())!=null){//!!!
                    System.out.println(info);
                    printWriter.println("you said:"+info);
                    if(info.equals("quit")){
                        break;
                    }
                }
            }catch(IOException exception){//如果客户端断开连接，则应捕获该异常，但不应中断整个while循环，使得服务器能继续与其他客户端通信
                System.out.println("该客户端连接断开");
                exception.printStackTrace();
            }finally {
                if (null != socket) {
                    try {
                        socket.close(); //断开连接
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}