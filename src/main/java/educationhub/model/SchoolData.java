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
public class SchoolData {

	private Long schoolId;
	private String schoolName;
	private String schoolAddress;
	private String schoolCity;
	private String schoolState;
	private String schoolZip;
	private String schoolPhone;
	private Set<TeacherData> teachers = new HashSet<>();
	private Set<StudentData> students = new HashSet<>();
	
	
	//we will use this construction to convert obj from JSON to JAVA AND from java class to JSON
	public SchoolData(School school) {
		schoolId = school.getSchoolId();
		schoolName = school.getSchoolName();
		schoolAddress = school.getSchoolAddress();
		schoolCity = school.getSchoolCity();
		schoolState = school.getSchoolState();
		schoolZip = school.getSchoolZip();
		schoolPhone = school.getSchoolPhone();
		
		for(Teacher teacher : school.getTeachers()) {
			teachers.add(new TeacherData(teacher));
		}
		
		for(Student student : school.getStudents()) {
			students.add(new StudentData(student));
		}
	}
}
