package com.techobyte.j2sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/helloworld")
public class HelloWorldWS {

	private String helloWorld = "Hello World!!!";
	
	public HelloWorldWS() {
		helloWorld = "Hi! Hello World!!!";
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getHWText() {
		return Response.ok(this.helloWorld).build();
	}
}
