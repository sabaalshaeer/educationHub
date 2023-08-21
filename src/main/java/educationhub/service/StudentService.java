package educationhub.service;

import java.util.NoSuchElementException;
import java.util.Optional;

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
	public StudentData addStudentToSchoolAndTeacher(Long schoolId, Long teacherId, StudentData studentData) {
		Teacher teacher = teacherService.findTeacherById(schoolId, teacherId);
	    School school =  schoolService.findSchoolById(schoolId);
	    Long studentId = studentData.getStudentId();
	    Student student = findOrCreateStudent(schoolId,studentId,teacherId,studentData);
	    
	    setStudentFields(student, studentData);
	    
	    // Associate the student with the school and teacher
        student.setSchool(school);
        student.getTeachers().add(teacher); //Add teacher to Student's list of teacher
        teacher.getStudents().add(student); // Add student to teacher's list of students

        return new StudentData(studentDao.save(student));
	    
	}

	private void setStudentFields(Student student, StudentData studentData) {
		student.setStudentFirstName(studentData.getStudentFirstName());
		student.setStudentLastName(studentData.getStudentLastName());
		student.setAge(studentData.getAge());
		student.setStudentEmail(studentData.getStudentEmail());
		student.setGrade(studentData.getGrade());
	}
	

	private Student findOrCreateStudent(Long schoolId, Long studentId,Long teacherId,StudentData studentData) {
		if(studentId == null) {
			String studentEmail = studentData.getStudentEmail();//get email from studentData payload
			
			Optional<Student> existingStudent = studentDao.findByStudentEmail(studentEmail);
			
			if(existingStudent.isPresent()) {
				System.out.println("Student"+ existingStudent+" already exists");
				throw new DuplicateKeyException("email for this student"+ existingStudent+" already exists");
			}else {
				Student newStudent = new Student();
				newStudent.setStudentEmail(studentEmail);
			 	return newStudent;
			}
			
		}else {
			return findStudentById(studentId, teacherId);
		}
	}



	private Student findStudentById(Long studentId, Long teacherId) {
		Student student = studentDao.findById(studentId).orElseThrow(() -> new NoSuchElementException("Student with id" + studentId+ "was not found"));
		Teacher teacher = teacherDao.findById(teacherId).orElseThrow(() -> new NoSuchElementException("teacher with id" + teacherId+ "was not found"));
		
		if(student.getSchool().getSchoolId().equals(teacher.getSchool().getSchoolId())) {
			for(Teacher singleTteacher : student.getTeachers()) {
				if(singleTteacher.getTeacherId().equals(teacherId)) {
					throw new IllegalArgumentException("student with id"+studentId+" not associate with teacher with id "+teacherId);
				}
			}
			return student;
		}else {
			throw new IllegalArgumentException("Student with id"+studentId+" not associated with the teacher has the same schoolId ");
		}
	}

	@Transactional(readOnly = false)
	public StudentData saveStudentWithAssociateTeacher(Long teacherId, StudentData studentData) {
		Teacher teacher = teacherService.findTeacherById(teacherId);
				
//		 String studentEmail = studentData.getStudentEmail();
		 
		 Long studentId = studentData.getStudentId();

		    
//	    // Check if a student with the same email already exists
//		Student student = studentDao.findByStudentEmail(studentEmail).orElseGet(() -> createNewStudent(studentData));

		 // Check if a student with the same ID already exists
	    Student student = studentDao.findById(studentId).orElseGet(() -> createNewStudent(studentData));
	    
	    
	    // Associate the student with the new teacher
		student.getTeachers().add(teacher);
		teacher.getStudents().add(student);
		
		setStudentFields(student, studentData); //set the fields from petStoreCustomer to the existing customer

		
		return new StudentData(studentDao.save(student));
		
	}
	
	//create new student if student's email exists then retrieve the existing student and associate student with new teacher
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
		Student student = studentDao.findById(studentId).orElseThrow(() -> new NoSuchElementException("Student with id" + studentId+ "was not found"));
		return new StudentData(student);
	}

	@Transactional(readOnly = false)
	public void deleteStudentById(Long studentId) {
		Student student = findStudentById(studentId);
		studentDao.delete(student);
		
	}
	
	private Student findStudentById(Long studentId) {
		Student student = studentDao.findById(studentId).orElseThrow(() -> new NoSuchElementException("Student with id" + studentId+ "was not found"));
		return student;
	}


	
}
