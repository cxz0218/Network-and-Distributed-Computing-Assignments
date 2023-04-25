package socket.udp.echo;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class EchoServer {
    DatagramSocket datagramSocket;
    int port = 8888;

    public EchoServer() throws SocketException, UnknownHostException {
        datagramSocket = new DatagramSocket(port);
        System.out.println("服务器启动，IP："+ InetAddress.getLocalHost());
        System.out.println("服务器启动，IP："+ datagramSocket.getLocalAddress());
    }

    public static void main(String[] args) throws IOException {
        new EchoServer().service();
    }

    public void service() throws IOException {
        while (true) {
            DatagramPacket datagramPacket = new DatagramPacket(new byte[512],512);//作为接收数据的DatagramPacket无需指定地址（IP和端口）
            datagramSocket.receive(datagramPacket);//接收客户端信息
            String msg = new String(datagramPacket.getData(),0,datagramPacket.getLength());
            System.out.println(datagramPacket.getAddress()+":"+datagramPacket.getPort()+">"+msg);//获取客户端信息
            datagramPacket.setData(("you said:"+msg).getBytes());
            datagramSocket.send(datagramPacket);//回复数据
        }

    }

}
