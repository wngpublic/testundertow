package com.wayne;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;

public class ApplicationResteasy extends Application {
	
	public ApplicationResteasy() {
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
