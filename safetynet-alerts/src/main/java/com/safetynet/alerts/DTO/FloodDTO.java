package com.safetynet.alerts.DTO;

import java.util.List;

public class FloodDTO {
	 private final String station = "";
	    private final List<FoyersFloodDTO> foyersList = null;

	    public FloodDTO(String station2, List<FoyersFloodDTO> foyersFloodList) {
			// TODO Auto-generated constructor stub
		}

		public String getStationNumber() {
	        return station;
	    }

		public List<FoyersFloodDTO> getFoyersList() {
			return foyersList;
		}
}
