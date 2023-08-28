package educationhub.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import educationhub.entity.School;
import educationhub.model.SchoolData;

public class SchoolServiceTestSupport {

	private static final String SCHOOL_TABLE = "school";
	private static final String TEACHER_TABLE = "teacher";
	private static final String STUDENT_TABLE = "student";
	private static final String TEACHER_STUDENT_TABLE = "teacher_student";

	private static final String INSERT_TEACHER_1_SQL = """
			INSERT INTO teacher (teacherFirstName, teacherLastName, teacherEmail, teacherSubject, school_id)
			VALUES
			('Heather', 'West', 'heather@icon.org', 'English', 1)
							""";
	private static final String INSERT_TEACHER_2_SQL = """
			INSERT INTO teacher (teacherFirstName, teacherLastName, teacherEmail, teacherSubject, school_id)
			VALUES
			('Mark', 'Anderson', 'mark@icon.org', 'Science', 1)
						""";
	private static final String INSERT_STUDENTS_1_SQL = """
		INSERT INTO teacher_student (teacher_id, student_id)
		VALUES
		(1, 1),(1,2)
			""";
	private static final String INSERT_STUDENTS_2_SQL = """
		INSERT INTO teacher_student (teacher_id, student_id)
		VALUES
		(2, 3),(2,4)
			""";

	// @formatter:off
	//create school using constructor that we have in SchoolData
	private SchoolData insertSchool1 = new SchoolData(
			1L,
			"Jackson Middle School",
			"123 Brooklyn st",
			"Brooklyn area",
			"MN",
			"34562",
			"543-768-5674"
			);
	private SchoolData insertSchool2 = new SchoolData(
			2L,
			"Champlin Park High School",
			"6025 109th Ave N",
			"Champlin",
			"MN",
			"55316",
			"(763) 506-6800"
			);
	private SchoolData updateSchool1 = new SchoolData(
			1L,
			"Jackson Middle School",
			"789 Brooklyn st",
			"Brooklyn Bld",
			"MN",
			"34562",
			"543-768-5674"
			);
	// @formatter:on

	@Autowired
	private SchoolController schoolController;

	@Autowired
	private JdbcTemplate jdbcTemplate;// one way of using spring JDBC to get information by the tables and we use it
										// to see how many rows affected

	// Create school
	protected SchoolData insertSchool(SchoolData schoolData) {
		School school = schoolData.toSchool();
		SchoolData clone = new SchoolData(school);

		clone.setSchoolId(null);// remove id then compare the value
		return schoolController.insertSchool(clone);
	}

	protected SchoolData buildInsertSchool(int which) {

		return which == 1 ? insertSchool1 : insertSchool2;
	}

	protected int rowsInSchoolTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, SCHOOL_TABLE);
	}

	// Update school
	protected SchoolData updateSchool(SchoolData schoolData) {
		return schoolController.updateSchool(schoolData.getSchoolId(), schoolData);
	}

	protected SchoolData buildUpdateSchool(int i) {
		return updateSchool1;
	}

	// retrieve school
	protected SchoolData retriveSchool(Long schoolId) {
		return schoolController.getSchoolById(schoolId);
	}

	// Retrieve all schools
	protected List<SchoolData> retrieveAllSchools() {
		return schoolController.getAllSchools();
	}

	protected List<SchoolData> sorted(List<SchoolData> list) {
		List<SchoolData> data = new LinkedList<>();
		data.sort((sch1, sch2) -> (int) (sch1.getSchoolId() - sch2.getSchoolId()));
		return data;
	}

	protected List<SchoolData> insertTwoSchools() {
		SchoolData school1 = insertSchool(buildInsertSchool(1));
		SchoolData school2 = insertSchool(buildInsertSchool(2));

		return List.of(school1, school2);
	}

	// delete school
	protected void deleteSchool(Long schoolId) {
		schoolController.deleteSchoolById(schoolId);

	}

	protected int rowsInStudentTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, STUDENT_TABLE);

	}

	protected int rowsInTeacherStudentTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, TEACHER_STUDENT_TABLE);

	}

	protected int rowsInTeacherTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, TEACHER_TABLE);

	}

	protected void insertTeacher(int which) {
		String teacherSql = which == 1 ? INSERT_TEACHER_1_SQL : INSERT_TEACHER_2_SQL;
		String studentSql = which == 1 ? INSERT_STUDENTS_1_SQL : INSERT_STUDENTS_2_SQL;

		jdbcTemplate.update(teacherSql);
		jdbcTemplate.update(studentSql);

	}
}
