package com.recruit.daos;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.recruit.entities.Offer;

@Repository
public interface OfferDao extends CrudRepository<Offer, Integer> {
	Optional<Offer> findByjobTitle(String title);
}
