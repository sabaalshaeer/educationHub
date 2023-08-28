package educationhub.model;

import java.util.HashSet;
import java.util.Set;

import educationhub.entity.Student;
import educationhub.entity.Teacher;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

//Data Transfer Objects (DTOs) to represent and transfer data between the client and server. These DTOs are designed to avoid recursive loops and simplify the JSON representation when converted.
@Data
@NoArgsConstructor
public class StudentData {

	@Column(name = "student_id")
	private Long studentId;
	@Column(name = "student_first_name")
	private String studentFirstName;
	@Column(name = "student_last_name")
	private String studentLastName;
	private int age;
	@Column(name = "student_email")
	private String studentEmail;
	private int grade;

	private Long schoolId; // Including the schoolId field

	// we change the field type to String instead of Teacher because we need to call
	// teacher first name which is type String
	private Set<String> teachers = new HashSet<>();

	// we will use this construction to convert obj from JSON to JAVA AND from java
	// class to JSON
	// this constructor takes Student obj as a parameter
	public StudentData(Student student) {
		studentId = student.getStudentId();
		studentFirstName = student.getStudentFirstName();
		studentLastName = student.getStudentLastName();
		age = student.getAge();
		studentEmail = student.getStudentEmail();
		grade = student.getGrade();
		
		schoolId = student.getSchool().getSchoolId();
		
		for (Teacher teacher : student.getTeachers()) {
			teachers.add(teacher.getTeacherFirstName());
		}
	}
}
