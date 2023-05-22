package com.safetynet.alerts.model;

public class Person {

	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String zip;
	private String phone;
	private String email;
	private MedicalRecord medicalRecord;

	public Person() {}
	
	/**
     * Constructor used to convert json data in java object.
     */
    public Person( String personsFirstName,  String personsLastName,
                   String personsAddress,  String personsCity,
                   String personsZip,  String personsPhoneNumber,
                   String personsEmail) {
        this.firstName = personsFirstName;
        this.lastName = personsLastName;
        this.address = personsAddress;
        this.city = personsCity;
        this.zip = personsZip;
        this.phone = personsPhoneNumber;
        this.email = personsEmail;
    }
    /**
     * Constructor with MedicalRecord
     */
    public Person( String personsFirstName,  String personsLastName,
                   String personsAddress,  String personsCity,
                   String personsZip,  String personsPhoneNumber,
                   String personsEmail,
                   MedicalRecord personsMedicalRecord) {
        this.firstName = personsFirstName;
        this.lastName = personsLastName;
        this.address = personsAddress;
        this.city = personsCity;
        this.zip = personsZip;
        this.phone = personsPhoneNumber;
        this.email = personsEmail;
        this.medicalRecord=personsMedicalRecord;
        
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String adress) {
		this.address = adress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}
	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}
	
}
