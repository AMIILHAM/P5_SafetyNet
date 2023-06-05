package com.safetynet.alerts.dataTransferObject;

public class AddressDTO {


	    private  String address;
	    private  String city;
	    private  String zip;
		public AddressDTO(String address, String city, String zip) {
			this.address= address;
			this.city=city;
			this.zip=zip;
		}
		public String getAddress() {
			return address;
		}
		public String getCity() {
			return city;
		}
		public String getZip() {
			return zip;
		}

	}


