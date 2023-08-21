package educationhub.service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional(readOnly = false)
	public StudentData addStudent(Long schoolId, Long teacherId, StudentData studentData) {
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
			return findStudentById(schoolId,studentId, teacherId);
		}
	}

	

	private Student findStudentById(Long schoolId, Long studentId, Long teacherId) {
		Student student = studentDao.findById(studentId).orElseThrow(() -> new NoSuchElementException("Student with id" + studentId+ "was not found"));
		
		if(student.getSchool().getSchoolId().equals(schoolId)) {
			for(Teacher teacher : student.getTeachers()) {
				if(teacher.getTeacherId().equals(teacherId)) {
					throw new IllegalArgumentException("student with id"+studentId+" not associate with teacher with id "+teacherId);
				}
			}
			return student;
		}else {
			throw new IllegalArgumentException("Student with id"+studentId+" not associated with the school with id "+schoolId);
		}
	}


	public StudentData retrieveStudentById(Long studentId) {
		Student student = studentDao.findById(studentId).orElseThrow(() -> new NoSuchElementException("Student with id" + studentId+ "was not found"));
		return new StudentData(student);
	}

	



	

	
	private Student findStudentById(Long studentId) {
		Student student = studentDao.findById(studentId).orElseThrow(() -> new NoSuchElementException("Student with id" + studentId+ "was not found"));
		return student;
	}
	
	private void setStudentFieldsWithTeacherAndSchool(Student student, StudentData studentData) {
		student.setStudentFirstName(studentData.getStudentFirstName());
		student.setStudentLastName(studentData.getStudentLastName());
		student.setAge(studentData.getAge());
		student.setGrade(studentData.getGrade());
		//student.getSchool().setSchoolId(studentData.getSchoolId());
		
		School currentSchool = student.getSchool();
	    if (currentSchool != null && !currentSchool.getSchoolId().equals(studentData.getSchoolId())) {
	        student.setSchool(null); // Clear the current school reference
	    }
		
//		student.getTeachers().clear(); // Clear existing teachers and add the new one
//	    if (teacherId != null) {
//	        Teacher teacher = teacherService.findTeacherById(student.getSchool().getSchoolId(), teacherId);
//	        student.getTeachers().add(teacher);
//	    }
		
	}



	@Transactional(readOnly = false)
	public void deleteStudentById(Long studentId) {
		Student student = findStudentById(studentId);
		studentDao.delete(student);
		
	}

	public StudentData updateStudentWithNewTeachers(Long studentId, List<String> teacherFirstNames) {
		 Student student = findStudentById(studentId);
		 School studentSchool = student.getSchool();

		 Set<Teacher> newTeachers = new HashSet<>();
		    for (String teacherFirstName : teacherFirstNames) {
		        Teacher newTeacher = teacherService.findTeacherByFirstNameAndSchool(studentSchool.getSchoolId(), teacherFirstName);
		        if (newTeacher != null && newTeacher.getSchool().equals(studentSchool)) {
		            newTeachers.add(newTeacher);
		        } else {
		            throw new IllegalArgumentException("Teacher with name " + teacherFirstName + " not found in the same school.");
		        }
		    }

		    student.setTeachers(newTeachers);
		    
		    return new StudentData(studentDao.save(student));
	}

	
	


}
