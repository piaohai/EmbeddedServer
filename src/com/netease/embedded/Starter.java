package com.netease.embedded;

import com.netease.embedded.impl.TomcatServer;

public class Starter {

	public static void main(String[] args) {
		try {
			Thread t = new Thread(new TomcatServer());
			// Thread t = new Thread(new JettyServer());
			t.start();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
