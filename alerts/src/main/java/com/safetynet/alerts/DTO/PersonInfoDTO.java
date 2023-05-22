package com.safetynet.alerts.DTO;

import java.util.List;

public class PersonInfoDTO {
	private  String firstName;
    private  String lastName;
    private  int age;
    private  String address;
    private  String city;
    private  String zip;
    private String email;
    private  List<String> medications ;
    private  List<String> allergies ;
    public PersonInfoDTO(String firstName, String lastName,int age, String address, String city, String zip, String email, List<String> medications ,  List<String> allergies ) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.age= age;
		this.city= city;
		this.zip=zip;
		this.email=email;
		this.medications=medications;
		this.allergies=allergies;
	}
	
		// TODO Auto-generated constructor stub
	
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
