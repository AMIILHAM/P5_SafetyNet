package com.safetynet.alerts.model;

import java.util.List;

public class MedicalRecord {

	private String firstName;
	private String lastName;
	private String birthday;
	private List<String> medications;
	private List<String> allergies;
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
	public String getBirthdate() {
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
	
	public MedicalRecord() {
    }

    /**
     * @param personBirthday   String
     * @param personMedication List
     * @param personAllergie   List
     */
    public MedicalRecord(final String personBirthday,
                         final List<String> personMedication,
                         final List<String> personAllergie) {
        this.birthday = personBirthday;
        this.medications = personMedication;
        this.allergies = personAllergie;
    }
	
	
}
