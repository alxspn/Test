# Test

Build and run:

need: Tomcat, MySql (encoding DB – UTF-8), Maven

сhange in Test/TestREST/src/main/webapp/WEB-INF/jdbc.properties properties "jdbc.username" and "jdbc.password" (default- "root" and "mysqlpsw")

build, run junit tests and deploy: from command line in Test run:

mvn -pl TestDTO package

mvn -pl TestREST tomcat7:deploy

mvn -pl TestWEB tomcat7:deploy

testing:

http://localhost:8080/TestREST/departments/list - returns a list of all departments http://localhost:8080/TestREST/departments/{id} – returns department with the appropriate id http://localhost:8080/TestREST/employers/list - returns a list of all employees 
http://localhost:8080/TestREST/employers/{id} – return employee by id 
http://localhost:8080/TestREST/employers/listByDepartment/{id} - returns a list of employees of the department with the appropriate id

http://localhost:8080/TestWEB  - start page
