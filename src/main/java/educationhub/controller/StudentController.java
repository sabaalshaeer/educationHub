package educationhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import educationhub.model.StudentData;
import educationhub.service.StudentService;
import lombok.extern.slf4j.Slf4j;

@RestController // define the class as a controller.
// It indicates that the class is responsible for handling incoming HTTP
// requests and returning appropriate responses
@RequestMapping("/educationhub") // this will map to the URL ex http://localhost:8080/educationhub
@Slf4j // it is a Lombok annotation that automatically generates logging code in the
// class
public class StudentController {
	
	//inject service which will handle logic and work as a bridge between Controller and Repository/Dao
	@Autowired//this annotation will tell spring to manage Bean, Spring will create StudentService bean , add it to the registry and inject it into the studentService instance
	private StudentService studentService;
	
	//Add a student to an existing school and teacher
	@PostMapping("school/{schoolId}/teacher/{teacherId}/student")
	@ResponseStatus(code = HttpStatus.CREATED)
	public StudentData addStudent(@PathVariable Long schoolId, @PathVariable Long teacherId, @RequestBody StudentData studentData) {
		log.info("Add student {} to school with id {} and teacher with id {}", studentData, schoolId,teacherId);
		return studentService.addStudent(schoolId,teacherId,studentData);
	}
	

}
