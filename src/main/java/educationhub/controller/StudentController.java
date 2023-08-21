package educationhub.controller;


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
	@PostMapping("/school/{schoolId}/teacher/{teacherId}/student")
	@ResponseStatus(code = HttpStatus.CREATED)
	public StudentData addStudent(@PathVariable Long schoolId, @PathVariable Long teacherId, @RequestBody StudentData studentData) {
		log.info("Add student {} to school with id {} and teacher with id {}", studentData, schoolId,teacherId);
		return studentService.addStudentToSchoolAndTeacher(schoolId,teacherId,studentData);
	}
	
	
	//Modify student info
	@PutMapping("/school/{schoolId}/teacher/{teacherId}/student/{studentId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public StudentData modifyStudent(@PathVariable Long schoolId, @PathVariable Long teacherId,@PathVariable Long studentId, @RequestBody StudentData studentData) {
		//studentData.setStudentId(studentId);
		log.info("Modify student {} to school with id {} and teacher with id {}", studentData, schoolId,teacherId);
		return studentService.addStudentToSchoolAndTeacher(schoolId,teacherId,studentData);
	}
	
	@PostMapping("teacher/{teacherId}/student")
    @ResponseStatus(HttpStatus.OK)
	public StudentData updateStudentWithNewTeachers(
	        @PathVariable Long teacherId,
	        @RequestBody StudentData studentData
	) {
	    log.info("Add student {} to associate with  teachers with id{}", teacherId);
	    return studentService.saveStudentWithAssociateTeacher(teacherId, studentData);
	}
//	
//	//Modify student's school and teacher
//	@PutMapping("school/{schoolId}/teacher/{teacherId}student/{studentId}")
//	@ResponseStatus(code = HttpStatus.CREATED)
//	public StudentData modifySchoolandTeacherForStudentWithId(@PathVariable Long schoolId,@PathVariable Long teacherId,@PathVariable Long studentId, @RequestBody StudentData studentData) {
//		log.info("Modify student {} for school with id {} and teacher id {} ",studentData, schoolId, teacherId);
//		return studentService.updateStudentWithSchoolAndTeacher(schoolId,studentId,teacherId, studentData);
//	}
	
	
	//get student with Id
	@GetMapping("/student/{studentId}")
	public StudentData retrieveStudentById(@PathVariable Long studentId) {
		log.info("Retrieving student with id {}", studentId);
		return studentService.retrieveStudentById(studentId);
	}
	

//	//delete student from teacher
//	@DeleteMapping("teacher/{teacherId}/student/{studentId}")
//	public Map<String, String> deleteStudentByTeacher(@PathVariable Long teacherId, @PathVariable Long studentId) {
//		log.info("Deleting student by teacherId {}", teacherId);
//		studentService.deleteStudentByTeacher(teacherId);
//		return Map.of("message","Deleted studnet with id {}"+studentId+" for teacher with id {}"+teacherId);
//
//	}
//	
	//delete student by id
	@DeleteMapping("student/{studentId}")
	public Map<String, String> deleteStudentBySchool(@PathVariable Long studentId) {
		log.info("Deleting student ");
		studentService.deleteStudentById(studentId);
		
		return Map.of("message","Deleted studnet with id {}"+studentId);

	}

}
