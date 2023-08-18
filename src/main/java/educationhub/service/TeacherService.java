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
import educationhub.entity.Teacher;
import educationhub.model.TeacherData;

@Service
public class TeacherService {

	@Autowired
	private TeacherDao teacherDao;

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

		teacher.setSchool(school);// set school to teacher
		school.getTeachers().add(teacher);// add the teacher to the list of teacher that associate with the school

		return new TeacherData(teacherDao.save(teacher));
	}

	private void setTeacherFields(Teacher teacher, TeacherData teacherData) {
		teacher.setTeacherId(teacherData.getTeacherId());
		teacher.setTeacher_first_name(teacherData.getTeacher_first_name());
		teacher.setTeacher_last_name(teacherData.getTeacher_last_name());
		teacher.setEmail(teacherData.getEmail());
		teacher.setTeacher_subject(teacherData.getTeacher_subject());

	}

	private Teacher findOrCreateTeacher(Long schoolId, Long teacherId, TeacherData teacherData) {
		// Checking if teacherId is null
		if (teacherId == null) {
			// Getting the email from teacherData
			String email = teacherData.getEmail();

			// Find existing teacher by email by passing variable email which we get it from teacherData
			Optional<Teacher> existingTeacher = teacherDao.findTeacherByEmail(email);
			// Check if an existing teacher with the same email exists
			if (existingTeacher.isPresent()) {
				throw new DuplicateKeyException("teacher with Email " + email + "exists");

			} else {
				// If no matching teacher was found, create a new teacher
				Teacher newTeacher = new Teacher();
				newTeacher.setEmail(email);// set the email
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

	public void deleteTeacherByIdWithSchoolId(Long schoolId, Long teacherId) {
		Teacher teacher = findTeacherById(schoolId, teacherId);
		teacherDao.delete(teacher);
	}

}
