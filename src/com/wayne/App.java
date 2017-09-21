package com.wayne;

/*
 * 
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import io.undertow.server.HttpServerExchange;
import io.undertow.servlet.Servlets;
import static io.undertow.servlet.Servlets.servlet;
import org.glassfish.jersey.server.ResourceConfig;

 */

//import org.jboss.resteasy.spi.ResteasyDeployment;
//import io.undertow.servlet.Servlets;
//import org.glassfish.jersey.servlet.ServletContainer;
//import io.undertow.servlet.api.ServletContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.spi.ResteasyDeployment;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletInfo;
import io.undertow.servlet.core.DeploymentManagerImpl;

/**
 * Hello world!
 *
 */
public class App 
{
	Undertow undertow = null;
	UndertowJaxrsServer undertowJaxrsServer = null;
	boolean started = false;
	
	public App() {
		//initJersey();
		initResteasy();
		//init1();
		//initServletGeneric();
		//initResteasyUndertowJaxrsServer();
	}
	public void initJersey() {
		try {
			int port = 8081;
			String host = "localhost";
			
			ResourceConfigJersey resourceConfigJersey = new ResourceConfigJersey();
			
			org.glassfish.jersey.servlet.ServletContainer servletContainerJersey = 
				new org.glassfish.jersey.servlet.ServletContainer(resourceConfigJersey);
			
		    //ServletInfo servletInfoJersey = 
			//new ServletInfo("servletjersey", 
			// org.glassfish.jersey.servlet.ServletContainer.class);
		    ServletInfo servletInfoJersey = 
		    		new ServletInfo("servletjersey", 
		    		//org.glassfish.jersey.servlet.ServletContainer.class);
		    		servletContainerJersey.getClass());
		    servletInfoJersey.setAsyncSupported(true);
		    servletInfoJersey.setLoadOnStartup(1);
		    servletInfoJersey.addMapping("/");
		    servletInfoJersey.addInitParam(ServletProperties.JAXRS_APPLICATION_CLASS, 
					ResourceConfigJersey.class.getName());
		    
			DeploymentInfo deploymentInfo = new DeploymentInfo();
			deploymentInfo.setClassLoader(this.getClass().getClassLoader());
			deploymentInfo.setContextPath("/context");
			deploymentInfo.setDeploymentName("undertowapp.war");
			deploymentInfo.addServlet(servletInfoJersey);
			deploymentInfo.addServletContextAttribute(resourceConfigJersey.getClass().getName(), resourceConfigJersey);

			//DeploymentManager deploymentManager = 
			//		new DeploymentManagerImpl(deploymentInfo, servletContainerJersey);
			io.undertow.servlet.api.ServletContainer servletContainerUndertow = 
					Servlets.defaultContainer();
					//io.undertow.servlet.api.ServletContainer.Factory.newInstance();

			DeploymentManager deploymentManager = 
					servletContainerUndertow.addDeployment(deploymentInfo);

			deploymentManager.deploy();
			
			HttpHandler httpHandler = deploymentManager.start();
			PathHandler pathHandler = Handlers.path(Handlers.redirect("/"));
			pathHandler.addPrefixPath("/", httpHandler);
			
			Undertow.Builder builder = Undertow.builder().addHttpListener(port, host);
			builder.setHandler(pathHandler);
			this.undertow = builder.build();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void initResteasy() {
		// do not use UndertowJaxrsServer, just reuse the code. this also works.
		// curl -v http://localhost:8081/context/path2/get1?p0=abc&p1=bcd -H "Content-Type: application/json"
		// curl -v http://localhost:8081/context/path2/post1 -H "Content-Type: application/json" \
		//     -H "Accept:application/json" -d '{ "hello":"data" }\r\n'
		try {
			int port = 8081;
			String host = "localhost";
			

			ResteasyDeployment resteasyDeployment = new ResteasyDeployment();
			resteasyDeployment.setApplicationClass(ApplicationResteasy.class.getName());

		    ServletInfo servletInfoResteasy = new ServletInfo("resteasyservlet", HttpServlet30Dispatcher.class);
		    servletInfoResteasy.setAsyncSupported(true);
		    servletInfoResteasy.setLoadOnStartup(1);
		    servletInfoResteasy.addMapping("/");
			
			DeploymentInfo deploymentInfo = new DeploymentInfo();
			deploymentInfo.setClassLoader(this.getClass().getClassLoader());
			deploymentInfo.setContextPath("/context");
			deploymentInfo.setDeploymentName("undertowapp.war");
			deploymentInfo.addServlet(servletInfoResteasy);
			deploymentInfo.addServletContextAttribute(ResteasyDeployment.class.getName(), resteasyDeployment);
			
			io.undertow.servlet.api.ServletContainer servletContainer = io.undertow.servlet.api.ServletContainer.Factory.newInstance();

			DeploymentManager deploymentManager = servletContainer.addDeployment(deploymentInfo);
			deploymentManager.deploy();
			
			HttpHandler httpHandler = deploymentManager.start();
			
			PathHandler pathHandler = Handlers.path();
			pathHandler.addPrefixPath("/", httpHandler);
			
			Undertow.Builder builder = Undertow.builder().addHttpListener(port, host);
			builder.setHandler(pathHandler);
			this.undertow = builder.build();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void initResteasyUndertowJaxrsServer() {
		// init2 works
		try {
			int port = 8081;
			String host = "localhost";
			
			undertowJaxrsServer = new UndertowJaxrsServer();

			ResteasyDeployment resteasyDeployment = new ResteasyDeployment();
			resteasyDeployment.setApplicationClass(ApplicationResteasy.class.getName());
			
			DeploymentInfo deploymentInfo = undertowJaxrsServer
					.undertowDeployment(resteasyDeployment, "/");
			
			deploymentInfo.setClassLoader(this.getClass().getClassLoader());
			deploymentInfo.setContextPath("/context");
			deploymentInfo.setDeploymentName("undertowapp.war");

			undertowJaxrsServer.deploy(deploymentInfo);

			Undertow.Builder builder = Undertow.builder().addHttpListener(port, host);

			undertowJaxrsServer.start(builder);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void init4() {
		try {
			int port = 8081;
			String host = "localhost";
			
			undertowJaxrsServer = new UndertowJaxrsServer();

			List<ServletInfo> listServlets = new ArrayList<>();
			
			//ServletInfo resteasyServlet = new ServletInfo("ResteasyServlet", 
			//org.glassfish.jersey.servlet.ServletContainer.class); // Servlet2.class);
			ServletInfo resteasyServlet = new ServletInfo("ResteasyServlet", 
					org.glassfish.jersey.servlet.ServletContainer.class); // Servlet2.class);
			resteasyServlet.setAsyncSupported(true);
			resteasyServlet.setLoadOnStartup(1);
			//resteasyServlet.addMapping("/path2");

			listServlets.add(resteasyServlet);

			ResteasyDeployment resteasyDeployment = new ResteasyDeployment();
			resteasyDeployment.setApplicationClass(ApplicationResteasy.class.getName());
			//resteasyDeployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");
			
			DeploymentInfo deploymentInfo = undertowJaxrsServer
					.undertowDeployment(resteasyDeployment, "/");
					//.undertowDeployment(resteasyDeployment, "/path2/*");
			
			deploymentInfo.setClassLoader(this.getClass().getClassLoader());
			deploymentInfo.setContextPath("/context");
			deploymentInfo.setDeploymentName("undertowapp.war");
			//deploymentInfo.addServlets(listServlets);
			//deploymentInfo.addListener(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

			undertowJaxrsServer.deploy(deploymentInfo);

			Undertow.Builder builder = Undertow.builder().addHttpListener(port, host);

			undertowJaxrsServer.start(builder);
		} catch(Exception e) {
			e.printStackTrace();
		}	

	}
	public void init3() {
		try {
			int port = 8080; // 00;
			String host = "localhost";
			
			ServletInfo servletInfo1 = new ServletInfo("Servlet1", HelloServlet.class);
			servletInfo1.addMapping("/servlet1");
			
			ServletInfo servletInfo2 = new ServletInfo("Servlet2", Servlet2.class);
			servletInfo2.setLoadOnStartup(1);
			servletInfo2.setAsyncSupported(true);
			servletInfo2.addMapping("/servlet2");


			ServletInfo servletInfo3 = new ServletInfo("Servlet3", JerseyServlet.class);
			//ServletInfo servletInfo3 = new ServletInfo("Servlet3", JerseyServletContainer.class);
			servletInfo3.setLoadOnStartup(1);
			servletInfo3.setAsyncSupported(true);
			//servletInfo3.addInitParam(ServletProperties.JAXRS_APPLICATION_CLASS, ApplicationJersey.class.getName());
			servletInfo3.addInitParam(ServletProperties.JAXRS_APPLICATION_CLASS, ApplicationJerseyResourceConfig.class.getName());
			//servletInfo3.addInitParam("javax.ws.rs.core.Application", ApplicationJersey.class.getName());
			servletInfo3.addMapping("/jersey");
			
			ResteasyDeployment resteasyDeployment = new ResteasyDeployment();
			resteasyDeployment.setApplicationClass(ApplicationJerseyRestEasy.class.getName());
			undertowJaxrsServer = new UndertowJaxrsServer();
			undertowJaxrsServer.undertowDeployment(resteasyDeployment, "/resteasydeploy");
			ServletInfo resteasyServlet = new ServletInfo("ResteasyServlet", HttpServlet30Dispatcher.class);
			resteasyServlet.addInitParam(ServletProperties.JAXRS_APPLICATION_CLASS, ApplicationJerseyRestEasy.class.getName());
			resteasyServlet.setAsyncSupported(true);
			resteasyServlet.setLoadOnStartup(1);
			resteasyServlet.addMapping("/resteasy");

			
			List<ServletInfo> listServlets = new ArrayList<>();
			listServlets.add(servletInfo1);
			listServlets.add(servletInfo2);
			listServlets.add(servletInfo3);
			//listServlets.add(resteasyServlet);
			
			DeploymentInfo deploymentInfo = new DeploymentInfo();
			
			deploymentInfo.setClassLoader(this.getClass().getClassLoader());
			deploymentInfo.setContextPath("/undertowapp");
			deploymentInfo.setDeploymentName("undertowapp.war");
			//deploymentInfo.addServletContextAttribute(ResteasyDeployment.class.getName(), resteasyDeployment);
			deploymentInfo.addServlets(listServlets);
						
			io.undertow.servlet.api.ServletContainer servletContainer = io.undertow.servlet.api.ServletContainer.Factory.newInstance();
			
			DeploymentManager deploymentManager = servletContainer.addDeployment(deploymentInfo);
			
			deploymentManager.deploy();
			
			HttpHandler httpHandler = deploymentManager.start();
			
			PathHandler pathHandler = Handlers.path().addPrefixPath("/path1", httpHandler);

			Undertow.Builder builder = Undertow.builder();
			builder.addHttpListener(port, host);
			builder.setHandler(pathHandler);

			this.undertow = builder.build();
			undertowJaxrsServer.deploy(deploymentInfo);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void initServletGeneric() {
		// this works for servlet1 and servlet2
		try {
			int port = 8081; // 00;
			String host = "localhost";
			
			ServletInfo servletInfo1 = new ServletInfo("Servlet1", HelloServlet.class);
			servletInfo1.addMapping("/servlet1");
			
			ServletInfo servletInfo2 = new ServletInfo("Servlet2", Servlet2.class);
			servletInfo2.setLoadOnStartup(1);
			servletInfo2.setAsyncSupported(true);
			servletInfo2.addMapping("/servlet2");


			ServletInfo servletInfo3 = new ServletInfo("Servlet3", JerseyServlet.class);
			//ServletInfo servletInfo3 = new ServletInfo("Servlet3", JerseyServletContainer.class);
			servletInfo3.setLoadOnStartup(1);
			servletInfo3.setAsyncSupported(true);
			//servletInfo3.addInitParam(ServletProperties.JAXRS_APPLICATION_CLASS, ApplicationJersey.class.getName());
			servletInfo3.addInitParam(ServletProperties.JAXRS_APPLICATION_CLASS, ApplicationJerseyResourceConfig.class.getName());
			//servletInfo3.addInitParam("javax.ws.rs.core.Application", ApplicationJersey.class.getName());
			servletInfo3.addMapping("/jersey");
			
			List<ServletInfo> listServlets = new ArrayList<>();
			listServlets.add(servletInfo1);
			listServlets.add(servletInfo2);
			listServlets.add(servletInfo3);
			
			DeploymentInfo deploymentInfo = new DeploymentInfo();
			
			deploymentInfo.setClassLoader(this.getClass().getClassLoader());
			deploymentInfo.setContextPath("/undertowapp");
			deploymentInfo.setDeploymentName("undertowapp.war");
			deploymentInfo.addServlets(listServlets);
						
			io.undertow.servlet.api.ServletContainer servletContainer = io.undertow.servlet.api.ServletContainer.Factory.newInstance();
			
			DeploymentManager deploymentManager = servletContainer.addDeployment(deploymentInfo);
			
			deploymentManager.deploy();
			
			HttpHandler httpHandler = deploymentManager.start();
			
			PathHandler pathHandler = Handlers.path().addPrefixPath("/path1", httpHandler);

			Undertow.Builder builder = Undertow.builder();
			builder.addHttpListener(port, host);
			builder.setHandler(pathHandler);

			this.undertow = builder.build();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}		
	public void start() {
		if(!started) {
			System.out.printf("start called and !started\n");
			started = true;
			if(undertowJaxrsServer != null) {
				//undertowJaxrsServer.start();
			}
		    if(undertow != null) {
				undertow.start();
			}
		}
	}
	
	public static void p(String f, Object ... o) {
		System.out.printf(f, o);
	}
	
    public static void main( String[] args )
    {
    	p("Starting main App\n");
    	App app = new App();
    	app.start();
    }
}






