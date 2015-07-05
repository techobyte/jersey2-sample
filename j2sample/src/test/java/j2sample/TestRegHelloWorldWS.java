package j2sample;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TestRegHelloWorldWS extends JerseyTest {

	public TestRegHelloWorldWS() {
	}

	@Override
	protected Application configure() {
		Application app = new ResourceConfig(com.techobyte.j2sample.HelloWorldWS.class);
		return app;
	}

	@Test
	public void test_GET_helloworld() throws Exception {
		String actual = target("helloworld").request().get(String.class);
		//System.out.println(actual);
		assertThat(actual, is("Hello [message=Hello World]"));
	}
}
