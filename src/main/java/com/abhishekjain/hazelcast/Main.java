package com.abhishekjain.hazelcast;

import java.util.concurrent.TimeUnit;

public class Main {

	private static String JOIN_ONLY = "join-only";
	private static boolean _true = true;

	public static void main(String[] args) throws InterruptedException {

		BigWideWorld theWorld = new BigWideWorld();
		FamilyGroup family = new FamilyGroup();
		boolean canLeave = args.length == 0 || !args[0].equals(JOIN_ONLY);

		while (_true) {
			String username = theWorld.nextUser();
			if (family.isLoggedOn(username)) {
				if (canLeave)
					family.leave(username);
			} else {
				family.join(username);
			}
			family.displayUsers();
			TimeUnit.SECONDS.sleep(2);
		}
	}
}
