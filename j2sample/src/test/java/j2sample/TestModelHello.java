package j2sample;

import static org.junit.Assert.*;
import org.junit.Test;

import com.techobyte.j2sample.model.Hello;


public class TestModelHello {

	public TestModelHello() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void TestHelloDefault() {
		Hello hello = new Hello();
		assertEquals(hello.getMessage(), "Hello ");
	}
	
	@Test
	public void TestHelloWithParam() {
		Hello hello = new Hello("World");
		assertEquals(hello.getMessage(), "Hello World");
	}
}
