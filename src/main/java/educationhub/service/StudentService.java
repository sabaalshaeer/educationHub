package educationhub.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import educationhub.dao.StudentDao;
import educationhub.entity.School;
import educationhub.entity.Student;
import educationhub.entity.Teacher;
import educationhub.model.StudentData;

@Service
public class StudentService {
	
	@Autowired
	private StudentDao studentDao;

	@Autowired
	private SchoolService schoolService;// inject this school service because I need to call methods in this service

	@Autowired
	private TeacherService teacherService;// inject this teacher service because I need to call methods in this service

	public StudentData addStudent(Long schoolId, Long teacherId, StudentData studentData) {
		Teacher teacher = teacherService.findTeacherById(schoolId, teacherId);
	    School school =  schoolService.findSchoolById(schoolId);
	    Long studentId = studentData.getStudentId();
	    Student student = findOrCreateStudent(schoolId,studentId);
	    
	    setStudentFields(student, studentData);
	    
	 // Associate the student with the school and teacher
        student.setSchool(school);
        student.getTeachers().add(teacher); //Add teacher to Student's list of teacher
        teacher.getStudents().add(student); // Add student to teacher's list of students

        studentDao.save(student); // Save the student
        //teacherDao.save(teacher); // Save the teacher

        return new StudentData(student);
	    
	}

	private void setStudentFields(Student student, StudentData studentData) {
		student.setStudent_first_name(studentData.getStudent_first_name());
		student.setStudent_last_name(studentData.getStudent_last_name());
		student.setAge(studentData.getAge());
		student.setGrade(studentData.getGrade());
	}

	private Student findOrCreateStudent(Long schoolId, Long studentId) {
		if(studentId == null) {
			return new Student();
		}else {
			return findStudentById(schoolId,studentId);
		}
	}

	private Student findStudentById(Long schoolId, Long studentId) {
		Student student = studentDao.findById(studentId).orElseThrow(() -> new NoSuchElementException("Student with id" + studentId+ "was not found"));
		
		if(student.getSchool().getSchoolId().equals(schoolId)) {
			return student;
		}else {
			throw new IllegalArgumentException("Student with id"+studentId+" not associated with the school with id "+schoolId);
		}
	}

}
