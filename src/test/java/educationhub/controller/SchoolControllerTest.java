package educationhub.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import educationhub.EducationHubApplication;
import educationhub.model.SchoolData;


@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = EducationHubApplication.class)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@SqlConfig(encoding = "utf-8")
class SchoolControllerTest extends SchoolServiceTestSupport{

	//test to insert School
	@Test
	void testinsertSchool() {
		//Given: A school request (this what we will build)
		SchoolData request = buildInsertSchool(1);
		SchoolData expected = buildInsertSchool(1);
		
		//When: the school is added to the school table(that's what actually inserting into the table)
		SchoolData actual = insertSchool(request);
		
		//then :the school returned is what is expected
		assertThat(actual).isEqualTo(expected);

		//And there is one row in the school table
		assertThat(rowsInSchoolTable()).isOne();

	}

	//test to update school
	@Test 
	void testUpdateSchool(){
		//Given: a school and an update request
		insertSchool(buildInsertSchool(1));
		SchoolData expected = buildUpdateSchool(1);
		
		//when: the school is updated
		SchoolData actual = updateSchool(expected);
		
		//Then: the school is returned as expected
		assertThat(actual).isEqualTo(expected);

		//And : there is  one row in the teacher table
		assertThat(rowsInSchoolTable()).isOne();

	}
	
	//test to retrieve school by id
	@Test
	void testRetrieveSchool() {
		//Given: A School  
		SchoolData school = insertSchool(buildInsertSchool(1));
		SchoolData expected = buildInsertSchool(1);

		//When: the School is retrieve by School Id
		SchoolData actual = retriveSchool(school.getSchoolId());
		
		//then :the actual location is equal to the expected location
		assertThat(actual).isEqualTo(expected);
		
	}
	


	//test to retrieve all schools
	@Test
	void testRetrieveAllchhols() {
		//Given: Two Schools  
		List<SchoolData> expected = insertTwoSchools();
		
		//When: all schools are retrieved
		List<SchoolData> actual = retrieveAllSchools();
		
		//then :the retrieved schools equal to the expected schools with sorting 
		assertThat(sorted(actual)).isEqualTo(sorted(expected));
		
	}
	
	
	

	//test to delete school
	@Test 
	void deleteSchoolwithTeachers(){
		//Given: a school and two teachers
		SchoolData school = insertSchool(buildInsertSchool(1));
		Long schoolId = school.getSchoolId();
		
		insertTeacher(1);
		insertTeacher(2);
		
		assertThat(rowsInSchoolTable()).isOne();
		assertThat(rowsInTeacherTable()).isEqualTo(2);
		assertThat(rowsInTeacherStudentTable()).isEqualTo(4);
		int studentRows = rowsInStudentTable();

		//when: the school is deleted
		deleteSchool(schoolId);
		
		//Then: there are no school, teacher, or teacher_student rows
		assertThat(rowsInSchoolTable()).isZero();
		assertThat(rowsInTeacherTable()).isZero();
		assertThat(rowsInTeacherStudentTable()).isZero();

		//And : the number of breed rows has no changed
		assertThat(rowsInStudentTable()).isEqualTo(studentRows);

	}

	

	
//	@Test
//	void testinsertTeacher() {
//		//Given: A teacher request (this what we will build)
//		TeacherData request = buildInsertTeacher(1);
//		TeacherData expected = buildInsertTeacher(1);
//		
//		//When: the teacher is added to the Teacher table(that's what actually inserting into the table)
//		TeacherData actual = insertTeacher(request);
//		
//		//then :the teacher returned is what is expected
//		assertThat(actual).isEqualTo(expected);
//
//		//And there is one row in the teacher table
//		assertThat(rowsInTeacherTable()).isOne();
//
//	}
//
//	@Test 
//	void testUpdateTeacher(){
//		//Given: a teacher and an update request
//		insertTeacher(buildInsertTeacher(1));
//		TeacherData expected = buildUpdateTeacher();
//		
//		//when: the teacher is updated
//		TeacherData actual = updateTeacher(expected);
//		
//		//Then: the teacher is returned as expected
//		assertThat(actual).isEqualTo(expected);
//
//		//And : there is  one row in the teacher table
//		assertThat(rowsInTeacherTable()).isOne();
//
//	}

	

	

}
