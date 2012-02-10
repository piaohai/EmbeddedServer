package com.netease.embedded.impl;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.webapp.WebAppContext;

import com.netease.embedded.EmbedServer;

public class JettyServer extends EmbedServer {

	@Override
	public void startup() {
		// TODO Auto-generated method stub
		Server server = new Server(port);
		Context root = new Context(server, webcontext, Context.SESSIONS);
		Connector connector = new SelectChannelConnector();
		server.addConnector(connector);

		WebAppContext wac = new WebAppContext();
		wac.setContextPath(webcontext);
		wac.setWar(docBase);
		server.setHandler(wac);
		server.setStopAtShutdown(true);
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
