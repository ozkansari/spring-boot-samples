# Spring Boot Embedded Tomcat With Ajp Only Connector

Spring Boot's embedded tomcat starts with http port by default. You can initialize an additional ajp connector or override the default http connector explained here.

Useful links that helps me to solve this problem:

* https://github.com/spring-projects/spring-boot/issues/3359
* https://blog.swdev.ed.ac.uk/2015/06/24/adding-embedded-tomcat-ajp-support-to-a-spring-boot-application/
* https://stackoverflow.com/questions/49275241/spring-boot-2-ajp
* https://stackoverflow.com/questions/57491231/spring-boot-how-to-dynamically-add-new-tomcat-connector
* https://www.baeldung.com/embeddedservletcontainercustomizer-configurableembeddedservletcontainer-spring-boot
* https://docs.spring.io/spring-boot/docs/2.1.9.RELEASE/reference/html/howto-embedded-web-servers.html
* https://stackoverflow.com/questions/27071292/tomcat-behind-apache-using-ajp-for-spring-boot-application
* https://github.com/spring-projects/spring-boot/issues/20377
* https://tomcat.apache.org/tomcat-8.5-doc/config/ajp.html
