package com.safetynet.alerts.dataTransferObject;

import java.util.List;

public class FoyersFloodDTO {

	  private final AddressDTO address;
	    private final List<PersonFloodDTO> personsList;
	    
	   
		public FoyersFloodDTO(AddressDTO address, List<PersonFloodDTO> personsList) {
			this.personsList = personsList;
			this.address=address;
			
		}
		public AddressDTO getAddress() {
			return address;
		}
		public List<PersonFloodDTO> getPersonsList() {
			return personsList;
		}

	}



