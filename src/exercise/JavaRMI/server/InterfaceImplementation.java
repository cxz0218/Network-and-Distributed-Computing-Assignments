package exercise.JavaRMI.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.*;

import exercise.JavaRMI.bean.Meeting;
import exercise.JavaRMI.bean.User;
import exercise.JavaRMI.rface.MeetingInterface;

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
public class InterfaceImplementation extends UnicastRemoteObject implements MeetingInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<User> userList;
	private List<Meeting> meetingList;
	private User master;

	/**
	 * You must define a constructor that throws a RemoteException
	 * 
	 * @throws ParseException
	 */
	public InterfaceImplementation() throws RemoteException, ParseException {
		userList = new ArrayList<User>();
		meetingList = new ArrayList<Meeting>();
		User u1 = new User("peter", "123456");
		userList.add(u1);
	}

	/**
	 * Implementation of remote interface method
	 */
	@Override
	public User findUser(String name) throws RemoteException {
		int n = userList.size();
		for (int i = 0; i < n; i++) {
			if (name.equals(userList.get(i).getUserName())) {
				return userList.get(i);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see rface.MeetingInterface#signUp(java.lang.String, java.lang.String)
	 */
	@Override
	public User signUp(String name, String passWord) throws RemoteException {
		int n = userList.size();
		for (int i = 0; i < n; i++) {
			if (name.equals(userList.get(i).getUserName())) {
				return null;
			}
		}
		master = new User(name, passWord);
		userList.add(master);
		return master;
	}

	/* (non-Javadoc)
	 * @see rface.MeetingInterface#add(java.lang.String, java.util.Date, java.util.Date, java.lang.String)
	 */
	@Override
	public int add(String otherusername, Date start, Date end, String title) throws RemoteException {
		int n = userList.size();
		ArrayList<User> tuserList = new ArrayList<User>();
		boolean judge = false;
		String users[] = otherusername.split(",");
		for (int i = 0; i < users.length; i++) {
			judge = false;
			for (int j = 0; j < n; j++) {
				if (users[i].equals(userList.get(j).getUserName())) {
					tuserList.add(userList.get(j));
					judge = true;
					break;
				}
			}
			if (!judge)
				return 2;
		}
		if (!meetingList.isEmpty()) {
			int nm = tuserList.size();
			for (int i = 0; i < nm; i++) {
				for (int j = 0; j < tuserList.get(i).getMeetings().size(); j++) {
					if (tuserList.get(i).getMeetings().get(j).getStartTime().before(start)) {
						if (meetingList.get(i).getEndTime().after(start))
							return 1;
					} else if (meetingList.get(i).getStartTime().after(start)) {
						if (meetingList.get(i).getStartTime().before(end))
							return 1;
					} else if (tuserList.get(i).getMeetings().get(j).getStartTime().equals(start)
							|| (tuserList.get(i).getMeetings().get(j).getEndTime().equals(end)))
						return 1;
				}
			}
		}
		Meeting tmeeting = new Meeting(master, tuserList, start, end, title);
		meetingList.add(tmeeting);
		master.getMeetings().add(tmeeting);
		System.out.println("master.getMeetings().size(): " + master.getMeetings().size());
		for (int i = 0; i < users.length; i++) {
			tuserList.get(i).getMeetings().add(tmeeting);
			System.out.println("tuserList.get(i).getMeetings().size(): " + tuserList.get(i).getMeetings().size());
		}
		return 0;
	}

	@Override
	public String query(Date start, Date end) throws RemoteException {
		String out = "";
		int nm = master.getMeetings().size();
		System.out.println("master.getMeetings().size(): " + master.getMeetings().size());
		List<Meeting> tmeetingList = new ArrayList<Meeting>();
		for (int i = 0; i < nm; i++) {
			if ((master.getMeetings().get(i).getStartTime().after(start)
					|| master.getMeetings().get(i).getStartTime().equals(start))
					&& (master.getMeetings().get(i).getEndTime().before(end)
							|| master.getMeetings().get(i).getEndTime().equals(end))) {
				tmeetingList.add(meetingList.get(i));
			}
		}
		if (tmeetingList.size() == 0)
			return "There are no meetings in this time period";
		Collections.sort(tmeetingList, new Comparator<Meeting>() {
			public int compare(Meeting o1, Meeting o2) {
				return o1.getStartTime().compareTo(o2.getStartTime());
			}
		});
		for (int i = 0; i < tmeetingList.size(); i++) {
			out = out + "Meeting" + i + ": " + tmeetingList.get(i).toString() + "\n";
		}
		return out;
	}

	@Override
	public void delete(int id) throws RemoteException {
		for (int i = 0; i < master.getMeetings().size(); i++) {
			if (master.getMeetings().get(i).getId() == id) {
				Meeting tMeeting = master.getMeetings().get(i);
				meetingList.remove(tMeeting);
				master.getMeetings().remove(tMeeting);
				List<User> users = tMeeting.getUsers();
				for (int j = 0; j < users.size(); j++)
					users.get(j).getMeetings().remove(tMeeting);
				return;
			}
		}
	}

	@Override
	public void clear() throws RemoteException {
		for (int i = 0; i < master.getMeetings().size(); i++) {
			meetingList.remove(master.getMeetings().get(i));
			for (int j = 0; j < master.getMeetings().get(i).getUsers().size(); j++) {
				master.getMeetings().get(i).getUsers().get(j).getMeetings().remove(master.getMeetings().get(i));
			}
		}
		master.getMeetings().clear();
	}

}
