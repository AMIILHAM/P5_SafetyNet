package com.safetynet.alerts.dataTransferObject;

import java.util.List;

public class FireDTO {


	    private  String stationNumber;
	    private  String firstName;
	    private  String lastName;
	    private  int age;
	    private  String phoneNumber;
	    private  List<String> medications;
	    private  List<String> allergies;
		public FireDTO(String stationNumber, String firstName, String lastName, int age, String phoneNumber,
				List<String> medications, List<String> allergies) {
			
			this.stationNumber=stationNumber;
			this.firstName = firstName;
			this.lastName = lastName;
			this.age= age;
			this.phoneNumber=phoneNumber;
			this.medications=medications;
			this.allergies=allergies;
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

