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
public class TeacherData {

	private Long teacherId;
	private String teacher_first_name;
	private String teacher_last_name;
	private String email;
	private String teacher_subject;

	private School school;

	private Set<String> students = new HashSet<>();
	
	//we will use this construction to convert obj from JSON to JAVA AND from java class to JSON

	public TeacherData(Teacher teacher) {
		teacherId = teacher.getTeacherId();
		teacher_first_name = teacher.getTeacher_first_name();
		teacher_last_name = teacher.getTeacher_last_name();
		email = teacher.getEmail();
		teacher_subject = teacher.getTeacher_subject();
		
		for(Student student : teacher.getStudents()) {
			students.add(student.getStudent_first_name());
		}
	}
}
