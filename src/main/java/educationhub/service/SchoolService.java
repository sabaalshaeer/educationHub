package educationhub.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import educationhub.dao.SchoolDao;
import educationhub.entity.School;
import educationhub.model.SchoolData;

@Service
public class SchoolService {

	@Autowired
	private SchoolDao schoolDao;

	@Transactional(readOnly = false)
	public SchoolData saveSchool(SchoolData schoolData) {
		// get the school id if it is exists
		Long schoolId = schoolData.getSchoolId();
		School school = findOrCreateSchool(schoolId);
		
		//call method
		setFieldsInSchool(school, schoolData);
		
		//save the school
		return new SchoolData(schoolDao.save(school));
		
	}

	//set all the instance variables in the this school obj(school) to what passed from the JSON (schoolData)
	private void setFieldsInSchool(School school, SchoolData schoolData) {
		school.setSchoolName(schoolData.getSchoolName());
		school.setSchoolAddress(schoolData.getSchoolAddress());
		school.setSchoolCity(schoolData.getSchoolCity());
		school.setSchoolState(schoolData.getSchoolState());
		school.setSchoolZip(schoolData.getSchoolZip());
		school.setSchoolPhone(schoolData.getSchoolPhone());
	}

	private School findOrCreateSchool(Long schoolId) {
		// declare school entity object
		School school;
		// to create new school when the school id is null
		if (Objects.isNull(schoolId)) {
			school = new School();
		} // to modify school so school id is exists
		else {
			school = findSchoolById(schoolId);
		}
		
		return school;
	}

	public School findSchoolById(Long schoolId) {
		// findById method return optional so to convert from optional to actual type
		// obj we use orElseThrow
		// so if optional was empty (school with that Id) then it will throw exception ,
		// if it found it will return the school with that id
		return schoolDao.findById(schoolId)
				.orElseThrow(() -> new NoSuchElementException("School with Id=" + schoolId + " not found"));
	}

	@Transactional(readOnly = true)
	public List<SchoolData> retrieveAllSchools() {
		//fetch all school objects by calling findAll method in schoolDao
		List<School> schools = schoolDao.findAll();
		List<SchoolData> ListOfSchoolsData = new LinkedList<>();
		
		for(School school : schools) {
			SchoolData schoolData = new SchoolData(school);//will convert school obj to schoolData
			schoolData.getTeachers().clear();//Don't get teachers associate with 
			schoolData.getStudents().clear();//Don't get students associate with
			
			//add schoolData to the ListOfSchoolData
			ListOfSchoolsData.add(schoolData);
		}
		return ListOfSchoolsData;
	}

	@Transactional(readOnly = true)
	public SchoolData retrieveSchoolWithId(Long schoolId) {
		//Retrieve the school with the id by calling findSchoolById method
		School schoolWithId = findSchoolById(schoolId);
		return new SchoolData(schoolWithId);
	}

	@Transactional(readOnly = false)
	public void deleteSchoolWithId(Long schoolId) {
		//Retrieve the school with the id by calling findSchoolById method
		School schoolWithId = findSchoolById(schoolId);
		schoolDao.delete(schoolWithId);
		
	}

}
