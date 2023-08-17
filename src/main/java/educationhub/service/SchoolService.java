package educationhub.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import educationhub.model.SchoolData;

@Service
public class SchoolService {

	@Transactional(readOnly = false)
	public SchoolData insertSchool(SchoolData schoolData) {
		//check the school id if it is exists 
		return null;
	}

}
