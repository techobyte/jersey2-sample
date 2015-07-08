package com.techobyte.j2sample;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.techobyte.j2sample.dao.UserDAO;
import com.techobyte.j2sample.model.Hello;
import com.techobyte.j2sample.model.User;

@Path("/helloworld")
public class HelloWorldWS {
	final static Logger logger = LogManager.getLogger(HelloWorldWS.class);

	private Hello hello;
	
	public HelloWorldWS() {
		hello = new Hello("World");
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getHWText() {
		logger.info("TEXT: "+this.hello.toString());
		return Response.ok(this.hello.toString()).build();
	}
	
	@GET
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Hello getHWJson() {
		logger.info("JSON: "+this.hello.toString());
		return this.hello;
	}
	
	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		UserDAO daoUser = new UserDAO();
		return daoUser.selectAll();
	}
}
