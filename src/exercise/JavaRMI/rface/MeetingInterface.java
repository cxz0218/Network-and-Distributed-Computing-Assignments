package exercise.JavaRMI.rface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

import exercise.JavaRMI.bean.User;

/**
 * Creating a distributed agenda sharing service using Java RMI. This shared
 * agenda service can be used by different users to query, add, and delete
 * meetings. The server supports functions such as registration and clearing of
 * meetings. the agenda sharing service includes the following functions: User
 * Registration, adding meetings, querying meetings, deleting meetings, clearing
 * meetings;
 * 
 * @author 陈心琢-2018303015
 *
 */
public interface MeetingInterface extends Remote {// 定义一个接口并继承了Remote接口，必须

	/**
	 * 远程接口方法必须抛出 java.rmi.RemoteException //网络的异常
	 */

	/**
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	public User findUser(String name) throws RemoteException;

	/**
	 * New User registration requires filling in the user name and Password. If the
	 * user name already exists, the registration fails and the prompt is printed.
	 * Otherwise, the registration is successful and the prompt is printed.
	 * 
	 * @param name
	 * @param password
	 * @return
	 * @throws RemoteException
	 */
	public User signUp(String name, String password) throws RemoteException;

	/**
	 * Registered users can add meetings. The meeting must involve two registered
	 * users, and a meeting involving only a single user can not be created.
	 * Information about the meeting includes starting time, ending time, meeting
	 * title, and meeting participants. When a meeting is added, it must appear on
	 * the agenda of the user who created the meeting and the user who attended the
	 * meeting. If a user’s new meeting overlaps with an existing meeting, the
	 * meeting can not be created. Finally, the user is prompted to add a result to
	 * the meeting.
	 * 
	 * @param otherusername
	 * @param start
	 * @param end
	 * @param title
	 * @return
	 * @throws RemoteException
	 */
	public int add(String otherusername, Date start, Date end, String title) throws RemoteException;

	/**
	 * Registered users look up all eligible minutes in the agenda by giving a
	 * specific time interval (given start and end times) . The list of results
	 * returned is sorted by time. In the list, include information such as start
	 * time, end time, meeting title, meeting participants, and so on. Use The
	 * following command for the session query:
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws RemoteException
	 */
	public String query(Date start, Date end) throws RemoteException;

	/**
	 * A registered user can delete a meeting created by that user based on the
	 * meeting information.
	 * 
	 * @param id
	 * @throws RemoteException
	 */
	public void delete(int id) throws RemoteException;

	/**
	 * A registered user can clear all meetings created by that user
	 * 
	 * @throws RemoteException
	 */
	public void clear() throws RemoteException;
}
