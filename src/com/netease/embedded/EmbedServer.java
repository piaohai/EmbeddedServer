package com.netease.embedded;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.netease.embedded.impl.TomcatServer;

public abstract class EmbedServer implements Runnable {

	protected String projectHome;

	protected String docBase;

	protected String webcontext;

	protected int port = 8080;

	protected static boolean started = false;

	public static boolean isStarted() {
		return started;
	}

	public EmbedServer() {
		initConf();
	}

	protected void initConf() {
		Properties properties = new Properties();
		try {
			properties.load(TomcatServer.class.getResourceAsStream("/server.properties"));
			docBase = properties.getProperty("docBase");
			webcontext = properties.getProperty("webcontext");
			System.out.println(" project location = " + docBase);
			try {
				port = Integer.parseInt(properties.getProperty("port"));
			} catch (Exception ex) {
				port = 8080;
			}
			File f = new File(".");
			projectHome = f.getAbsolutePath();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void run() {
		startup();
	}

	public abstract void startup();

}
