package educationhub.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import educationhub.dao.TeacherDao;
import educationhub.entity.School;
import educationhub.entity.Student;
import educationhub.entity.Teacher;
import educationhub.model.TeacherData;

@Service
public class TeacherService {

	@Autowired
	private TeacherDao teacherDao;
	
//	@Autowired
//	StudentDao studentDao;

	@Autowired
	private SchoolService schoolService;// inject this service because I need to call methods in this service

	@Transactional(readOnly = false)
	public TeacherData saveTeacher(Long schoolId, TeacherData teacherData) {
		// fetch school by calling findSchoolById method which is in SchoolService class
		School school = schoolService.findSchoolById(schoolId);
		// fetch the teacher id
		Long teacherId = teacherData.getTeacherId();
		// get teacher associate with the school
		Teacher teacher = findOrCreateTeacher(schoolId, teacherId, teacherData);
		// set fields from teacherData to the teacher obj
		setTeacherFields(teacher, teacherData);

		teacher.setSchool(school);// set school to that teacher
		school.getTeachers().add(teacher);// add the teacher to the list of teacher that associate with the school
	    
		return new TeacherData(teacherDao.save(teacher));
	}

	private void setTeacherFields(Teacher teacher, TeacherData teacherData) {
		teacher.setTeacherId(teacherData.getTeacherId());
		teacher.setTeacherFirstName(teacherData.getTeacherFirstName());
		teacher.setTeacherLastName(teacherData.getTeacherLastName());
		teacher.setTeacherEmail(teacherData.getTeacherEmail());
		teacher.setTeacherSubject(teacherData.getTeacherSubject());
		

	}

	private Teacher findOrCreateTeacher(Long schoolId, Long teacherId, TeacherData teacherData) {
		// Checking if teacherId is null
		if (teacherId == null) {
			// Getting the email from teacherData
			String teacherEmail = teacherData.getTeacherEmail();

			// Find existing teacher by email by passing variable email which we get it from teacherData
			Optional<Teacher> existingTeacher = teacherDao.findByTeacherEmail(teacherEmail);
			// Check if an existing teacher with the same email exists
			if(existingTeacher.isPresent()) {
				throw new DuplicateKeyException("email for this teacher"+ existingTeacher+" already exists");
			}else {
				Teacher newTeacher = new Teacher();
				newTeacher.setTeacherEmail(teacherEmail);
				return newTeacher;
			}
		} else {
			// If teacherId is provided, find the teacher by ID
			return findTeacherById(schoolId, teacherId);

		}
	}

	public Teacher findTeacherById(Long schoolId, Long teacherId) {
		Teacher teacher = teacherDao.findById(teacherId)
				.orElseThrow(() -> new NoSuchElementException("Teacher with id " + teacherId + "not found"));

		// check if the teacher associate with the schoolId
		if (teacher.getSchool().getSchoolId().equals(schoolId)) {
			return teacher;
		} else {
			throw new IllegalArgumentException(
					"Teacher with Id " + teacherId + "is not associate with the school with id" + schoolId);
		}
	}

	@Transactional(readOnly = true)
	public List<TeacherData> getAllTeachersAssWithSchoolWithId(Long schoolId) {
		// fetch school by calling findSchoolById method which is in SchoolService class
		School school = schoolService.findSchoolById(schoolId);
		// create list
		List<TeacherData> listOfTeachersData = new LinkedList<>();
		for (Teacher teacher : school.getTeachers()) {
			TeacherData teacherData = new TeacherData(teacher);
			teacherData.getStudents().clear();// Don't get students which associate with the teacher
			listOfTeachersData.add(teacherData);
		}
		return listOfTeachersData;
	}

	@Transactional(readOnly = true)
	public TeacherData getTeacherById(Long teacherId) {
		Teacher teacher = teacherDao.findById(teacherId)
				.orElseThrow(() -> new NoSuchElementException("Teacher with id " + teacherId + "not found"));
		return new TeacherData(teacher);
	}

	@Transactional(readOnly = true)
	public TeacherData getTeacherByIdWithSchoolId(Long schoolId, Long teacherId) {
		Teacher teacher = findTeacherById(schoolId, teacherId);
		return new TeacherData(teacher);
	}

	@Transactional(readOnly = false)
	public void deleteTeacherByIdWithSchoolId(Long schoolId, Long teacherId) {
		Teacher teacher = findTeacherById(schoolId, teacherId);

		if(teacher.getSchool().getSchoolId() != schoolId) {
			throw new IllegalArgumentException("school with id "+teacherId+" does not associate with this teacher");
		}
		// Remove the association between teacher and students
	    for (Student student : teacher.getStudents()) {
	        student.getTeachers().remove(teacher);
	    }
	    
	    // Clear the association on the teacher's side as well
	    teacher.getStudents().clear();

	    // Now you can safely delete the teacher
	    teacherDao.delete(teacher);
	}

	//will use this method in student service
	 @Transactional(readOnly = true)
	 public Teacher findTeacherById(Long teacherId) {
		 return teacherDao.findById(teacherId)
                .orElseThrow(() -> new NoSuchElementException("Teacher with id" + teacherId + " not found"));
	    }

	public Teacher findTeacherByFirstNameAndSchool(Long schoolId, String teacherFirstName) {
		 School school = schoolService.findSchoolById(schoolId); // You need to have a schoolService for this
	        
	        if (school != null) {
	            return teacherDao.findByTeacherFirstNameAndSchool(teacherFirstName, school);
	        } else {
	            throw new IllegalArgumentException("School with id" + schoolId + " not found.");
	        }
	}
}
