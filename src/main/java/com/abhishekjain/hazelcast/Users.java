package com.abhishekjain.hazelcast;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * This represents the user database, accessed by the application.
 * 
 * @author abhishek
 * 
 */
public class Users {

	/** The users in the database */
	private final User[] users = {
			new User("abhishek"),
			new User("ashish"),
			new User("rahul"),
			new User("utsav"),
			new User("shubham"),
			new User("tanvi"),
			new User("akansha"),
			new User("akshat"),
			new User("charu"),
			new User("nonu"),
			new User("nikku"),
			new User("srishti")
	};

	private final Map<String, User> userMap;

	public Users() {

		userMap = new HashMap<String, User>();

		for (User user : users) {
			userMap.put(user.getUsername(), user);
		}
	}

	/**
	 * The number of users in the database
	 */
	public int size() {
		return userMap.size();
	}

	/**
	 * Given a number, return the user
	 */
	public User get(int index) {
		return users[index];
	}

	/**
	 * Given the user's name return the User details
	 */
	public User get(String username) {
		return userMap.get(username);
	}

	/**
	 * Return the user names.
	 */
	public Set<String> getUserNames() {
		return userMap.keySet();
	}
}
