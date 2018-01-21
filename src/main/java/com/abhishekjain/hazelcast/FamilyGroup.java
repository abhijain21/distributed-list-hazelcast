package com.abhishekjain.hazelcast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author abhishek
 *
 */
public class FamilyGroup {

	private final List<User> loggedInUsers;
	private final Users userDB = new Users();
	private final SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss-SS");

	private long lastChange;
	private HazelcastInstance instance;

	public FamilyGroup() {

		instance = Hazelcast.newHazelcastInstance();
		loggedInUsers = instance.getList("Users");
		System.out.println("FamilyGroup size on instance startup: " + loggedInUsers.size());
	}

	/**
	 * A user joins the group
	 * 
	 * @param username
	 */
	public void join(String username) {
		System.out.println("current size before joining: " + loggedInUsers.size());

		User user = userDB.get(username);
		loggedInUsers.add(user);
		lastChange = System.currentTimeMillis();
	}

	/**
	 * The user leaves group
	 */
	public void leave(String username) {
		System.out.println("current size before leaving: " + loggedInUsers.size());

		User user = userDB.get(username);
		int before = loggedInUsers.size();
		loggedInUsers.remove(user);
		if (loggedInUsers.size() == before) {
			System.out.println("Unable to remove user: " + user.toString());
		}
		lastChange = System.currentTimeMillis();
	}

	/**
	 * @return Return true if the user is present in the group
	 */
	public boolean isLoggedOn(String username) {
		for (User u : loggedInUsers) {
			if (u.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return a list of the currently logged on users.
	 */
	public Collection<User> loggedOnUsers() {
		return loggedInUsers;
	}

	/**
	 * Display the members in the group
	 */
	public void displayUsers() {

		StringBuilder sb = new StringBuilder("Members present:\n");
		Collection<User> users = loggedInUsers;
		for (User user : users) {
			sb.append(user);
			sb.append("\n");
		}
		sb.append(loggedInUsers.size());
		sb.append(" -- ");
		sb.append(sdf.format(new Date(lastChange)));
		sb.append("\n");
		System.out.println(sb.toString());
	}

}
