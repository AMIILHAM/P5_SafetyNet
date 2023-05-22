package com.safetynet.alerts.DTO;

import java.util.List;

public class PersonFloodDTO {


	    private final String firstName = "";
	    private final String lastName = "";
	    private final String phone = "";
	    private final int age = 0;
	    private final List<String> medications = null;
	    private final List<String> allergies = null;
		public PersonFloodDTO(String firstName2, String lastName2, String phone2, int age2, List<String> medications2,
				List<String> allergies2) {
			// TODO Auto-generated constructor stub
		}
		public String getFirstName() {
			return firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public String getPhone() {
			return phone;
		}
		public List<String> getMedications() {
			return medications;
		}
		public List<String> getAllergies() {
			return allergies;
		}
		public int getAge() {
			return age;
		}

	
}
