package eductionhub.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import educationhub.entity.School;

public interface SchoolDao extends JpaRepository<School, Long>{

}
