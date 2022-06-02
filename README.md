<h1 align="center"><img height=25 width=25 src="https://user-images.githubusercontent.com/44253834/171605731-17be58eb-7935-494c-8c3e-67963107abd7.png"/>&nbsp;&nbsp;Employee Record</h1>
<p align="center">Employee Record is a Spring Boot Web Application that allows you to add, update, delete & view employees.</p>
<br>

<video height=500 width=600 src="https://user-images.githubusercontent.com/44253834/171632154-7867a16b-617f-4f1e-a5be-40be7e81d145.mp4" type="video/mp4" controls></video>


## Initialize Spring Boot Project

- To begin with the project, go to [Spring Initializr](https://start.spring.io/).
- Select Maven/Gradle project -> Select Lanaguage Java/Kotlin/Groovy -> Select Spring Boot Version -> Enter Project Metadata -> Select Packaging -> Select Java Version -> Add Spring Web Dependency 
- Finally, click on the Generate button to download a Spring Boot project zip on your system.
- Extract the zip file & open your project in Intellij IDEA / Eclipse / Netbeans / VS Code IDE.
- You'll find two important files shown below.

pom.xml (Project Object Model file)
```xml
<dependencies>
  <!-- Dependency for Spring Boot Web Application -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <!-- Dependency for testing the Spring Boot Application -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>    
```
SpringBootApplication.java (Main file)
```java
import org.springframework.boot.SpringApplication;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplication.class, args);
	}
}
```

## MySQL Database Setup & Connectivity with the application

- Open MySQL Workbench & select root user with all the privileges.
- Create a database "employee_record" & a table "employee" with below given schema.
```sql
CREATE TABLE employee (
    emp_id INT(3) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    gender VARCHAR(255),
    dob VARCHAR(255),
    age INT(3),
    salary DECIMAL(10,2),
    status VARCHAR(255)
);
```

- Add two more dependencies shown below to integrate MySQL database with your Spring Boot application.

pom.xml
```xml
<!-- Dependency for Spring Boot Data JPA which allows us to access and persist data between Java object/class and relational database -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<!-- Dependency for MySQL driver to integrate MySQL Database with the Spring Boot Application -->
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <scope>runtime</scope>
</dependency>
```

- Add below given properties in the "application.properties" file to configure your Spring Boot application to connect to your database using hibernate.

application.properties
```properties
# Connect MySQL Database
spring.datasource.platform=mysql
spring.datasource.url=jdbc:mysql://localhost:3306/employee_record
spring.datasource.username=root
spring.datasource.password=YourPassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Spring Data JPA & Hibernate properties
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=none
spring.jpa.open-in-view=false
spring.jpa.properties.hibernate.id.new_generator_mappings=false
```

- Create an Entity class that represents a table in the database and every instance of it represents a row of that table.

Employee.java
```java
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "emp_id")
    private int employeeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "dob")
    private String dob;

    @Column(name = "age")
    private int age;

    @Column(name = "salary")
    private double salary;

    @Column(name = "status")
    private String status;
}
```

- Create a Repository interface that provides the mechanism for storage, retrieval, update, delete and search operation on objects.

EmployeeRepository.java
```java
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
```

- Create a Controller class to create backend services/endpoints by implementing the repository interface methods.

EmployeeController.java
```java
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @RequestMapping(value ="/", method = RequestMethod.GET)
    public RedirectView redirectToIndex() {
        return new RedirectView("http://localhost:8080/employees");
    }
}
```
<h1 align="center">Demo</h1>

## Open Application
<video height=500 width=600 src="https://user-images.githubusercontent.com/44253834/171631754-5cdaea10-5cf6-4c2d-bcce-61496762352d.mp4" type="video/mp4" controls></video>

```java
@RequestMapping(value ="/employees", method = RequestMethod.GET)
public String index() {
    return "index";
}
```

## Add an Employee
<video height=500 width=600 src="https://user-images.githubusercontent.com/44253834/171631107-1788981d-a67a-487c-8dbd-c8149161e327.mp4" type="video/mp4" controls></video>

```java
@RequestMapping(value = "/employees/add", method = RequestMethod.POST)
public @ResponseBody ResponseEntity addNewEmployee(@RequestBody Map<String,String> body) {

    if (isEmployee(body.get("email"))) {
        return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    } else {
        String firstName = StringUtils.capitalize(body.get("firstName").trim());
        String lastName = StringUtils.capitalize(body.get("lastName").trim());
        String email = body.get("email").trim();
        String gender = body.get("gender").trim();
        String dob = body.get("dob").trim();
        double salary = Double.parseDouble(body.get("salary").trim());
        String status = body.get("status").trim();

        // Save form data to the database
        try {
            Employee emp = new Employee(firstName,lastName,email,gender,dob,salary,status);
            employeeRepository.save(emp);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }
```

## Edit an Employee
<video height=500 width=600 src="https://user-images.githubusercontent.com/44253834/171633006-d2c8fefe-d900-4817-8d78-a019cf8832ec.mp4" type="video/mp4" controls></video>

```java
@RequestMapping(value = "/employees/edit/{id}", method = RequestMethod.PUT)
public @ResponseBody ResponseEntity updateEmployee(@RequestBody Map<String,String> body, @PathVariable int id) {

    String firstName = StringUtils.capitalize(body.get("firstName").trim());
    String lastName = StringUtils.capitalize(body.get("lastName").trim());
    String email = body.get("email").trim();
    String gender = body.get("gender").trim();
    String dob = body.get("dob").trim();
    double salary = Double.parseDouble(body.get("salary").trim());
    String status = body.get("status").trim();

    // Update form data in the database
    try {
        Employee emp = new Employee();
        emp.setEmployeeId(id);
        emp.setFirstName(firstName);
        emp.setLastName(lastName);
        emp.setEmail(email);
        emp.setGender(gender);
        emp.setDob(dob);
        emp.setAge(dob);
        emp.setSalary(salary);
        emp.setStatus(status);
        employeeRepository.save(emp);
    } catch (Exception e){
        return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }
    return new ResponseEntity(HttpStatus.CREATED);
}
```

## Delete an Employee
<video height=500 width=600 src="https://user-images.githubusercontent.com/44253834/171633118-fb28b0da-04e3-4a7b-9c0b-14b3a89995f3.mp4" type="video/mp4" controls></video>

```java
@RequestMapping(value = "/employees/delete/{id}", method = RequestMethod.DELETE)
public @ResponseBody ResponseEntity deleteAnEmployee(@PathVariable String id){
    int empId = Integer.parseInt(id);
    if (isEmployeeId(empId)) {
        employeeRepository.deleteById(empId);
        return new ResponseEntity(HttpStatus.OK);
    } else {
        return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }
}
```


<p align="center">Drop your queries at <a href="mailto:dev.paurav@gmail.com">dev.paurav@gmail.com</a>.</p>
