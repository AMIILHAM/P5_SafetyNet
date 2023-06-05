package com.safetynet.alerts.dataTransferObject;


import java.util.List;


public class PersonStationCounterDTO {

    private List<PersonStationDTO> personsStationList;
    private int totalAdultsNumber;
    private int totalChildrenNumber;

	public PersonStationCounterDTO(List<PersonStationDTO> personsStationList, int totalAdultsNumber, int totalChildrenNumber) {
		this.personsStationList = personsStationList;
		this.totalAdultsNumber = totalAdultsNumber;
		this.totalChildrenNumber = totalChildrenNumber;
	}

	public List<PersonStationDTO> getPersonsStationList() {
		return personsStationList;
	}

	public void setPersonsStationList(List<PersonStationDTO> personsStationList) {
		this.personsStationList = personsStationList;
	}

	public int getTotalAdultsNumber() {
		return totalAdultsNumber;
	}

	public void setTotalAdultsNumber(int totalAdultsNumber) {
		this.totalAdultsNumber = totalAdultsNumber;
	}

	public int getTotalChildrenNumber() {
		return totalChildrenNumber;
	}

	public void setTotalChildrenNumber(int totalChildrenNumber) {
		this.totalChildrenNumber = totalChildrenNumber;
	}
}



