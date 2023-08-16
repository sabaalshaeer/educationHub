package educationhub.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
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
}
