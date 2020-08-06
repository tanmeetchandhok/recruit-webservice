package com.recruit.daos;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.recruit.entities.Applications;

@Repository
public interface ApplicationDao extends 
	CrudRepository<Applications, Integer> {
	Optional<Applications> findByemail(String email);
}
