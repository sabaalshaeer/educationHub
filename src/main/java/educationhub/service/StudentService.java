package educationhub.service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import educationhub.dao.StudentDao;
import educationhub.dao.TeacherDao;
import educationhub.entity.School;
import educationhub.entity.Student;
import educationhub.entity.Teacher;
import educationhub.model.StudentData;

@Service
public class StudentService {

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private TeacherDao teacherDao;

	@Autowired
	private SchoolService schoolService;// inject this school service because I need to call methods in this service

	@Autowired
	private TeacherService teacherService;// inject this teacher service because I need to call methods in this service

	@Transactional(readOnly = false)
	public StudentData saveStudent(Long schoolId, StudentData studentData) {
		School school = schoolService.findSchoolById(schoolId);
		Set<Teacher> teachers = teacherDao.findAllByTeacherFirstNameIn(studentData.getTeachers());
		Student student = findOrCreateNewStudent(studentData.getStudentId(), studentData);
		setStudentFields(student, studentData);
		
		student.setSchool(school);
		school.getStudents().add(student);
		
		for(Teacher teacher: teachers) {
			if(student.getSchool().getSchoolId().equals(teacher.getSchool().getSchoolId())){
				teacher.getStudents().add(student);
				student.getTeachers().add(teacher);
			}else {
				throw new IllegalArgumentException("Teacher does not associate with the same school that student associate with");
			}
			
		}
		
		Student dbStudent =studentDao.save(student);
		return new StudentData(dbStudent);

	}

	private Student findOrCreateNewStudent(Long studentId, StudentData studentData) {
		if(Objects.isNull(studentId)) {
			String studentEmail = studentData.getStudentEmail();// get email from studentData payload

			Optional<Student> existingStudent = studentDao.findByStudentEmail(studentEmail);

			if (existingStudent.isPresent()) {
				System.out.println("Student" + existingStudent + " already exists");
				throw new DuplicateKeyException("email for this student" + existingStudent + " already exists");
			} else {
				Student newStudent = new Student();
				newStudent.setStudentEmail(studentEmail);
				return newStudent;
			}
		}else {
			return findStudentById(studentId);
		}
	}

	private void setStudentFields(Student student, StudentData studentData) {
		student.setStudentFirstName(studentData.getStudentFirstName());
		student.setStudentLastName(studentData.getStudentLastName());
		student.setAge(studentData.getAge());
		student.setStudentEmail(studentData.getStudentEmail());
		student.setGrade(studentData.getGrade());

	}



	@Transactional(readOnly = false)
	public StudentData associateStudentWithOtherTeacherTeacher(Long teacherId, StudentData studentData) {
		// Retrieve the teacher using the provided teacherId
		Teacher teacher = teacherService.findTeacherById(teacherId);
		// get student's email to check if it exists
		String studentEmail = studentData.getStudentEmail();

		// Check if a student with the same email already exists find it or create new
		// one
		Student student = studentDao.findByStudentEmail(studentEmail).orElseGet(() -> createNewStudent(studentData));

		// Check if the student and teacher belong to the same school
		if (!student.getSchool().equals(teacher.getSchool())) {
			throw new IllegalArgumentException("Student and teacher are not associated with the same school.");
		}

		// Associate the student with the new teacher
		student.getTeachers().add(teacher);
		teacher.getStudents().add(student);

		setStudentFields(student, studentData);// Set the student's fields from the provided studentData
		// Save the updated student and return its data

		return new StudentData(studentDao.save(student));

	}

	// create new student if student with given email does not exist
	private Student createNewStudent(StudentData studentData) {
		Student newStudent = new Student();
		newStudent.setStudentEmail(studentData.getStudentEmail());
		newStudent.setStudentFirstName(studentData.getStudentFirstName());
		newStudent.setStudentLastName(studentData.getStudentLastName());
		newStudent.setAge(studentData.getAge());
		newStudent.setGrade(studentData.getGrade());

		return newStudent;
	}

	@Transactional(readOnly = true)
	public StudentData retrieveStudentById(Long studentId) {
		Student student = studentDao.findById(studentId)
				.orElseThrow(() -> new NoSuchElementException("Student with id" + studentId + "was not found"));
		return new StudentData(student);
	}

	@Transactional(readOnly = false)
	public void deleteStudentById(Long studentId) {
		Student student = findStudentById(studentId);
		
		// Remove the association between teacher and students
	    for (Teacher teacher : student.getTeachers()) {
	        teacher.getStudents().remove(student);
	    }
	    
	    // Clear the association on the student side as well
	    student.getTeachers().clear();

	    // delete the student
		studentDao.delete(student);

	}

	private Student findStudentById(Long studentId) {
		Student student = studentDao.findById(studentId)
				.orElseThrow(() -> new NoSuchElementException("Student with id" + studentId + "was not found"));
		return student;
	}

	@Transactional(readOnly = false)
	public void deleteRealtionshipBetweenStudentAndTeacher(Long teacherId, Long studentId) {
		Student student = findStudentById(studentId);
		Teacher teacher = teacherService.findTeacherById(teacherId);

		if(student.getSchool().getSchoolId().equals(teacher.getSchool().getSchoolId())) {
			// Remove the student-teacher relationship from the join table
			student.getTeachers().remove(teacher);// Remove the teacher from the student's teacher list
			teacher.getStudents().remove(student);// Remove the student from the teacher's student list

			// Save the updated teacher and student entities
			teacherDao.save(teacher);
			studentDao.save(student);
		}else {
			throw new IllegalArgumentException("Teacher with id "+teacherId+" does not associate to the same school for the student with id "+studentId);
		}
		
	}



}
