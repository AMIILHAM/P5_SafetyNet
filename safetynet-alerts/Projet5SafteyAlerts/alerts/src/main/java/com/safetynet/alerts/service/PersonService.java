package com.safetynet.alerts.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.alerts.DTO.ChildAlertDTO;
import com.safetynet.alerts.DTO.PersonInfoDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.SafetyData;
import com.safetynet.alerts.utils.DataSafety;

@Service
public class PersonService {

	
	@Autowired
    private DataSafety safetyData;
	
	public Person addPerson(final Map<String, String> personToCreate) {
		
		

        
        Person newPerson = new Person(personToCreate.get("firstName"),
                personToCreate.get("lastName"),
                personToCreate.get("address"), personToCreate.get("city"),
                personToCreate.get("zip"),
                personToCreate.get("phone"),
                personToCreate.get("email"));
        List<Person> persons = safetyData.getSafetyData().getPersons();
        persons.add(newPerson);
		return newPerson;
	}

	
	public boolean updatePerson( Person personToUpdate) {
		
		 List<Person> persons = safetyData.getSafetyData().getPersons(); 
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
        safetyData.getSafetyData().setPersons(persons);
        return isUpdated;
    }

	public boolean delete(Person personToDeleted) throws JsonProcessingException, IOException {

		List<Person> persons = safetyData.getSafetyData().getPersons();
		if (persons == null) {
			persons = new ArrayList<>();
		}
		
		List<Person> newPersons = persons.stream() // java stream pour boucler sur une liste
		.filter(person -> !personToDeleted.getFirstName().equalsIgnoreCase(person.getFirstName()) // éliminer la personne qui match
		&& !personToDeleted.getLastName().equalsIgnoreCase(person.getLastName()))	
		.collect(Collectors.toList());
		
		safetyData.getSafetyData().setPersons(newPersons); // remettre la liste des personnes modifiées dans l'objet safetyData
		
		
		
		return true;
	}
	/**
     *  les adresses mail de tous les habitants de la ville.
     *
     * @param city String
     * @return personsEmail List
     */
	public List<String> communityEmail(final String city) {

        List<String> personsEmail = new ArrayList<>();
        List<Person> personsList = safetyData.getSafetyData().getPersons();

        for (Person person : personsList) {
            if (person.getCity().equals(city)) {
                personsEmail.add(person.getEmail());
            }
        }
        return personsEmail;
    }
	
	/**
     * Return retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux de chaque habitant
     *
     * @param firstName String
     * @param lastName  String
     * @return personsInfosList List
     */
	public List<PersonInfoDTO> personInfo(final String firstName, final String lastName) {
       
        List<PersonInfoDTO> personsInfosList = new ArrayList<>();
        List<Person> personsList = safetyData.getSafetyData().getPersons();

        for (Person person : personsList) {
            if (person.getLastName().equals(lastName) ) {
                PersonInfoDTO personsInfos = new PersonInfoDTO(
                        person.getFirstName(), person.getLastName(),
                        person.getMedicalRecord().getAge(), person.getAddress(),
                        person.getCity(), person.getZip(), person.getEmail(),
                        person.getMedicalRecord().getMedications(),
                        person.getMedicalRecord().getAllergies());
                personsInfosList.add(personsInfos);
            }
        }
        
        return personsInfosList;
    }

public List<ChildAlertDTO> childAlert(final String address) {
    
        List<ChildAlertDTO> childAlert = new ArrayList<>();
        Map<String, List<Person>> foyers = SafetyData.getFoyers();
        boolean isWithChilds = false;

        for (Entry<String, List<Person>> entry : foyers.entrySet()) {
            String foyerAddress = entry.getKey();
            if (!address.equals(foyerAddress)) {
                continue;
            }
            List<Person> foyerMembersList = entry.getValue();
            for (Person person : foyerMembersList) {
                // si child
                if (AgeCalculatorService.isChild(person)) {
                    int childAge = AgeCalculatorService.calculateAge(person.getMedicalRecord().getBirthday());
                    ChildAlertDTO child = new ChildAlertDTO(childAge, person.getFirstName(), person.getLastName());
                    childAlert.add(child);
                    isWithChilds = true;

                    // si adulte
                } else if (!AgeCalculatorService.isChild(person)) {
                    int adultAge = AgeCalculatorService.calculateAge(
                            person.getMedicalRecord().getBirthday());
                    ChildAlertDTO adultMember = new ChildAlertDTO(adultAge,
                            person.getFirstName(), person.getLastName());
                    childAlert.add(adultMember);
                }
            }
        }
        // liste vide si pas d'enfants
        if (!isWithChilds) {
            childAlert.clear();
        }
        return childAlert;
    }
	}

