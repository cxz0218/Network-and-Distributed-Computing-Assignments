package exercise.JavaRMI.client;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.*;
import java.util.*;

import exercise.JavaRMI.bean.User;
import exercise.JavaRMI.rface.MeetingInterface;

/**
 * This is the PMI client, which implements operations on objects on a remote
 * server, using Java Rmi to create a distributed agenda sharing service. This
 * shared agenda service can be used by different users to query, add, and
 * delete meetings. The server supports functions such as registration and
 * clearing of meetings
 * 
 * @author 陈心琢-2018303015
 *
 */
public class MeetingClient {

	static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	static PrintWriter screen = new PrintWriter(System.out, true);
	static User master;

	private static void displayMenu() {
		System.out.println("Time format : yyyy-MM-dd-HH:mm");
		screen.println("RMI Menu:");
		screen.println("\t 1.add");
		screen.println("\t\t arguments:<username><start><end><title>");
		screen.println("\t 2.delete");
		screen.println("\t\t arguments:<meetingid>");
		screen.println("\t 3.clear");
		screen.println("\t\t arguments: no args");
		screen.println("\t 4.query");
		screen.println("\t\t arguments:<start><end>");
		screen.println("\t 5.help");
		screen.println("\t\t arguments: no args");
		screen.println("\t 5.quit");
		screen.println("\t\t arguments: no args");
		screen.println("Input an operation:");
	}

	/**
	 * Use Java Rmi to create a distributed agenda sharing service. This shared
	 * agenda service can be used by different users to query, add, and delete
	 * meetings. The server supports functions such as registration and clearing of
	 * meetings
	 * 
	 * @param argv
	 * @throws NotBoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] argv) throws NotBoundException, IOException, ParseException {

		// if (argv.length != 5) {
		// System.err.println("Invalid input!");
		// System.exit(0);
		// }
		screen.println("Please register first!");
		String r[] = keyboard.readLine().split(" ");
		String name = r[0];
		String password = r[1];
		// Remote is returned by default, which is why you must inherit the Remote when
		// defining the interface,
		// and then force type conversion, noting that the result is a Remote object
		// reference
		MeetingInterface mi = (MeetingInterface) Naming.lookup("MI");
		// Calling a remote method
		while (true) {
			master = mi.signUp(name, password);
			if (master != null) {
				screen.println("Registration successful");
				break;
			} else
				screen.println("Failed to register, username already exists, please register again.");
			String reg = keyboard.readLine();
			name = reg.split(" ")[0];
			password = reg.split(" ")[1];
		}
		displayMenu();
		String reg = keyboard.readLine();
		String cmd[] = reg.split(" ");
		while (!cmd[0].equals("quit")) {
			switch (cmd[0]) {
			case "add": {
				if (cmd.length != 7) {
					System.err.println("Invalid input!");
					break;
				} else {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date start = format.parse(cmd[2] + " " + cmd[3]);
					Date end = format.parse(cmd[4] + " " + cmd[5]);
					int j = mi.add(cmd[1], start, end, cmd[6]);
					if (j == 0)
						screen.println("Add success!");
					else if (j == 1)
						System.err.println("Time conflict, add meeting failure!");
					else if (j == 2)
						System.err.println("The user does not exist and the add meeting failed!");
					break;
				}
			}
			case "query": {
				if (cmd.length != 5) {
					System.err.println("Invalid input!");
					break;
				} else {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date start = format.parse(cmd[1] + " " + cmd[2]);
					Date end = format.parse(cmd[3] + " " + cmd[4]);
					screen.println(mi.query(start, end));
					break;
				}
			}
			case "delete": {
				if (cmd.length != 2) {
					System.err.println("Invalid input!");
					break;
				} else {
					int id = Integer.parseInt(cmd[1]);
					mi.delete(id);
					break;
				}
			}
			case "clear": {
				if (cmd.length != 1) {
					System.err.println("Invalid input!");
					break;
				} else {
					mi.clear();
					break;
				}
			}
			case "quit": {
				System.exit(0);
			}
			}
			screen.println("Input an operation:");
			reg = keyboard.readLine();
			cmd = reg.split(" ");
		}

	}
}
