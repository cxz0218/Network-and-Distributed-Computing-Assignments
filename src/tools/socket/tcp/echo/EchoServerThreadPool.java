package socket.tcp.echo;

import java.io.IOException;
import java.lang.reflect.Executable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServerThreadPool {
    ServerSocket serverSocket;
    private int PORT = 8080;
    //private int nThreads = 3;
    ExecutorService executorService;
    final int POOL_SOZE = 4;

    public EchoServerThreadPool() throws IOException {
        serverSocket = new ServerSocket(PORT);
        //Runtime的availableProcessors()方法返回当前系统可用处理器的数目
        //FixedThreadPool:固定线程池
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SOZE);//由JVM根据系统的情况来决定线程的数量
        System.out.println("服务器启动！IP地址："+ InetAddress.getLocalHost()+"端口："+PORT);
        System.out.println("当前系统可用处理器的数目："+Runtime.getRuntime().availableProcessors());
        //serverSocket.setSoTimeout(3000);//等待客户连接超时时间
    }

    public static void main(String args[]) throws IOException {
        new EchoServerThreadPool().service();
    }

    public void service(){
        Socket socket = null;
        while(true){
            try {
                socket = serverSocket.accept();
                socket.setSoTimeout(7000);//等待用户发数据的超时时间，避免用户一直不输入，他已经离开了
                executorService.execute(new Handler(socket));
            }catch(IOException exception){
                exception.printStackTrace();
            }
        }



    }
}
