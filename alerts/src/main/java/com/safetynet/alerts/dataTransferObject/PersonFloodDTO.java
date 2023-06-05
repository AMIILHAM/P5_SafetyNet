package com.safetynet.alerts.dataTransferObject;

import java.util.List;

public class PersonFloodDTO {


	    private  String firstName;
	    private  String lastName;
	    private  String phone;
	    private  int age;
	    private  List<String> medications;
	    private  List<String> allergies;
		public PersonFloodDTO(String firstName, String lastName, String phone, int age, List<String> medications,
				List<String> allergies) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.age= age;
			this.phone=phone;
			this.medications=medications;
			this.allergies=allergies;
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
