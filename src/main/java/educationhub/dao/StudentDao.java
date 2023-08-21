package educationhub.dao;




import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import educationhub.entity.Student;

public interface StudentDao extends JpaRepository<Student, Long> {

	Optional<Student> findByStudentEmail(String studentEmail);

	

	

}
