package com.safetynet.alerts.model;



import java.util.List;
import java.util.Map;



public class SafetyData {

	private List<Person> persons;
	 private Map<String, Firestation> firestations;
	private List<MedicalRecord> medicalRecords;
	public Map<String, Firestation> getFirestations() {
		return firestations;
	}
	public void setFirestations(Map<String, Firestation> firestations) {
		this.firestations = firestations;
	}
	public List<MedicalRecord> getMedicalRecords() {
		return medicalRecords;
	}
	public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
		this.medicalRecords = medicalRecords;
	}
	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	
	

	
}
	
	