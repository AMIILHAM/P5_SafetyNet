package com.safetynet.alerts.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.SafetyData;

@Service
public class PersonService {

	
	@Autowired
    private SafetyData safetyData;
	
	public Person addPerson(final Map<String, String> personToCreate) {
		
		

        
        Person newPerson = new Person(personToCreate.get("firstName"),
                personToCreate.get("lastName"),
                personToCreate.get("address"), personToCreate.get("city"),
                personToCreate.get("zip"),
                personToCreate.get("phone"),
                personToCreate.get("email"));
        List<Person> personsList = safetyData.getPersons();
        personsList.add(newPerson);
		return newPerson;
        
	}

	
	public boolean updatePerson( Person personToUpdate) {
		
		 List<Person> persons = safetyData.getPersons(); 
        boolean isUpdated = false;

        for (Person personList : persons) {
            if (personList.getFirstName().equals(personToUpdate.getFirstName())
                    && personList.getLastName()
                    .equals(personToUpdate.getLastName())) {
                personList.setAddress(personToUpdate.getAddress());
                personList.setCity(personToUpdate.getCity());
                personList.setZip(personToUpdate.getZip());
                personList.setPhone(personToUpdate.getPhone());
                personList.setEmail(personToUpdate.getEmail());
               
                isUpdated = true;
            }
        }
        safetyData.setPersons(persons);
        return isUpdated;
    }

	public boolean delete(Person personToDeleted) throws JsonProcessingException, IOException {

		List<Person> persons = safetyData.getPersons();
		if (persons == null) {
			persons = new ArrayList<>();
		}
		
		List<Person> newPersons = persons.stream() // java stream pour boucler sur une liste
		.filter(person -> !personToDeleted.getFirstName().equalsIgnoreCase(person.getFirstName()) // éliminer la personne qui match
		&& !personToDeleted.getLastName().equalsIgnoreCase(person.getLastName()))	
		.collect(Collectors.toList());
		
		safetyData.setPersons(newPersons); // remettre la liste des personnes modifiées dans l'objet safetyData
		
		
		
		return true;
	}
	}

