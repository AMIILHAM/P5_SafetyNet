package com.safetynet.alerts.controller;


import java.io.IOException;
import java.util.List;

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
@RequestMapping("person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@GetMapping("findAll")
	public List<Person> findAll() {
		return personService.findAll();
	}
	
	@GetMapping("findOne")
	public Person findOne(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
		return personService.findOne(firstName,  lastName);
	}
	//methode permet de faire le update à une personne 
	@PutMapping("/person/{lastName}&{firstName}")
    public boolean updatePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) throws IOException {
        Person person = personService.findOne(firstName, lastName);


        person.setAddress(person.getAddress());
        person.setCity(person.getCity());
        person.setEmail(person.getEmail());
        person.setPhone(person.getPhone());
        person.setZip(person.getZip());
     
 
        final boolean updatedPerson = personService.add(person);
        return updatedPerson;
    }
	@PostMapping("add")
	public boolean add(@RequestBody Person person) throws IOException {
		return personService.add(person);
	}
	
	@DeleteMapping("delete")
	public boolean delete(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) throws JsonProcessingException, IOException {
		return personService.delete(firstName,  lastName);
	}
	
	
}