package com.safetynet.alerts.DTO;

import java.util.List;

public class PersonInfoDTO {
	private final String firstName = "";
    private final String lastName = "";
    private final int age = 0;
    private final String address = "";
    private final String city = "";
    private final String zip = "";
    private final String email = "";
    private final List<String> medications = null;
    private final List<String> allergies = null;
	public PersonInfoDTO(String firstName2, String lastName2, int age2, String address2, String city2, String zip2,
			String email2, List<String> medications2, List<String> allergies2) {
		// TODO Auto-generated constructor stub
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getAddress() {
		return address;
	}
	public int getAge() {
		return age;
	}
	public List<String> getMedications() {
		return medications;
	}
	public String getZip() {
		return zip;
	}
	public String getEmail() {
		return email;
	}
	public String getCity() {
		return city;
	}
	public List<String> getAllergies() {
		return allergies;
	}

}
