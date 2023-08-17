package educationhub.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import educationhub.entity.Teacher;

public interface TeacherDao extends JpaRepository<Teacher, Long> {

}
