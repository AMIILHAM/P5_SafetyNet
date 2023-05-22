package com.safetynet.alerts.DTO;

import java.util.List;

public class FireDTO {


	    private final String stationNumber = "";
	    private final String firstName = "";
	    private final String lastName = "";
	    private final int age = 0;
	    private final String phoneNumber = "";
	    private final List<String> medications = null;
	    private final List<String> allergies = null;
		public FireDTO(String stationNumber2, String firstName2, String lastName2, int age2, String phone,
				List<String> medications2, List<String> allergies2) {
			// TODO Auto-generated constructor stub
		}
		public String getStationNumber() {
			return stationNumber;
		}
		public String getFirstName() {
			return firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public int getAge() {
			return age;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public List<String> getMedications() {
			return medications;
		}
		public List<String> getAllergies() {
			return allergies;
		}
	}

