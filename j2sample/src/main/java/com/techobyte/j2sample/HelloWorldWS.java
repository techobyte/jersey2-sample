package com.techobyte.j2sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.techobyte.j2sample.model.Hello;

@Path("/helloworld")
public class HelloWorldWS {

	private Hello hello;
	
	public HelloWorldWS() {
		hello = new Hello("World");
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getHWText() {
		return Response.ok(this.hello.toString()).build();
	}
	
	@GET
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Hello getHWJson() {
		return this.hello;
	}
}
