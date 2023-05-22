package com.safetynet.alerts.model;


import java.util.List;

import com.safetynet.alerts.service.AgeCalculatorService;

public class MedicalRecord {

	private String firstName;
	private String lastName;
	private String birthday;
	private List<String> medications;
	private List<String> allergies;

	public MedicalRecord( String firstName,  String lastName, String birthday, List<String> medicationsList,
			List<String> allergiesList) {
		 
      this.firstName = firstName;
      this.lastName = lastName;
      this.birthday = birthday;
      this.medications = medicationsList;
      this.allergies = allergiesList;
      
		
	}
	public MedicalRecord() {
    }
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthdate(String birthday) {
		this.birthday = birthday;
	}
	public List<String> getMedications() {
		return medications;
	}
	public void setMedications(List<String> medications) {
		this.medications = medications;
	}
	public List<String> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
	
	

    /**
     * @param personBirthday   String
     * @param personMedication List
     * @param personAllergie   List
     */
    public MedicalRecord( String personBirthday,
                          List<String> personMedication,
                          List<String> personAllergie) {
        this.birthday = personBirthday;
        this.medications = personMedication;
        this.allergies = personAllergie;
    }
	
	public int getAge() {
		return AgeCalculatorService.calculateAge(this.birthday);
	}
	
	

	
	
}
