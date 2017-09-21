package com.wayne;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;

public class MiscClass {

}

//class JerseyServlet implements Servlet {
@SuppressWarnings("serial")
class JerseyServlet extends HttpServlet {
}

class JerseyServletContainer extends org.glassfish.jersey.servlet.ServletContainer {
	public JerseyServletContainer() {
		//super(new ApplicationJerseyResourceConfig());
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse rsp) 
			throws ServletException, IOException {
  	System.out.printf("JerseyServletContainer doGet called\n");
  	rsp.getWriter().write("JerseyServletContainer_servlet_doget\n");
  	rsp.getWriter().flush();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse rsp) 
			throws ServletException, IOException {
  	System.out.printf("JerseyServletContainer doPost called\n");
  	rsp.getWriter().write("JerseyServletContainer_servlet_dopost\n");
  	rsp.getWriter().flush();
	}
}

//@ApplicationPath("path2")
class ApplicationJerseyRestEasy extends Application {
	
	public ApplicationJerseyRestEasy() {
		super();
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new LinkedHashSet<Class<?>>();
		resources.add(ResourceResteasy.class);
		return resources;
	}
	
	@Override
  public Set<Object> getSingletons() {
      return Collections.emptySet();
  }
	
	@Override
  public Map<String, Object> getProperties() {
      return Collections.emptyMap();
  }
}


@ApplicationPath("/apppath")
class ApplicationJerseyResourceConfig extends ResourceConfig {
	public ApplicationJerseyResourceConfig() {
		register(ResourceJersey.class);
	}
}


//class HelloServlet implements Servlet {
@SuppressWarnings("serial")
class HelloServlet extends HttpServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		super.service(req, res);
  	System.out.printf("HelloServlet service called\n");
		//res.getWriter().write("hello_servlet");
		//res.getWriter().flush();
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void destroy() {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse rsp) 
			throws ServletException, IOException {
  	System.out.printf("HelloServlet doGet called\n");
  	rsp.getWriter().write("hello_servlet_doget\n");
  	rsp.getWriter().flush();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse rsp) 
			throws ServletException, IOException {
  	System.out.printf("HelloServlet doPost called\n");
  	rsp.getWriter().write("hello_servlet_dopost\n");
  	rsp.getWriter().flush();
	}
}

//class JerseyServlet implements Servlet {
@SuppressWarnings("serial")
class Servlet2 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse rsp) 
			throws ServletException, IOException {
  	System.out.printf("Servlet2 doGet called\n");
  	rsp.getWriter().write("servlet2_servlet_doget\n");
  	rsp.getWriter().flush();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse rsp) 
			throws ServletException, IOException {
  	System.out.printf("Servlet2 doPost called\n");
  	rsp.getWriter().write("servlet2_servlet_dopost\n");
  	rsp.getWriter().flush();
	}
}



class HttpHandlerUndertow1 implements HttpHandler {

	public void handleRequest(HttpServerExchange exchange) throws Exception {
		
	}
	
}

class Listener1 implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
	}
	
}

class HttpHandlerJersey1 {
	
}
