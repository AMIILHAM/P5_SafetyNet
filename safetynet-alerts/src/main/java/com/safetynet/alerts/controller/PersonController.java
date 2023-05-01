package com.safetynet.alerts.controller;


import java.io.IOException;

import java.util.List;
import java.util.Map;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javaparser.quality.NotNull;
import com.safetynet.alerts.DTO.ChildAlertDTO;
import com.safetynet.alerts.DTO.PersonInfoDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;


@RestController
@RequestMapping("/person")
public class PersonController {
	private static final Logger logger = LogManager.getLogger(PersonController.class);
	
	@Autowired
	private PersonService personService;
	
	 @PostMapping("/person")
	    public void createPerson(@RequestBody final Map<String, String> personToCreate) {
	        Person personsCreated = personService.addPerson(personToCreate);
	        if (personsCreated != null) {
	            logger.info("OK 201 - Create Person POST request for {} {}",
	                    personsCreated.getFirstName(),
	                    personsCreated.getLastName());
	            
	        } else {
	            logger.info("FAILED 409 - CONFLICT");
	        }

	        }
	    

	
	 @PutMapping("/person")
	    public void updatePerson( @RequestBody final Person personToUpdate) {

	
		boolean isUpdated = personService.updatePerson(personToUpdate);
		if (isUpdated) {
            logger.info("SUCCESS - UpdatePerson PUT request");
         
        } else {
            logger.error("404 - No person found : {} {}",
                    personToUpdate.getFirstName(),
                    personToUpdate.getLastName());
        
        }
	    }
	 
	        
	
	
	 @DeleteMapping("/person")
	    public void delete( @RequestBody final Person personToDelete) throws JsonProcessingException, IOException {

	        boolean isDeleted = personService.delete(personToDelete);

	        if (isDeleted) {
	            logger.info("OK 200 - DeletePerson DELETE request");
	        } else {
	            logger.error("404 - No person found : {} {}",
	                    personToDelete.getFirstName(),
	                    personToDelete.getLastName());
	        
	        }
	        }
	 
	 /**
	     * Return persons informations avec le meme  last name
	     *
	     * @param firstName String
	     * @param lastName  String
	     * @return personInfos list
	     */
	    @GetMapping("/personInfo")
	    public List<PersonInfoDTO> personInfo(@RequestParam final String firstName,
	                                          @NotNull @RequestParam final String lastName) {

	        logger.debug("GET request received for personInfos: {}", lastName);
	        List<PersonInfoDTO> personInfos = personService.personInfo(firstName, lastName);

	        if (!personInfos.isEmpty()) {
	            logger.info("OK 200 - personInfos GET request");
	        
	        } else {
	            logger.error("404 FAILED - No person found with LastName : {}", lastName);
	            
	        }
	        return personInfos;
	    }

	    /**
	     * Return la liste des personnes dans un foyer si l'adresse contient un child 
	     *
	     * @param address String
	     * @return childAlert
	     */
	    @GetMapping("/childAlert")
	    public List<ChildAlertDTO> getChildAlert(
	            @NotNull @RequestParam(value = "address") final String address) {

	        logger.debug("GET request received for getChildAlert : {}", address);
	        List<ChildAlertDTO> childAlert = personService.childAlert(address);

	        if (!childAlert.isEmpty()) {
	            logger.info("OK 200 - ChildAlert GET request");
	           
	        } else {
	            logger.error("404 FAILED - No household with child found : {}", address);
	           
	        }
	        return childAlert;
	    }
	    /**
	     * Return les emails des personnes habitant dans city entrée
	     *
	     * @param city String
	     * @return List of communityEmail
	     */
	    @GetMapping("/communityEmail")
	    public List<String> getCommunityEmail(
	            @NotNull @RequestParam(value = "city") final String city) {

	        List<String> communityEmail = personService.communityEmail(city);

	        if (!communityEmail.isEmpty()) {
	            logger.info("OK 200 - CommunityEmail GET request");
	            
	        } else {
	            logger.error("404 FAILED - Not found : {}", city);
	            String notFounded = "Not found : " + city;
	            communityEmail.add(notFounded);
	           
	        }
	        return communityEmail;
	    }

	    }
	
	
