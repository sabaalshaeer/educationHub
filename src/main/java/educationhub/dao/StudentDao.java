package educationhub.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import educationhub.entity.Student;

public interface StudentDao extends JpaRepository<Student, Long> {

}
