package com.safetynet.alerts.DTO;

import java.util.List;

public class FoyersFloodDTO {


	    private final AddressDTO address = new AddressDTO(null, null, null);
	    private final List<PersonFloodDTO> personsList = null;
		public FoyersFloodDTO(AddressDTO key, List<PersonFloodDTO> value) {
			// TODO Auto-generated constructor stub
		}
		public AddressDTO getAddress() {
			return address;
		}
		public List<PersonFloodDTO> getPersonsList() {
			return personsList;
		}

	}



