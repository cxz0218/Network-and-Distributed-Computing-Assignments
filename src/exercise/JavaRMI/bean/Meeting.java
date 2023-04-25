package exercise.JavaRMI.bean;

import java.io.Serializable;
import java.util.*;

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
public class Meeting implements Serializable {
	private int meetingId;
	private User founder;
	private List<User> arrayList;
	private Date startTime;
	private Date endTime;
	private String title;
	private static int nid = 0;

	public Meeting(User f, List<User> a, Date s, Date e, String t) {
		super();
		this.meetingId = nid++;
		this.founder = f;
		this.arrayList = a;
		this.startTime = s;
		this.endTime = e;
		this.title = t;
	}

	public List<User> getUsers() {
		return this.arrayList;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public int getId() {
		return this.meetingId;
	}

	@Override
	public String toString() {
		return "Meeting [meetingId=" + meetingId + ", founder=" + founder + ", arrayList=" + arrayList + ", startTime="
				+ startTime + ", endTime=" + endTime + ", title=" + title + "]";
	}

}
