package exercise.JavaRMI.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import exercise.JavaRMI.rface.MeetingInterface;

/**
 * Creating a distributed agenda sharing service using Java RMI. This shared
 * agenda service can be used by different users to query, add, and delete
 * meetings. The server supports functions such as registration and clearing of
 * meetings
 * 
 * @author 陈心琢-2018303015
 *
 */
public class MeetingServer {

	/**
	 * Start the RMI registration service and register objects
	 */
	public static void main(String[] args) {
		try {
			// 启动RMI注册服务，指定端口为1099 （1099为默认端口）
			LocateRegistry.createRegistry(1099);
			// 创建远程对象的一个或多个实例，下面是mi对象
			// 可以用不同名字注册不同的实例
			MeetingInterface mi = new InterfaceImplementation();
			Naming.rebind("MI", mi);
			System.out.println("MeetingServer is ready.");
		} catch (Exception e) {
			System.out.println("MeetingServer failed: " + e);
		}
	}

}
