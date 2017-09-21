package com.wayne;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class ResourceConfigJersey extends ResourceConfig {
	public ResourceConfigJersey() {
        register(MultiPartFeature.class);
        register(ResourceJersey.class);
	}
}
