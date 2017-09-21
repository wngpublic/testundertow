package com.wayne;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.core.Application;


public class ApplicationJersey extends Application {
	public ApplicationJersey() {
		super();
		//register(ResourceJersey.class);
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		System.out.printf("ApplicationJersey getClasses\n");
		Set<Class<?>> resources = new LinkedHashSet<Class<?>>();
		resources.add(ResourceJersey.class);
		return resources;
	}
	
	@Override
	public Set<Object> getSingletons() {
		System.out.printf("ApplicationJersey getSingletons\n");
		return null;
	}
}
