package com.safetynet.alerts.dataTransferObject;

public class ChildAlertDTO {


	    private  String firstName;
	    private  String lastName;
	    private  int age;

	    public ChildAlertDTO( int personsAge,  String personFirstName,
	                          String personLastName) {
	        this.age = personsAge;
	        this.firstName = personFirstName;
	        this.lastName = personLastName;

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
}