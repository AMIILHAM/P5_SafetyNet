package com.safetynet.alerts.DTO;

public class ChildAlertDTO {


	    private final String firstName;
	    private final String lastName;
	    private final int age;

	    public ChildAlertDTO(final int personsAge, final String personFirstName,
	                         final String personLastName) {
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