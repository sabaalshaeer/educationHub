package educationhub.model;

import java.util.HashSet;
import java.util.Set;

import educationhub.entity.School;
import educationhub.entity.Student;
import educationhub.entity.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentData {

	private Long studentId;
	private String student_first_name;
	private String student_last_name;
	private int age;
	private int grade;

	private School school;

	//we change the field type to String instead of Teacher because we need to call teacher first name which is type String
	private Set<String> teachers = new HashSet<>();
	
	//we will use this construction to convert obj from JSON to JAVA AND from java class to JSON
	public StudentData(Student student) {
		studentId = student.getStudentId();
		student_first_name = student.getStudent_first_name();
		student_last_name= student.getStudent_last_name();
		age = student.getAge();
		grade = student.getGrade();
		
		for(Teacher teacher : student.getTeachers()) {
			teachers.add(teacher.getTeacher_first_name());
		}
	}
}
