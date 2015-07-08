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
   ...
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
   ...
   <dependencies>
      ...
      <dependency>
         <groupId>com.fasterxml.jackson.jaxrs</groupId>
         <artifactId>jackson-jaxrs-json-provider</artifactId>
         <version>2.5.4</version>
      </dependency>
   </dependencies>
   ...
</project>
```

#### [JSON Support] Updating web.xml with new package
```xml
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
   http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
   version="3.0">
   ...
   <servlet>
      ...
      <init-param>
        <param-name>jersey.config.server.provider.packages</param-name>
        <param-value>com.techobyte.j2sample, com.fasterxml.jackson.jaxrs.json</param-value>
      </init-param>
   </servlet>
   ...
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
   ...
   <dependencies>
      ...
      <dependency>
         <groupId>junit</groupId>
         <artifactId>junit</artifactId>
         <version>4.10</version>
         <scope>test</scope>
      </dependency>
   </dependencies>
   ...
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

### [Optional] Adding Regression Tests using JerseyTest
#### [Reg Tests Support] Adding Dependency
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
   ...
   <dependencies>
      ...
      <dependency>
         <groupId>org.glassfish.jersey.test-framework.providers</groupId>
         <artifactId>jersey-test-framework-provider-jdk-http</artifactId>
         <version>2.19</version>
         <scope>test</scope>
      </dependency>
   </dependencies>
   ...
</project>
```

#### [Reg Tests Support] Adding Regression Test
```java
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
```

### [Optional] Adding Logging support using Log4j
#### [Logging Support] Adding Dependency
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
   ...
   <dependencies>
      ...
      <dependency>
         <groupId>org.apache.logging.log4j</groupId>
         <artifactId>log4j-api</artifactId>
         <version>2.3</version>
      </dependency>
      <dependency>
         <groupId>org.apache.logging.log4j</groupId>
         <artifactId>log4j-core</artifactId>
         <version>2.3</version>
      </dependency>
   </dependencies>
   ...
</project>
```

#### [Logging Support] Adding Config
*./src/main/resources/log4j2.xml*
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
  <Appenders>
    <Console name="Console" target="SYSTEM_OUT">
      <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
    </Console>
  </Appenders>
  <Loggers>
    <Root level="info">
      <AppenderRef ref="Console"/>
    </Root>
  </Loggers>
</Configuration>
```

#### [Logging Support] Adding Code
```java
package com.techobyte.j2sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.techobyte.j2sample.model.Hello;

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
}
```


### [Optional] Adding ORM using MyBatis
#### [ORM Support] Adding Dependency
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
   ...
   <dependencies>
      ...
      <dependency>
         <groupId>org.mybatis</groupId>
         <artifactId>mybatis</artifactId>
         <version>3.3.0</version>
      </dependency>
      <dependency>
         <groupId>mysql</groupId>
         <artifactId>mysql-connector-java</artifactId>
         <version>5.1.36</version>
         <scope>runtime</scope>
      </dependency>
   </dependencies>
   <build>
      <finalName>j2sample</finalName>
      <plugins>
         <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.1</version>
            <configuration>
               <source>1.7</source>
               <target>1.7</target>
            </configuration>
         </plugin>
      </plugins>
   </build>
</project>
```

#### [ORM Support] Adding mybatis config files
*./src/main/resources/jdbc.properties*
```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/test
jdbc.username=root
jdbc.password=
```

*./src/main/resources/mybatis-config.xml*
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
   PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
   "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
   <properties resource='jdbc.properties' />
   <typeAliases>
      <typeAlias alias="User" type="com.techobyte.j2sample.model.User" />
   </typeAliases>
   <environments default="development">
      <environment id="development">
         <transactionManager type="JDBC" />
         <dataSource type="POOLED">
            <property name='driver' value='${jdbc.driver}' />
            <property name='url' value='${jdbc.url}' />
            <property name='username' value='${jdbc.username}' />
            <property name='password' value='${jdbc.password}' />
         </dataSource>
      </environment>
   </environments>
   <mappers>
      <mapper resource="com/techobyte/j2sample/mapper/UserMapper.xml" />
   </mappers>
</configuration>
```

#### [ORM Support] Adding Model Class
*./src/main/java/com/techobyte/j2sample/model/User.java*
```java
package com.techobyte.j2sample.model;

public class User {
  private Integer userId;
  private String userLogin;

  private String userFirstName;
  private String userLastName;
  private String userEmail;
  private String userRole;
  
  public User() {
    super();
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUserLogin() {
    return userLogin;
  }

  public void setUserLogin(String userLogin) {
    this.userLogin = userLogin;
  }

  public String getUserFirstName() {
    return userFirstName;
  }

  public void setUserFirstName(String userFirstName) {
    this.userFirstName = userFirstName;
  }

  public String getUserLastName() {
    return userLastName;
  }

  public void setUserLastName(String userLastName) {
    this.userLastName = userLastName;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }
  
  @Override
  public String toString() {
    return "User [userId=" + userId + ", userLogin=" + userLogin + "]";
  }
}
```

#### [ORM Support] Adding mapper Files
*./src/main/java/com/techobyte/j2sample/mapper/UserMapper.xml*
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
   "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.techobyte.j2sample.mapper.UserMapper">
   <resultMap id="resmapUser" type="User">
      <result property="userId" column="UserId_int" />
      <result property="userLogin" column="UserLogin_vchr" />
      <result property="userFirstName" column="UserFirstName_vchr" />
      <result property="userLastName" column="UserLastName_vchr" />
      <result property="userEmail" column="UserEMail_vchr" />
      <result property="userRole" column="UserRole_enm" />
   </resultMap>
   
   <select id="getAllUsers" resultMap="resmapUser">
      SELECT * FROM User
   </select>

   <select id="getUserById" parameterType="int" resultMap="resmapUser">
      SELECT * FROM User WHERE UserId_int = #{id}
   </select>
</mapper>
```

*./src/main/java/com/techobyte/j2sample/mapper/UserMapper.java*
```java
package com.techobyte.j2sample.mapper;

import java.util.List;
import com.techobyte.j2sample.model.User;

public interface UserMapper {
  public List<User> getAllUsers();
  public User getUserById(Integer id);
}
```

#### [ORM Support] Adding DAO Classes
*./src/main/java/com/techobyte/j2sample/dao/DBConnectionFactory.java*
```java
package com.techobyte.j2sample.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBConnectionFactory {
  private static SqlSessionFactory sqlSessionFactory;
  static {
    try {
      String resource = "mybatis-config.xml";
      Reader reader = Resources.getResourceAsReader(resource);
      if (sqlSessionFactory == null) {
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
      }
    }
    catch (FileNotFoundException fileNotFoundException) {
      fileNotFoundException.printStackTrace();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    }
  }

  public static SqlSessionFactory getSqlSessionFactory() {
    return sqlSessionFactory;
  }
}
```

*./src/main/java/com/techobyte/j2sample/dao/UserDAO.java*
```java
package com.techobyte.j2sample.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.techobyte.j2sample.mapper.UserMapper;
import com.techobyte.j2sample.model.User;


public class UserDAO {
  protected SqlSessionFactory sqlSessionFactory;
  
  public UserDAO() {
    sqlSessionFactory = DBConnectionFactory.getSqlSessionFactory();
  }

  public List<User> selectAll() {
    SqlSession session = sqlSessionFactory.openSession();
    try { 
      com.techobyte.j2sample.mapper.UserMapper userMapper = session.getMapper(UserMapper.class);
      return userMapper.getAllUsers();
    } finally {
      session.close();
    }
  }
  
  public User selectById(Integer id){
    SqlSession session = sqlSessionFactory.openSession();
    
    try {
      UserMapper userMapper = session.getMapper(UserMapper.class);
      return userMapper.getUserById(id);
    } finally {
      session.close();
    }
  }
}
```

#### [ORM Support] Adding to endpoint
```java
package com.techobyte.j2sample;

...
import com.techobyte.j2sample.model.User;

@Path("/helloworld")
public class HelloWorldWS {
  ...
  @GET
  @Path("/users")
  @Produces(MediaType.APPLICATION_JSON)
  public List<User> getAllUsers() {
    UserDAO daoUser = new UserDAO();
    return daoUser.selectAll();
  }
}
```

## Folder Structure
```sh
$ tree j2sample
j2sample
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── techobyte
    │   │           └── j2sample
    │   │               ├── Application.java
    │   │               ├── HelloWorldWS.java
    │   │               ├── dao
    │   │               │   ├── DBConnectionFactory.java
    │   │               │   └── UserDAO.java
    │   │               ├── mapper
    │   │               │   ├── UserMapper.java
    │   │               │   └── UserMapper.xml
    │   │               └── model
    │   │                   ├── Hello.java
    │   │                   └── User.java
    │   ├── resources
    │   │   ├── jdbc.properties
    │   │   ├── log4j2.xml
    │   │   └── mybatis-config.xml
    │   └── webapp
    │       ├── WEB-INF
    │       │   └── web.xml
    │       └── index.jsp
    └── test
        └── java
            └── j2sample
                ├── TestModelHello.java
                └── TestRegHelloWorldWS.java
```

## Links
* [Java JDK 7]
* [Maven Plugins]
* [Tomcat 7]
* [Jersey 2]
* [Log4j 2]
* [MyBatis 3]

[Java JDK 7]:http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
[Maven Plugins]:https://maven.apache.org/plugins/index.html
[Jersey 2]:https://jersey.java.net
[Tomcat 7]:https://tomcat.apache.org/download-70.cgi
[Log4j 2]:http://logging.apache.org/log4j/2.x/
[MyBatis 3]:https://mybatis.github.io/mybatis-3/