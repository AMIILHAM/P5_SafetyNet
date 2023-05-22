package com.safetynet.alerts.DTO;

import java.util.List;

public class FloodDTO {
	 private  String station ;
	    private  List<FoyersFloodDTO> foyersList;

	    public FloodDTO(String station, List<FoyersFloodDTO> foyersFloodList) {
	    	this.foyersList=foyersFloodList;
	    	this.station=station;
	
		}

		public String getStationNumber() {
	        return station;
	    }

		public List<FoyersFloodDTO> getFoyersList() {
			return foyersList;
		}
}
