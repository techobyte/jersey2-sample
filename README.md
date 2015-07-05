# Tutorial Jersey2 (Servlet 3.0) boiler plate code

# Maven
### Pre-requisites
* Java JDK 7
* Maven 3
* Eclipse Luna
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
*src/main/java/com/techobyte/j2sample/Application.java*
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

## Links
* [Java JDK 7]
* [Maven Plugins]
* [Jersey 2]

[Java JDK 7]:http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
[Maven Plugins]:https://maven.apache.org/plugins/index.html
[Jersey 2]:https://jersey.java.net
