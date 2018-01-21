package com.abhishekjain.hazelcast;

import java.io.Serializable;

/**
 * 
 * A simple user class.
 * 
 * @author abhishek
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String username;
	private final boolean isPersonAwesome = getAwesomeness();
	
	public User(String username) {
		super();
		this.username = username;
	}

	private boolean getAwesomeness() {
		return Math.random() < 0.5;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		return "#{" + username +"}";
	}
}
