package com.recruit.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.recruit.daos.ApplicationDao;
import com.recruit.entities.Applications;
import com.recruit.exception.EmailNotFoundException;

@RestController
@RequestMapping("/application")
public class ApplicationController {

	@Autowired
	private ApplicationDao dao;
	
	@RequestMapping(value="/", method=RequestMethod.GET,
			produces="application/json")
	public ResponseEntity<Applications> getAll() {
		  dao.findAll();
		return new ResponseEntity<Applications>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/{email}", method=RequestMethod.GET,
			produces="application/json")
	public ResponseEntity<Applications> getByCandidateEmail(@PathVariable  String email) throws Exception {
		Optional<Applications> opt = dao.findByemail(email);
		
		return opt.map(application -> new ResponseEntity<Applications>(HttpStatus.OK))
		        .orElseThrow(() -> new EmailNotFoundException("Not Found-"+email));					
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET,
			produces="application/json")
	public ResponseEntity<Applications> getById(@PathVariable  int id) throws Exception {
		Optional<Applications> opt = dao.findById(id);
		
		return opt.map(application -> new ResponseEntity<Applications>(HttpStatus.OK))
		        .orElseThrow(() -> new EmailNotFoundException("Not Found-"+
		id));			
	}
}
