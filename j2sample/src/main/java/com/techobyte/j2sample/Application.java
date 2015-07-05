package com.techobyte.j2sample;

import org.glassfish.jersey.server.ResourceConfig;

public class Application extends ResourceConfig {

	public Application() {
		packages(this.getClass().getPackage().getName());
	}

}
