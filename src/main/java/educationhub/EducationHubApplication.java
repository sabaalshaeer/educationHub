package educationhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // it combines several annotations,@ComponentScan and @EnableAutoConfiguration
						//@EnableAutoConfiguration:Automatically configure the application based on the classpath and defined beans
						//@ComponentScan:Automatically configure the application based on the classpath and defined beans
						//this @SpringBootApplication will start the component scan in educationhub package and will load all the sub-packages 
public class EducationHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(EducationHubApplication.class, args);//SpringApplication is a class to launch A Spring application from Java main method
																  //SpringApplication.run() method is used to start a Spring Boot application

	}

}
