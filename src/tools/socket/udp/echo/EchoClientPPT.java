package tools.socket.udp.echo;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class EchoClientPPT {
    DatagramSocket datagramSocket;
    int remotePort = 8888;//服务器端口
    String remoteIp = "192.168.10.1";//服务器IP

    public EchoClientPPT() throws SocketException {
        datagramSocket = new DatagramSocket();//随机可用端口，又称匿名端口
        System.out.println("客户端端口号："+datagramSocket.getLocalPort());
    }

    public static void main(String args[]) throws IOException {
        new EchoClientPPT().send();
    }

    public void send() throws IOException {
        SocketAddress socketAddress = new InetSocketAddress(remoteIp, remotePort);
        Scanner in = new Scanner(System.in);
        String info = null;
        DatagramPacket outputdDatagramPacket = null;
        DatagramPacket inputdDatagramPacket = new DatagramPacket(new byte[512],512);
        String msg = null;
        while(true){
            info = in.next();
            if(info.equals("quit"))
                break;
            outputdDatagramPacket = new DatagramPacket(info.getBytes(),info.getBytes().length,socketAddress);
            datagramSocket.send(outputdDatagramPacket);
            datagramSocket.receive(inputdDatagramPacket);
            msg = new String(inputdDatagramPacket.getData(),0,inputdDatagramPacket.getLength());
            System.out.println(msg);
        }
        datagramSocket.close();
        }
}
