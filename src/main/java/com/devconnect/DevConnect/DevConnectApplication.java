package com.devconnect.DevConnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication = 3 annotations in one:
//   @SpringBootConfiguration  -> this class is a source of Spring beans
//   @EnableAutoConfiguration  -> configure things based on the dependencies in pom.xml
//   @ComponentScan            -> scan this package (and sub-packages) for
//                                @Component / @RestController / @Service ... classes
//
// Spring creates and manages objects of those classes (beans) for us.
// That is Inversion of Control: the framework owns object creation, not our code.
@SpringBootApplication
public class DevConnectApplication {

	public static void main(String[] args) {

		SpringApplication.run(DevConnectApplication.class, args);
	}

}
