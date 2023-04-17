package com.safetynet.alerts.service;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.SafetyData;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;


	/**
	 * PersonService units tests class.
	 */
	@WebMvcTest(PersonService.class)
	@ExtendWith(MockitoExtension.class)
	class PersonServiceTest {

	    @InjectMocks
	    private PersonService personService;

	    @Mock
	    private SafetyData safetyData;

	
	    private static Person person1;
	    private static Person person2;
	  


	@Test
    @Tag("POST")
    @DisplayName("add Person")
    void addPersonTest() throws IOException {
		Map<String, String> personToCreate = new HashMap<String, String>();
        Person newPerson = new Person("New", "Person", "address", "City", "zip",
                "phone", "email");
        personToCreate.put("firstName", newPerson.getFirstName());
        personToCreate.put("lastName", newPerson.getLastName());
        personToCreate.put("address", newPerson.getAddress());
        personToCreate.put("city", newPerson.getCity());
        personToCreate.put("zip", newPerson.getZip());
        personToCreate.put("phone", newPerson.getPhone());
        personToCreate.put("email", newPerson.getEmail());

        List<Person> personsList = new ArrayList<>();
        when(safetyData.getPersons()).thenReturn(personsList);
        
  
        Person result = personService.addPerson(personToCreate);
                 assertThat(result).hasFieldOrPropertyWithValue("firstName", newPerson.getFirstName())
                .hasFieldOrPropertyWithValue("lastName", newPerson.getLastName())
                .hasFieldOrPropertyWithValue("address", newPerson.getAddress())
                .hasFieldOrPropertyWithValue("city", newPerson.getCity())
                .hasFieldOrPropertyWithValue("zip", newPerson.getZip())
                .hasFieldOrPropertyWithValue("phone", newPerson.getPhone())
                .hasFieldOrPropertyWithValue("email", newPerson.getEmail());
        assertThat(personsList.contains(result)).isTrue();
        
    }
	 @Test
	    @Tag("PUT")
	    @DisplayName("UPDATE  Person")
	    void updateExistingPerson() {
	        List<Person> personsList = new ArrayList<>();
	        person1 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451",
	                "841-874-6512", "jaboyd@email.com");
	        personsList.add(person1);
	        when(safetyData.getPersons()).thenReturn(personsList);
	        Person personToUpdate = new Person("John", "Boyd", "NEW ADDRESS", "city",
	                "zip", "phone", "email");
	        personService.updatePerson(personToUpdate);
	        assertThat(personsList.size()).isEqualTo(1);
	    }
	 @Test
	    @Tag("DELETE")
	    @DisplayName(" Delete Person")
	    void deleteExistingPerson() throws JsonProcessingException, IOException {
	        List<Person> personsList = new ArrayList<>();
	        person1 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451",
	                "841-874-6512", "jaboyd@email.com");
	        person2 = new Person("Jacob", "Tenley", "1509 Culver St", "Culver",
	                "97451", "841-874-6513", "drk@email.com");
	        personsList.add(person1);
	        personsList.add(person2);
	        when(safetyData.getPersons()).thenReturn(personsList);
	        Person personToDelete = person1;
	        personService.delete(personToDelete);
	        assertThat(personsList.size()).isEqualTo(1); // verifier la taille de la liste
	    }
	 }
