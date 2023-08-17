package educationhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import educationhub.model.SchoolData;
import educationhub.service.SchoolService;
import lombok.extern.slf4j.Slf4j;

@RestController // define the class as a controller.
				//It indicates that the class is responsible for handling incoming HTTP requests and returning appropriate responses
@RequestMapping("/educationhub") // this will map to the URL ex http://localhost:8080/educationhub
@Slf4j //it is a Lombok annotation that automatically generates logging code in the class
public class SchoolController{
	
	//inject service which will handle logic and work as a bridge between Controller and Repository/Dao
	@Autowired//this annotation will tell spring to manage Bean, Spring will create SchoolService bean , add it to the registry and inject it into the schoolService instance
	private SchoolService schoolService;
	
	//Add new school
	//this method will get a post request to /educationhub/school 
	@PostMapping("/school")
	public SchoolData insertSchool(@RequestBody SchoolData schoolData) {
		log.info("Creating School {}", schoolData);
		return schoolService.insertSchool(schoolData);
		
	}

}
