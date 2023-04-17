package com.safetynet.alerts.service;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.assertj.core.api.Assertions.assertThat;


import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.SafetyData;


	/**
	 * FirestationService tests class
	 */
	@WebMvcTest(FirestationService.class)
	@ExtendWith(MockitoExtension.class)
	class FirestationServiceTest {

	    @InjectMocks
	    private FirestationService fireStationService;
	    @Mock
	    private SafetyData safetyData;

	    @Test
	    @Tag("CREATE")
	    @DisplayName("CREATE FireStation OK with Two Address")
	    void createFireStationOkWithTwoAddress() {
	        Firestation firestation1 = new Firestation("1");
	        firestation1.addAddress("908 73rd St");
	        firestation1.addAddress("644 Gershwin Cir");
	        Map<String, Firestation> firestationsList = new HashMap<String, Firestation>();
	        firestationsList.put("1", firestation1);
	        // ajout new addresse station 1
	        Map<String, String> firestationMappingToCreate = new HashMap<String, String>();
	        firestationMappingToCreate.put("station", "1");
	        firestationMappingToCreate.put("address", "new address");
	        when(safetyData.getFirestations()).thenReturn(firestationsList);
	        boolean isAdded = fireStationService.addAddressForFirestation(firestationMappingToCreate);
	        assertThat(isAdded).isTrue();
	        assertThat(firestationsList.get("1").getAddresses().contains("new address")).isTrue();
	    }

	   

	    @Test
	    @Tag("CREATE")
	    @DisplayName("Create ERROR - Address alreadyExisting")
	    void createErrorAddressAlreadyExisting() {

	        Firestation firestation1 = new Firestation("1");
	        firestation1.addAddress("908 73rd St");
	        firestation1.addAddress("644 Gershwin Cir");

	        Firestation firestation2 = new Firestation("2");
	        firestation2.addAddress("29 15th St");
	        Map<String, Firestation> firestationsList = new HashMap<String, Firestation>();
	        firestationsList.put("1", firestation1);
	        firestationsList.put("2", firestation2);

	        // ajout addresse existante
	        Map<String, String> firestationMappingToCreate = new HashMap<String, String>();
	        firestationMappingToCreate.put("station", "2");
	        firestationMappingToCreate.put("address", "908 73rd St");
	        when(safetyData.getFirestations()).thenReturn(firestationsList);
	        boolean isAdded = fireStationService.addAddressForFirestation(firestationMappingToCreate);
	        assertThat(isAdded).isFalse();
	        assertThat(firestationsList.get("2").getAddresses().contains("908 73rd St")).isFalse();
	    }

	    @Test
	    @Tag("UPDATE")
	    @DisplayName("UPDATE Address OK - Add Address already exist")
	    void updateAddressOkAddAddressAlreadyExist() {

	        Firestation firestation1 = new Firestation("1");
	        firestation1.addAddress("908 73rd St");
	        firestation1.addAddress("644 Gershwin Cir");

	        Firestation firestation2 = new Firestation("2");
	        firestation2.addAddress("29 15th St");
	        Map<String, Firestation> firestationsList = new HashMap<String, Firestation>();
	        firestationsList.put("1", firestation1);
	        firestationsList.put("2", firestation2);

	        //ajout addresse existante pour autre station
	        Map<String, String> firestationMappingToUpdate = new HashMap<String, String>();
	        firestationMappingToUpdate.put("station", "2");
	        firestationMappingToUpdate.put("address", "908 73rd St");
	        when(safetyData.getFirestations()).thenReturn(firestationsList);
	        boolean isUpdated = fireStationService.updateAddressForFirestation(firestationMappingToUpdate);
	        assertThat(isUpdated).isTrue();
	        assertThat(firestationsList.get("1").getAddresses().contains("908 73rd St")).isFalse();
	        assertThat(firestationsList.get("2").getAddresses().contains("908 73rd St")).isTrue();
	    }

	    @Test
	    @Tag("UPDATE")
	    @DisplayName("UPDATE OK - Browse three address")
	    void updateAddressOkBrowseThreeAddress() {

	        Firestation firestation1 = new Firestation("1");
	        firestation1.addAddress("644 Gershwin Cir");

	        Firestation firestation2 = new Firestation("2");
	        firestation2.addAddress("29 15th St");

	        Firestation firestation3 = new Firestation("3");
	        firestation3.addAddress("908 73rd St");

	        Map<String, Firestation> firestationsList = new HashMap<String, Firestation>();
	        firestationsList.put("1", firestation1);
	        firestationsList.put("2", firestation2);
	        firestationsList.put("3", firestation3);

	        // ajout addresse existante
	        Map<String, String> firestationMappingToUpdate = new HashMap<String, String>();
	        firestationMappingToUpdate.put("station", "2");
	        firestationMappingToUpdate.put("address", "908 73rd St");

	        when(safetyData.getFirestations()).thenReturn(firestationsList);
	        boolean isUpdated = fireStationService.updateAddressForFirestation(firestationMappingToUpdate);
	        assertThat(isUpdated).isTrue();
	        assertThat(firestationsList.get("3").getAddresses().contains("908 73rd St")).isFalse();
	        assertThat(firestationsList.get("2").getAddresses().contains("908 73rd St")).isTrue();
	    }


	}