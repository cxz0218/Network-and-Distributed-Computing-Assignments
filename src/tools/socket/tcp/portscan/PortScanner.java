package socket.tcp.portscan;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class PortScanner {
    //private static final String HOST = "127.0.0.1";
    //private static final int scope = 1024;

    public static void main(String args[]){
        String host = "127.0.0.1";
        int scope = 1024;
        //new PortScanner().scan(host,scope);
        PortScanner.scan(host,scope);
    }

    public static void scan(String host, int scope) {
        if(scope<=0||scope>65535){
            System.out.println("please choose the scope between 0 and 65535");
            return;//??
        }
        Socket socket = null;
        for(int port =1000 ; port<=65535; port++){
            try {
                socket = new Socket(host, port);
                System.out.println("端口：" + port + ",被占用");
            }catch(UnknownHostException exception){
                System.out.println("主机无法识别！");
            }catch (IOException exception){
                System.out.println("端口："+port+"不能连接！");
            }finally {
                try {
                    if (socket != null)
                        socket.close();
                }catch (IOException exception){
                    exception.printStackTrace();
                }
            }
        }
    }
}
