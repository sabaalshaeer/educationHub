package educationhub.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import educationhub.entity.School;
import educationhub.entity.Teacher;



public interface TeacherDao extends JpaRepository<Teacher, Long> {

	Optional<Teacher> findByTeacherEmail(String teacherEmail);

	Teacher findByTeacherFirstNameAndSchool(String teacherFirstName, School school);

}
