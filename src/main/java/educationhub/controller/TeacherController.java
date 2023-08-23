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

import educationhub.model.TeacherData;
import educationhub.service.TeacherService;
import lombok.extern.slf4j.Slf4j;

@RestController // define the class as a controller.
				// It indicates that the class is responsible for handling incoming HTTP
				// requests and returning appropriate responses
@RequestMapping("/educationhub") // this will map to the URL ex http://localhost:8080/educationhub
@Slf4j // it is a Lombok annotation that automatically generates logging code in the
		// class
public class TeacherController {

	// inject service which will handle logic and work as a bridge between
	// Controller and Repository/Dao
	@Autowired // this annotation will tell spring to manage Bean, Spring will create
				// TeacherService bean , add it to the registry and inject it into the
				// teacherService instance
	private TeacherService teacherService;

	// Add new teacher to an exist school with id
	@PostMapping("/school/{schoolId}/teacher")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TeacherData addTeacherToSchool(@PathVariable Long schoolId, @RequestBody TeacherData teacherData) {
		log.info("Add Teacher {} to School with id {}", teacherData, schoolId);
		return teacherService.saveTeacher(schoolId, teacherData);
	}

	// modify an exist teacher to an exist school with id
	@PutMapping("/school/{schoolId}/teacher/{teacherId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TeacherData ModifyTeacherToSchool(@PathVariable Long schoolId, @PathVariable Long teacherId,
			@RequestBody TeacherData teacherData) {
		// set teacherId in the teacherData from the Id which pass in the method
		teacherData.setTeacherId(teacherId);
		log.info("Modify Teacher {} to School with id {}", teacherData, schoolId);
		return teacherService.saveTeacher(schoolId, teacherData);
	}

	// get list of teachers to school with id
	@GetMapping("/school/{schoolId}/teacher")
	public List<TeacherData> getAllTeachersAssWithSchoolWithId(@PathVariable Long schoolId) {
		return teacherService.getAllTeachersAssWithSchoolWithId(schoolId);
	}

	// get teacher by id
	@GetMapping("/teacher/{teacherId}")
	public TeacherData getTeacherById(@PathVariable Long teacherId) {
		return teacherService.getTeacherById(teacherId);
	}

	// get teacher by id associate with school with id
	@GetMapping("/school/{schoolId}/teacher/{teacherId}")
	public TeacherData getTeacherByIdWithSchoolId(@PathVariable Long schoolId, @PathVariable Long teacherId) {
		return teacherService.getTeacherByIdWithSchoolId(schoolId, teacherId);
	}

	// delete teacher for specific school
	@DeleteMapping("/school/{schoolId}/teacher/{teacherId}")
	public Map<String, String> deleteTeacheForSchoolWithId(@PathVariable Long schoolId, @PathVariable Long teacherId) {
		log.info("Deleting Teacher with Id{} for school with Id {}", teacherId, schoolId);

		teacherService.deleteTeacherByIdWithSchoolId(schoolId, teacherId);

		return Map.of("message", "Deletion of Teacher with Id=" + teacherId + " was successful.");

	}

}
