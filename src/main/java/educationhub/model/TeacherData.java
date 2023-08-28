package educationhub.model;

import java.util.HashSet;
import java.util.Set;

import educationhub.entity.Student;
import educationhub.entity.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Data Transfer Objects (DTOs) to represent and transfer data between the
		// client and server. These DTOs are designed to avoid recursive loops and
		// simplify the JSON representation when converted.

@NoArgsConstructor
public class TeacherData {

	private Long teacherId;
	private String teacherFirstName;
	private String teacherLastName;
	private String teacherEmail;
	private String teacherSubject;

	private Long schoolId; // Including the schoolId field instead of school object

	private Set<String> students = new HashSet<>();

	// we will use this construction to convert obj from JSON to JAVA AND from java
	// class to JSON
	// this constructor takes Teacher obj as a parameter
	public TeacherData(Teacher teacher) {
		teacherId = teacher.getTeacherId();
		teacherFirstName = teacher.getTeacherFirstName();
		teacherLastName = teacher.getTeacherLastName();
		teacherEmail = teacher.getTeacherEmail();
		teacherSubject = teacher.getTeacherSubject();

		schoolId = teacher.getSchool().getSchoolId();

		for (Student student : teacher.getStudents()) {
			students.add(student.getStudentFirstName());
		}
	}
	//Use constructor in the test level
	//constructor takes teacher variables to use it in the test to create teacher
//	public TeacherData(
//			Long teacherId, 
//			String teacherFirstName, 
//			String teacherLastName, 
//			String teacherEmail,
//			String teacherSubject) {
//		this.teacherId = teacherId;
//		this.teacherFirstName = teacherFirstName;
//		this.teacherLastName = teacherLastName;
//		this.teacherEmail = teacherEmail;
//		this.teacherSubject = teacherSubject;
//
//	}

	// for test convert teacher object back to the teacherData object
//	public Teacher toTeacher() {
//		Teacher teacher = new Teacher();
//		// set Fields
//		teacher.setTeacherId(teacherId);
//		teacher.setTeacherFirstName(teacherFirstName);
//		teacher.setTeacherLastName(teacherLastName);
//		teacher.setTeacherEmail(teacherEmail);
//		teacher.setTeacherSubject(teacherSubject);
//
//		return teacher;
//	}
}
