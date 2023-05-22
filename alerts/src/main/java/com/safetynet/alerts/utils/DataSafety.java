package com.safetynet.alerts.utils;

import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.SafetyData;

@Service

public class DataSafety {

	private static int personCounter = 0;
	private static int fireStationCounter = 0;
	private static int medicalRecordCounter = 0;
	private static SafetyData safetyData;
	List<Person> foyer;

	public DataSafety() {
	}

	@PostConstruct
	public void init() {
		try {
			this.readJsonFile();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void readJsonFile()
			throws IOException, NullPointerException {
		ClassPathResource res = new ClassPathResource("safetyAlertsData.json");
		File file = res.getFile();
		byte[] byteArray = Files.readAllBytes(file.toPath());
		JsonIterator jsonIterator = JsonIterator.parse(byteArray);
		Any any = jsonIterator.readAny();

		// Persons
		Any anyPerson = any.get("persons");
		List<Person> persons = new ArrayList<>();
		Map<String, List<Person>> foyers = new HashMap<>();

		for (Any personJson : anyPerson) {
			String firstName = personJson.get("firstName").toString();
			String lastName = personJson.get("lastName").toString();
			String address = personJson.get("address").toString();
			String city = personJson.get("city").toString();
			String zip = personJson.get("zip").toString();
			String phone = personJson.get("phone").toString();
			String email = personJson.get("email").toString();

			Person person = new Person(firstName, lastName, address, city, zip, phone, email);
			persons.add(person);
			setPersonCounter(getPersonCounter() + 1);

			// cache, remplace containskey ID, plus lisible
			List<Person> foyer = foyers.computeIfAbsent(address, temp -> new ArrayList<>());
			foyer.add(person);
		}

		// FireStations
		Any anyFirestation = any.get("firestations");
		Map<String, Firestation> firestations = new HashMap<>();
		for (Any firestationJson : anyFirestation) {
			String id = firestationJson.get("station").toString();
			String address = firestationJson.get("address").toString();

			Firestation firestation = firestations.computeIfAbsent(id, Firestation::new);
			firestation.addAddress(address);
			setFireStationCounter(getFireStationCounter() + 1);
		}

		// MedicalRecord
		Any anyMedicalRecord = any.get("medicalrecords");
		for (Any medicalRecordJson : anyMedicalRecord) {
			String firstName = medicalRecordJson.get("firstName").toString();
			String lastName = medicalRecordJson.get("lastName").toString();
			String birthday = medicalRecordJson.get("birthdate").toString();



			List<String> medications = new ArrayList<>();
			Any anyMedications = medicalRecordJson.get("medications");
			anyMedications.forEach(medicationJson -> medications.add(medicationJson.toString()));

			List<String> allergies = new ArrayList<>();
			Any anyAllergies = medicalRecordJson.get("allergies");
			anyAllergies.forEach(allergyJson -> allergies.add(allergyJson.toString()));

			// MedicalRecord aux bonnes personnes
			searchPerson(firstName, lastName, persons).setMedicalRecord(
					new MedicalRecord(birthday, medications, allergies));
			setMedicalRecordCounter(getMedicalRecordCounter() + 1);
		}

		safetyData = new SafetyData(persons, firestations, foyers);

	}

	/**
	 * Search person with first & last name
	 *
	 * @param firstName   String
	 * @param lastName    String
	 * @param persons String
	 * @return person
	 */
	public static Person searchPerson(String firstName, String lastName, List<Person> persons) {

		return persons.stream()
				.filter(person -> firstName.equals(person.getFirstName())
						&& lastName.equals(person.getLastName()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Name : "
						+ firstName.toUpperCase()
						+ " "
						+ lastName.toUpperCase()
						+ "not found"));
	}

	public static int getPersonCounter() {
		return personCounter;
	}

	public static void setPersonCounter(int personCounter) {
		DataSafety.personCounter = personCounter;
	}

	public static int getFireStationCounter() {
		return fireStationCounter;
	}

	public static void setFireStationCounter(int fireStationCounter) {
		DataSafety.fireStationCounter = fireStationCounter;
	}

	public static int getMedicalRecordCounter() {
		return medicalRecordCounter;
	}

	public static void setMedicalRecordCounter(int medicalRecordCounter) {
		DataSafety.medicalRecordCounter = medicalRecordCounter;
	}

	public SafetyData getSafetyData() {
		return safetyData;
	}

}
