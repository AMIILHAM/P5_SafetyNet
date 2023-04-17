package com.safetynet.alerts.controller;


import java.io.IOException;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	 @PostMapping("/person")
	    public void createPerson(
	             @RequestBody final Map<String, String> personToCreate, final HttpServletResponse response) {
	        Person personsCreated = personService.addPerson(personToCreate);

	        }
	    
	
	
	
	 @PutMapping("/person")
	    public void updatePerson( @RequestBody final Person personToUpdate,
	                             final HttpServletResponse response) {

		 boolean isUpdated = personService.updatePerson(personToUpdate);
	    }
	 
	        
	
	
	 @DeleteMapping("/person")
	    public void delete( @RequestBody final Person personToDelete,
	                             final HttpServletResponse response) throws JsonProcessingException, IOException {

	        boolean isDeleted = personService.delete(personToDelete);

	        
	        }
	    }
	
	
