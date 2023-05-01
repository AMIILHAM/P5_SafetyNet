package com.safetynet.alerts.model;



import java.util.List;
import java.util.Map;



public class SafetyData {

	private List<Person> persons;
	 private Map<String, Firestation> firestations;
	private List<MedicalRecord> medicalRecords;
	 private static Map<String, List<Person>> personsPerFoyer;

	    public SafetyData(List<Person> persons2, Map<String, Firestation> firestations2, Map<String, List<Person>> foyers) {
		// TODO Auto-generated constructor stub
	}
		public static Map<String, List<Person>> getFoyers() {
	        return personsPerFoyer;
	    }
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
	
	