package educationhub.model;

import java.util.HashSet;
import java.util.Set;

import educationhub.entity.Student;
import educationhub.entity.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;

//Data Transfer Objects (DTOs) to represent and transfer data between the client and server. These DTOs are designed to avoid recursive loops and simplify the JSON representation when converted.
@Data
@NoArgsConstructor
public class TeacherData {

	private Long teacherId;
	private String teacher_first_name;
	private String teacher_last_name;
	private String email;
	private String teacher_subject;
	
	private Long schoolId; // Including the schoolId field instead of school object

	private Set<String> students = new HashSet<>();

	// we will use this construction to convert obj from JSON to JAVA AND from java
	// class to JSON
	// this constructor takes Teacher obj as a parameter
	public TeacherData(Teacher teacher) {
		teacherId = teacher.getTeacherId();
		teacher_first_name = teacher.getTeacher_first_name();
		teacher_last_name = teacher.getTeacher_last_name();
		email = teacher.getEmail();
		teacher_subject = teacher.getTeacher_subject();
		
        schoolId = teacher.getSchool().getSchoolId();

		for (Student student : teacher.getStudents()) {
			students.add(student.getStudent_first_name());
		}
	}
}
