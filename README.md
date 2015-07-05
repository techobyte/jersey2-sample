# Tutorial Jersey2 (Servlet 3.0) boiler plate code

# Maven
### Pre-requisites
* Java JDK 7
* Maven 3
* Eclipse Luna
* Tomcat 7
* Jersey 2

### Create a maven application project
* using Command line:
```sh
mvn archetype:generate -DgroupId=com.techobyte -DartifactId=j2sample -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false
```
  IDE specific project file generation. Eclipse: ```mvn eclipse:eclipse``` IntelliJ: ```mvn idea:idea```
* using IDE: Eclipse/IntelliJ/Netbeans
  * New Maven Project
  * Archetype: maven-archetype-webapp
  * GroupId: com.techobyte
  * ArtifactId: j2sample

### Update Web Descriptor File (web.xml)
```xml
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
   http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
   version="3.0">
   <display-name>Servlet 3.0 Web Application</display-name>
</web-app>
```

### Add Dependency (pom.xml)
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
   <modelVersion>4.0.0</modelVersion>
   <groupId>com.techobyte</groupId>
   <artifactId>j2sample</artifactId>
   <packaging>war</packaging>
   <version>0.0.1</version>
   <name>j2sample Maven Webapp</name>
   <url>http://maven.apache.org</url>
   <dependencies>
      <dependency>
         <groupId>org.glassfish.jersey.containers</groupId>
         <artifactId>jersey-container-servlet</artifactId>
         <version>2.19</version>
      </dependency>
   </dependencies>
   <build>
      <finalName>j2sample</finalName>
   </build>
</project>
```

### Create java package
* Make directory @ src/main/java
* Add java package "com.techobyte.j2sample" under src/main/java

### Add Application class under package
*./src/main/java/com/techobyte/j2sample/Application.java*
```java
package com.techobyte.j2sample;

import org.glassfish.jersey.server.ResourceConfig;

public class Application extends ResourceConfig {

	public Application() {
		packages(this.getClass().getPackage().getName());
	}

}
```

### Update web.xml with Application in ServletContainer
```xml
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
   http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
   version="3.0">
   <display-name>Servlet 3.0 Web Application</display-name>
   <servlet>
      <servlet-name>j2sample</servlet-name>
      <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
      <init-param>
         <param-name>javax.ws.rs.Application</param-name>
         <param-value>com.techobyte.j2sample.Application</param-value>
      </init-param>
   </servlet>
</web-app>
```

### Add RESTful API endpoint code
```java
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
```

### Update web.xml with api pattern
```xml
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
   http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
   version="3.0">
   <display-name>Servlet 3.0 Web Application</display-name>
   <servlet>
      <servlet-name>j2sample</servlet-name>
      <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
      <init-param>
         <param-name>javax.ws.rs.Application</param-name>
         <param-value>com.techobyte.j2sample.Application</param-value>
      </init-param>
   </servlet>
   <servlet-mapping>
      <servlet-name>j2sample</servlet-name>
      <url-pattern>/api/*</url-pattern>
   </servlet-mapping>
</web-app>
```

### Folder Structure
```sh
$ tree .
j2sample/
|-- pom.xml
`-- src
    `-- main
        |-- java
        |   `-- com
        |       `-- techobyte
        |           `-- j2sample
        |               |-- Application.java
        |               `-- HelloWorldWS.java
        |-- resources
        `-- webapp
            |-- index.jsp
            `-- WEB-INF
                `-- web.xml
```

### Build/Compile
```sh
$ mvn clean install
$ mvn package
```

### Run/Debug
```sh
$ mvn tomcat:run
$ mvnDebug tomcat:run
```

### Verify Endpoint
* Browse to http://localhost:8080/j2sample/api/helloworld
* ```curl http://localhost:8080/j2sample/api/helloworld```

### WADL Output
http://localhost:8080/j2sample/api/application.wadl

### [Optional] Adding JSON support using Jackson
#### [JSON Support] Adding Model class
*./src/main/java/com/techobyte/j2sample/model/Hello.java*
```java
package com.techobyte.j2sample.model;

public class Hello {
	private String message = "Hello ";

	public Hello() {
		setMessage("Hello ");
	}
	
	public Hello(String to) {
		setMessage(getMessage() + to);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Hello [message=" + message + "]";
	}
}

```

#### [JSON Support] Adding new Endpoint for JSON
```java
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
```

#### [JSON Support] Adding Jackson dependency
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
   <modelVersion>4.0.0</modelVersion>
   <groupId>com.techobyte</groupId>
   <artifactId>j2sample</artifactId>
   <packaging>war</packaging>
   <version>0.0.1</version>
   <name>j2sample Maven Webapp</name>
   <url>http://maven.apache.org</url>
   <dependencies>
      <dependency>
         <groupId>org.glassfish.jersey.containers</groupId>
         <artifactId>jersey-container-servlet</artifactId>
         <version>2.19</version>
      </dependency>
      <dependency>
         <groupId>com.fasterxml.jackson.jaxrs</groupId>
         <artifactId>jackson-jaxrs-json-provider</artifactId>
         <version>2.5.4</version>
      </dependency>
   </dependencies>
   <build>
      <finalName>j2sample</finalName>
   </build>
</project>
```

#### [JSON Support] Updating web.xml with new package
```xml
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
   http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
   version="3.0">
   <display-name>Servlet 3.0 Web Application</display-name>
   <servlet>
      <servlet-name>j2sample</servlet-name>
      <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
      <init-param>
         <param-name>javax.ws.rs.Application</param-name>
         <param-value>com.techobyte.j2sample.Application</param-value>
      </init-param>
      <init-param>
        <param-name>jersey.config.server.provider.packages</param-name>
        <param-value>com.techobyte.j2sample, com.fasterxml.jackson.jaxrs.json</param-value>
      </init-param>
   </servlet>
   <servlet-mapping>
      <servlet-name>j2sample</servlet-name>
      <url-pattern>/api/*</url-pattern>
   </servlet-mapping>
</web-app>
```

#### [JSON Support] Verify Endpoint
* Browse to http://localhost:8080/j2sample/api/helloworld/json
* ```curl http://localhost:8080/j2sample/api/helloworld/json```

### [Optional] Adding Unit Tests using JUnit
#### [Unit Tests Support] Adding Dependency
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
   <modelVersion>4.0.0</modelVersion>
   <groupId>com.techobyte</groupId>
   <artifactId>j2sample</artifactId>
   <packaging>war</packaging>
   <version>0.0.1</version>
   <name>j2sample Maven Webapp</name>
   <url>http://maven.apache.org</url>
   <dependencies>
      <dependency>
         <groupId>org.glassfish.jersey.containers</groupId>
         <artifactId>jersey-container-servlet</artifactId>
         <version>2.19</version>
      </dependency>
      <dependency>
         <groupId>com.fasterxml.jackson.jaxrs</groupId>
         <artifactId>jackson-jaxrs-json-provider</artifactId>
         <version>2.5.4</version>
      </dependency>
      <dependency>
         <groupId>junit</groupId>
         <artifactId>junit</artifactId>
         <version>4.10</version>
         <scope>test</scope>
      </dependency>
   </dependencies>
   <build>
      <finalName>j2sample</finalName>
   </build>
</project>
```

#### [Unit Tests Support] Creating Maven folder structure
```sh
mkdir ./src/test/java
mkdir ./src/test/resources
```

#### [Unit Tests Support] Create Test Case
*./src/test/java/j2sample/TestModelHello.java*
```java
package j2sample;

import static org.junit.Assert.*;
import org.junit.Test;

import com.techobyte.j2sample.model.Hello;


public class TestModelHello {

	public TestModelHello() {
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
```

#### [Unit Tests Support] Run
Maven runs all test cases as part of build process.
To skip test append ```-DskipTests=true```

## Links
* [Java JDK 7]
* [Maven Plugins]
* [Tomcat 7]
* [Jersey 2]

[Java JDK 7]:http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
[Maven Plugins]:https://maven.apache.org/plugins/index.html
[Jersey 2]:https://jersey.java.net
[Tomcat 7]:https://tomcat.apache.org/download-70.cgi
