package com.safetynet.alerts.service;


import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.alerts.DTO.ChildAlertDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.SafetyData;
import com.safetynet.alerts.utils.DataSafety;

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
	    private DataSafety safetyData;

	    private static final String adultBirthday = "03/23/1993";
	    private static final String childBirthday = "01/01/2020";
	    private static MedicalRecord medicalRecordAdult;
	    private static MedicalRecord medicalRecordChild;
	    private static Person person1;
	    private static Person person2;
	    private static Person person3;
	    
	    @BeforeEach
	    private void setUpPerTest() {
	        List<String> medicationsList = new ArrayList<>();
	        medicationsList.add("medication1");
	        List<String> allergiesList = new ArrayList<>();
	        allergiesList.add("allergies");
	        medicalRecordAdult = new MedicalRecord(adultBirthday, medicationsList, allergiesList);
	        medicalRecordChild = new MedicalRecord(childBirthday, medicationsList, allergiesList);
	    }

	  


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
        when(safetyData.getSafetyData().getPersons()).thenReturn(personsList);
        
  
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
	        when(safetyData.getSafetyData().getPersons()).thenReturn(personsList);
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
	        when(safetyData.getSafetyData().getPersons()).thenReturn(personsList);
	        Person personToDelete = person1;
	        personService.delete(personToDelete);
	        assertThat(personsList.size()).isEqualTo(1); // verifier la taille de la liste
	    }
	 @Test
	    @Tag("CommunityEmail")
	    @DisplayName("Valid City entry - CommunityEmail")
	    void validCityEntryCommunityEmail() {
	        List<Person> personsList = new ArrayList<>();
	        person1 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451",
	                "841-874-6512", "jaboyd@email.com", medicalRecordAdult);
	        person2 = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451",
	                "841-874-6513", "drk@email.com", medicalRecordAdult);
	        person3 = new Person("Eric", "Cadigan", "951 LoneTree Rd", "Culver",
	                "97451", "841-874-7458", "gramps@email.com",
	                medicalRecordAdult);
	        Person otherCityPerson = new Person("Other", "Unknow", "Other city",
	                "Other city", "00000", "111-111-111", "other@email.com");
	        personsList.add(person1);
	        personsList.add(person2);
	        personsList.add(person3);
	        personsList.add(otherCityPerson);

	        when(safetyData.getSafetyData().getPersons()).thenReturn(personsList);
	        List<String> personsEmails = personService.communityEmail("Culver");
	        assertThat(personsEmails.size()).isEqualTo(3);
	        assertThat(personsEmails.get(0)).isEqualTo("jaboyd@email.com");
	        assertThat(personsEmails.get(1)).isEqualTo("drk@email.com");
	        assertThat(personsEmails.get(2)).isEqualTo("gramps@email.com");
	    }
	 @Test
	    @Tag("ChildAlert")
	    @DisplayName("ChildAlert Childs & Adults with Same address")
	    void childAlertChildsAndAdultsWithSameAddress() {

	        // child 1 address ok
	        person1 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451",
	                "841-874-6512", "jaboyd@email.com", medicalRecordChild);
	        // adult address ok
	        person2 = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451",
	                "841-874-6513", "drk@email.com", medicalRecordAdult);
	        // child 2 incorrect address
	        person3 = new Person("Eric", "Cadigan", "951 LoneTree Rd", "Culver",
	                "97451", "841-874-7458", "gramps@email.com",
	                medicalRecordChild);

	        // ajout meme personne dans foyer
	        List<Person> foyerMembersList = new ArrayList<>();
	        foyerMembersList.add(person1);
	        foyerMembersList.add(person2);

	        Map<String, List<Person>> foyers = new HashMap<>();
	        foyers.put(person1.getAddress(), foyerMembersList);
	        foyers.put(person2.getAddress(), foyerMembersList  );
	        foyers.put(person3.getAddress(), foyerMembersList);
	        safetyData.getSafetyData();
			when(SafetyData.getFoyers()).thenReturn(foyers);
	        List<ChildAlertDTO> result = personService.childAlert("1509 Culver St");
	        assertThat(result.size()).isEqualTo(2);
	        assertThat(result.isEmpty()).isFalse();
	    }
	 @Test
	    @Tag("ChildAlert")
	    @DisplayName("ChildAlert Unknown Address")
	    void childAlertUnknownAddress() {
	        person1 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451",
	                "841-874-6512", "jaboyd@email.com", medicalRecordChild);

	        person2 = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451",
	                "841-874-6513", "drk@email.com", medicalRecordAdult);

	        List<Person> foyerMembersList = new ArrayList<>();
	        foyerMembersList.add(person1);
	        foyerMembersList.add(person2);
	        Map<String, List<Person>> foyers = new HashMap<>();
	        foyers.put(person1.getAddress(), foyerMembersList);
	        foyers.put(person2.getAddress(), foyerMembersList  );
	        safetyData.getSafetyData();
			when(SafetyData.getFoyers()).thenReturn(foyers);
	        List<ChildAlertDTO> result = personService.childAlert("Unknow address");
	        assertThat(result.size()).isZero();
	        assertThat(result.isEmpty()).isTrue();
	    }
	 }
