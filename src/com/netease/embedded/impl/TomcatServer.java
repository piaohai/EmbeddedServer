package com.netease.embedded.impl;

import java.net.InetAddress;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Embedded;

import com.netease.embedded.EmbedServer;

public class TomcatServer extends EmbedServer {

	private static Context context = null;

	public static Context getContext() {
		return context;
	}

	private Embedded tomcat;

	private String catalinaHome = "tomcat6";

	private void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					tomcat.stop();
				} catch (LifecycleException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		System.out.println("==============系统开始启动==============");
		tomcat = new Embedded();
		tomcat.setCatalinaHome(catalinaHome);
		Engine engine = tomcat.createEngine();
		Host host = tomcat.createHost("localhost", projectHome);
		Context context = tomcat.createContext(webcontext, docBase);

		context.setReloadable(false);
		//
		// NamingResources name = new NamingResources();
		// ContextResource resource = new ContextResource();
		// resource.setName("jdbc/netest");
		// resource.setAuth("Container");
		// resource.setType("javax.sql.DataSource");
		// resource.setProperty("factory",
		// "org.apache.commons.dbcp.BasicDataSourceFactory");
		// resource.setProperty("maxActive", "20");
		// resource.setProperty("maxIdle", "10");
		// resource.setProperty("maxWait", "100000");
		// resource.setProperty("username", "");
		// resource.setProperty("password", "");
		// resource.setProperty("driverClassName",
		// "oracle.jdbc.driver.OracleDriver");
		// resource.setProperty("url",
		// "jdbc:oracle:thin:@192.168.131.100:1521:epms");
		//
		// name.addResource(resource);
		// System.out.println(resource.getName());
		// System.out.println(resource.getAuth());
		// System.out.println(resource.getType());
		// System.out.println(resource.getProperty("url"));
		// context.setNamingResources(name);

		host.addChild(context);
		engine.addChild(host);
		engine.setDefaultHost("localhost");
		tomcat.addEngine(engine);

		try {
			Connector c = tomcat.createConnector((InetAddress) null, port, false);
			c.setProtocol("AJP/1.3");
			c.setRedirectPort(8443);
			tomcat.addConnector(c);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		tomcat.addConnector(tomcat.createConnector((InetAddress) null, port, false));
		registerShutdownHook();
		try {
			TomcatServer.context = context;
			tomcat.start();
			Thread.sleep(Long.MAX_VALUE);
		} catch (Exception e) {
			try {
				tomcat.stop();
			} catch (LifecycleException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}

	}

}
