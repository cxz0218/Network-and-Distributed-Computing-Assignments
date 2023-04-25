package socket.tcp.echo;

import java.io.*;
import java.net.Socket;

public class Handler implements Runnable{
    //Socket socket;
    private Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    PrintWriter printWriter;
    public Handler(Socket socket) {
        this.socket = socket;
    }

    public void initStream() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        printWriter = new PrintWriter(bufferedWriter,true);
    }

//    public static void main(String args[]){//不需要main函数调用run方法吗
//        new Handler().run();
//    }

    @Override
    public void run() {
        try {
            System.out.println("新连接，连接地址："+socket.getInetAddress()+":"+socket.getPort());//!!!
            initStream();
            String info = null;
            while ((info=bufferedReader.readLine())!=null){
                System.out.println(info);
                printWriter.println("you said:"+info);
                if(info.equals("quit"))
                    break;
            }
        }catch (IOException exception){
            exception.printStackTrace();
        }finally {
            if(socket!=null) {
                try {
                    socket.close();
                }catch (IOException exception){
                    exception.printStackTrace();
                }
            }
        }

    }
}
