package com.safetynet.alerts.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.SafetyData;

@Component

public class DataSafety {

	    private static int personCounter = 0;
	    private static int fireStationCounter = 0;
	    private static int medicalRecordCounter = 0;
	    
	    private DataSafety() {
	    	
	    
	    }

		public static  SafetyData readJsonFile( String dataFile)
	            throws IOException, NullPointerException {
	        byte[] byteArray = Files.readAllBytes(new File(dataFile).toPath());
	        JsonIterator  jsonIterator = JsonIterator.parse(byteArray);
	        Any any = jsonIterator.readAny();

	        // Persons
	        Any anyPerson = any.get("persons");
	        List<Person> persons = new ArrayList<>();
	        Map<String, List<Person>> households = new HashMap<>();

	        anyPerson.forEach( personJson -> {
	            String firstName = personJson.get("firstName").toString();
	            String lastName = personJson.get("lastName").toString();
	            String address = personJson.get("address").toString();
	            String city = personJson.get("city").toString();
	            String zip = personJson.get("zip").toString();
	            String phone = personJson.get("phone").toString();
	            String email = personJson.get("email").toString();

	            Person person = new Person(firstName, lastName, address, city, zip, phone, email);
	            persons.add(person);
	            personCounter++;

	           
	        // FireStations
	        Any anyFireStation = any.get("firestations");
	        Map<String, Firestation> firestations = new HashMap<>();
	        anyFireStation.forEach(firestationJson -> {
	            String id = firestationJson.get("station").toString();
	            String address = firestationJson.get("address").toString();

	            FireStation firestation = firestations.computeIfAbsent(id, FireStation::new);
	            firestation.addAddress(address);
	            fireStationCounter++;
	        });

	        // MedicalRecord
	        Any anyMedicalRecord = any.get("medicalrecords");
	        anyMedicalRecord.forEach(medicalRecordJson -> {
	            String firstName = medicalRecordJson.get("firstName").toString();
	            String lastName = medicalRecordJson.get("lastName").toString();
	            String birthdate = medicalRecordJson.get("birthdate").toString();

	            List<String> medications = new ArrayList<>();
	            Any anyMedications = medicalRecordJson.get("medications");
	            anyMedications.forEach(medicationJson -> medications.add(medicationJson.toString()));

	            List<String> allergies = new ArrayList<>();
	            Any anyAllergies = medicalRecordJson.get("allergies");
	            anyAllergies.forEach(allergyJson -> allergies.add(allergyJson.toString()));

	            // MedicalRecord aux bonnes personnes
	            searchPerson(firstName, lastName, persons).setMedicalRecord(
	                    new MedicalRecord(birthdate, medications, allergies));
	            medicalRecordCounter++;
	        });
	        logger.debug("Loaded from a Json file : \r\n"
	                + (personCounter) + " persons \r\n"
	                + (fireStationCounter)
	                + " firestations \r\n"
	                + (medicalRecordCounter)
	                + " medicalrecords");

	        return new SafetyData(persons, firestations, households);
	        		
}
