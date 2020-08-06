package com.recruit.controllers;

import java.net.URI;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.recruit.daos.OfferDao;
import com.recruit.entities.Offer;
import com.recruit.exception.JobOfferNotFoundException;
import com.recruit.exception.JobTitleExistException;

@RestController
@RequestMapping("/offer")
public class OfferController {

	@Autowired
	private OfferDao dao;

	@RequestMapping(value="/", method=RequestMethod.GET,
			produces="application/json")
	public ResponseEntity<Offer> getAll() {
		dao.findAll();
		return new ResponseEntity<Offer>(HttpStatus.OK);
	}

	@RequestMapping(value="/jobtitle/{title}", method=RequestMethod.GET,
			produces="application/json")
	public ResponseEntity<Offer> getByJobTitle(@PathVariable  String title) throws Exception {
		Optional<Offer> opt = dao.findByjobTitle(title);
		return opt.map(offer -> new ResponseEntity<Offer>(HttpStatus.OK))
				.orElseThrow(() -> new JobOfferNotFoundException("Not Found-"+title));
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET,
			produces="application/json")
	public ResponseEntity<Offer> getById(@PathVariable  int id) throws Exception {
		Optional<Offer> opt = dao.findById(id);
		return opt.map(offer -> new ResponseEntity<Offer>(HttpStatus.OK))
				.orElseThrow(() -> new JobOfferNotFoundException("Not Found-"+
						id));
	}

	@RequestMapping(value="/jobtitle", method=RequestMethod.POST,
			consumes="application/json",
			produces="text/plain")
	public ResponseEntity<Offer> save(@Valid@RequestBody Offer offer) {
		Offer saveOffer = dao.save(offer);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saveOffer.getId()).toUri();
		return  ResponseEntity.created(location).build();
	}

	@RequestMapping(value="/", method=RequestMethod.POST,
			consumes="application/json",
			produces="text/plain")
	public ResponseEntity<Offer> saveByJobTitle (@RequestBody Offer offer) {
		Optional<Offer> opt = dao.findByjobTitle(offer.getJobTitle());
		if(opt.isPresent()) {
			throw new JobTitleExistException("Already present-"+offer.getJobTitle());
		}
		Offer saveOffer = dao.save(offer);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saveOffer.getId()).toUri();
		return  ResponseEntity.created(location).build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT,
			consumes="application/json",
			produces="text/plain")
	public ResponseEntity<Offer> update(@PathVariable int id, @RequestBody Offer offer) 
	{
		Optional<Offer> opt = dao.findById(id);
		return opt.map(offerUpdate->{dao.save(offerUpdate);
		return new ResponseEntity<Offer>(HttpStatus.OK);})
				.orElseThrow(()-> new JobOfferNotFoundException("Not Found-"+id));		
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE,
			produces="text/plain")
	public ResponseEntity<Offer> remove(@PathVariable int id) 
	{
		Optional<Offer> opt = dao.findById(id);

		return opt.map(offer-> {dao.delete(offer);
		return new ResponseEntity<Offer>(HttpStatus.OK);})
				.orElseThrow(()-> new JobOfferNotFoundException("Not Found-"+id));
	}
}
