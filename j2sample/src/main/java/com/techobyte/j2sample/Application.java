package com.techobyte.j2sample;

import org.glassfish.jersey.server.ResourceConfig;

public class Application extends ResourceConfig {

	public Application() {
		register(HelloWorldWS.class);
		packages(this.getClass().getPackage().getName(), "com.fasterxml.jackson.jaxrs.json");
	}

}
