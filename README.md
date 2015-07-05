# Tutorial Jersey2 boiler plate code
* Create a maven application project
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
* Update Web Descriptor File (web.xml)
```xml
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
   http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
   version="3.0">
   <display-name>Servlet 3.0 Web Application</display-name>
</web-app>
```
