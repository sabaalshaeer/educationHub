package educationhub.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	//insert school
	//this method will get a post request to /educationhub/school 
	@PostMapping("/school")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SchoolData insertSchool(@RequestBody SchoolData schoolData) {
		log.info("Creating School {}", schoolData);
		return schoolService.saveSchool(schoolData);
		
	}
	
	//modify the school object
	@PutMapping("/school/{schoolId}")
	public SchoolData updateSchool(@PathVariable Long schoolId,
			@RequestBody SchoolData schoolData) {
		//Set the school ID in the school data from the ID parameter.
		schoolData.setSchoolId(schoolId);
		log.info("Updating school {}", schoolData);
		return schoolService.saveSchool(schoolData);
	}
	
	//get a list of school without students and teachers data
	@GetMapping("/school")
	public List<SchoolData> getAllSchools(){
		return schoolService.retrieveAllSchools();
	}

	//get school By id with students and teachers data
	@GetMapping("/school/{schoolId}")
	public SchoolData getSchoolById(@PathVariable Long schoolId){
		return schoolService.retrieveSchoolWithId(schoolId);
	}
	
	//delete school by id
	@DeleteMapping("/school/{schoolId}")
	public Map<String, String> deleteSchoolById(@PathVariable Long schoolId){
		log.info("Deleting school with Id={}", schoolId);
		schoolService.deleteSchoolWithId(schoolId);
		
		return Map.of("message", "Deleting of School with id=" + schoolId+ " was successful!");
	}
	
}
