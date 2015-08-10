set "JAVA_HOME=D:\Java\jdk1.8.0_51"

rem ###### set "JAVA_HOME=C:\Users\Maureen\Documents\Java\java_ee_sdk-7u1"

set "CLASSPATH=D:\Java\jdk1.8.0_51\jre\lib\rt.jar;D:\Java\jdk1.8.0_51\jre\lib\servlet-api.jar;C:\Users\svclaiborne\Documents\projects\read_practice2\app\java;D:\Java\3rd_libs\javax.json-api-1.0.jar;D:\Program Files\tomcat\apache-tomcat-8.0.24\lib\servlet-api.jar;D:\Program Files\tomcat\apache-tomcat-8.0.24\lib\catalina.jar"

D:\Java\jdk1.8.0_51\bin\javac @file-list.txt -d "D:\Program Files\tomcat\apache-tomcat-8.0.24\webapps\service\WEB-INF\classes"