package educationhub.model;

import java.util.HashSet;
import java.util.Set;

import educationhub.entity.School;
import educationhub.entity.Student;
import educationhub.entity.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;

//Data Transfer Objects (DTOs) to represent and transfer data between the client and server. These DTOs are designed to avoid recursive loops and simplify the JSON representation when converted.

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

	// we will use this construction to convert obj from JSON to JAVA AND from java
	// class to JSON
	// this constructor takes School obj as a parameter
	public SchoolData(School school) {
		schoolId = school.getSchoolId();
		schoolName = school.getSchoolName();
		schoolAddress = school.getSchoolAddress();
		schoolCity = school.getSchoolCity();
		schoolState = school.getSchoolState();
		schoolZip = school.getSchoolZip();
		schoolPhone = school.getSchoolPhone();

		for (Teacher teacher : school.getTeachers()) {
			teachers.add(new TeacherData(teacher));
		}

		for (Student student : school.getStudents()) {
			students.add(new StudentData(student));
		}
	}
	
	//Use constructor in the test level
		//constructor takes school variables to use it in the test to create school
		public SchoolData(
				Long schoolId, 
				String schoolName, 
				String schoolAddress, 
				String schoolCity,
				String schoolState,
				String schoolZip,
				String schoolPhone) {
			this.schoolId = schoolId;
			this.schoolName = schoolName;
			this.schoolAddress = schoolAddress;
			this.schoolCity = schoolCity;
			this.schoolState = schoolState;
			this.schoolZip = schoolZip;
			this.schoolPhone = schoolPhone;

		}

		// for test convert school object back to the schoolData object
		public School toSchool() {
			School school = new School();
			// set Fields
			school.setSchoolId(schoolId);
			school.setSchoolName(schoolName);
			school.setSchoolAddress(schoolAddress);
			school.setSchoolCity(schoolCity);
			school.setSchoolState(schoolState);
			school.setSchoolZip(schoolZip);
			school.setSchoolPhone(schoolPhone);

			return school;
		}
}
