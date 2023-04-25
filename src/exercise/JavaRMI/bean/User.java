package exercise.JavaRMI.bean;

import java.io.Serializable;
import java.util.ArrayList;

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
public class User implements Serializable {
	private String userName;
	private String passWord;
	private ArrayList<Meeting> userMeetings;

	public String getUserName() {
		return this.userName;
	}

	public String getPassword() {
		return this.passWord;
	}

	public User(String iuserName, String ipassWord) {
		super();
		this.passWord = ipassWord;
		this.userName = iuserName;
		userMeetings = new ArrayList<Meeting>();
	}

	public ArrayList<Meeting> getMeetings() {
		return this.userMeetings;
	}
}
