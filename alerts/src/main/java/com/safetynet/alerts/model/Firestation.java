package com.safetynet.alerts.model;

import java.util.HashSet;
import java.util.Set;

public class Firestation {
	private final Set<String> addresses= new HashSet<>();
    private String station;



public Firestation(String station) {
		// TODO Auto-generated constructor stub
	this.station= station;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public Set<String> getAddresses() {
		return addresses;
	}
	
	 public void addAddress(final String newAddress) {
	        addresses.add(newAddress);
}
}
