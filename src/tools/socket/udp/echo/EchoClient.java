package tools.socket.udp.echo;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class EchoClient {
    DatagramSocket datagramSocket;
    int remotePort = 8888;//服务器端口
    String remoteIp = "192.168.10.1";//服务器IP

    public EchoClient() throws SocketException {
        datagramSocket = new DatagramSocket();//随机可用端口，又称匿名端口
        System.out.println("客户端端口号："+datagramSocket.getLocalPort());
    }

    public static void main(String args[]) throws IOException {
        new EchoClient().send();
    }

    public void send() throws IOException {
        SocketAddress socketAddress = new InetSocketAddress(remoteIp, remotePort);
        Scanner in = new Scanner(System.in);
        String info = null;
        DatagramPacket datagramPacket = new DatagramPacket(new byte[512],512,socketAddress);//发送数据的DatagramPacket必须设定数据到达的目的地址
        DatagramPacket inputDatagramPacket = new DatagramPacket(new byte[512],512);//接收数据的DatagramPacket无需指定地址（IP和端口）

        //以下两个while循环结果一样，哪个都可以
        while ((info=in.next())!=null){
            if(info.equals("quit"))
                break;
            datagramPacket.setData(info.getBytes());
            datagramSocket.send(datagramPacket);
            datagramSocket.receive(inputDatagramPacket);
            String msg = new String(inputDatagramPacket.getData(),0,inputDatagramPacket.getLength());
            System.out.println(msg);
        }

//        while (true){
//            info = in.next();
//            if(info.equals("quit"))
//                 break;
//            datagramPacket.setData(info.getBytes());
//            datagramSocket.send(datagramPacket);
//            datagramSocket.receive(inputDatagramPacket);
//            String msg = new String(inputDatagramPacket.getData(),0,inputDatagramPacket.getLength());
//            System.out.println(msg);
//        }

        datagramSocket.close();
    }

}
