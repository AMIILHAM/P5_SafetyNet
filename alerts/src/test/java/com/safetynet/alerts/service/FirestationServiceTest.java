package com.safetynet.alerts.service;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;


import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.SafetyData;
import com.safetynet.alerts.utils.DataSafety;


	/**
	 * FirestationService tests class
	 */
	@WebMvcTest(FirestationService.class)
	@ExtendWith(MockitoExtension.class)

	class FirestationServiceTest {
		 @InjectMocks
		    private PersonService personService;

	    @InjectMocks
	    private FirestationService firestationService;
	    @MockBean
	    private DataSafety safetyData;
	    
	    private static MedicalRecord medicalRecordAdult;
	    private static MedicalRecord medicalRecordChild;
	    private static final String adultBirthday = "03/23/1993";
	    private static final String childBirthday = "01/01/2020";
	    private static Person person1;
	    private static Person person2;
	    private static Person person3;
	    @BeforeEach
	    private void setUpPerTest() {
	        List<String> medicationsList = new ArrayList<>();
	        medicationsList.add("medication1");
	        List<String> allergiesList = new ArrayList<>();
	        allergiesList.add("allergies");
	        medicalRecordAdult = new MedicalRecord(adultBirthday, medicationsList, allergiesList);
	        medicalRecordChild = new MedicalRecord(childBirthday, medicationsList, allergiesList);
	    }

	   
	    
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
	        when(safetyData.getSafetyData()).thenReturn(new SafetyData(new ArrayList<>(), firestationsList, new HashMap<>()));
	        boolean isAdded = firestationService.addAddressForFirestation(firestationMappingToCreate);
	        assertThat(isAdded).isTrue();
	        assertThat(firestationsList.get("1").getAddresses().contains("new address")).isTrue();
	    }
	    @Test
	    @Tag("DELETE")
	    @DisplayName("Delete firestation")
	    void deleteFirestationExisting() {
	        Firestation firestation1 = new Firestation("1");
	        firestation1.addAddress("908 73rd St");
	        firestation1.addAddress("644 Gershwin Cir");
	        Map<String, Firestation> firestationsList = new HashMap<String, Firestation>();
	        firestationsList.put("1", firestation1);
	        // ajout new addresse station 1
	        String firestationMappingToCreate = "908 73rd St";
	        
	        when(safetyData.getSafetyData()).thenReturn(new SafetyData(new ArrayList<>(), firestationsList, new HashMap<>()));
	        boolean isDeleted = firestationService.deleteAddressForFirestation(firestationMappingToCreate);
	        	
	        assertThat(isDeleted).isTrue();
	        assertThat(firestationsList.size()).isEqualTo(1);
	       
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
	        when(safetyData.getSafetyData()).thenReturn(new SafetyData(new ArrayList<>(), firestationsList, new HashMap<>()));
	        boolean isUpdated = firestationService.updateAddressForFirestation(firestationMappingToUpdate);
	        assertThat(isUpdated).isTrue();
	        assertThat(firestationsList.get("1").getAddresses().contains("908 73rd St")).isFalse();
	        assertThat(firestationsList.get("2").getAddresses().contains("908 73rd St")).isTrue();
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
	        when(safetyData.getSafetyData()).thenReturn(new SafetyData(new ArrayList<>(), firestationsList, new HashMap<>()));
	        boolean isAdded = firestationService.addAddressForFirestation(firestationMappingToCreate);
	        assertThat(isAdded).isTrue();
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

	        when(safetyData.getSafetyData()).thenReturn(new SafetyData(new ArrayList<>(), firestationsList, new HashMap<>()));
	        boolean isUpdated = firestationService.updateAddressForFirestation(firestationMappingToUpdate);
	        assertThat(isUpdated).isTrue();
	        assertThat(firestationsList.get("3").getAddresses().contains("908 73rd St")).isFalse();
	        assertThat(firestationsList.get("2").getAddresses().contains("908 73rd St")).isTrue();
	    }
	    
	    @Test
	    @Tag("phoneAlert")
	    @DisplayName("Valid station entry - phoneAlert")
	    void validAddressEntryPhoneAlert() {
	    	Map<String, Firestation> firestationsList = new HashMap<String, Firestation>();
	    	 Firestation firestation1 = new Firestation("1");
	    	 firestation1.addAddress("1509 Culver St");
			 firestationsList.put("1", firestation1);
		    
	    	  List<Person> personsList = new ArrayList<>();
	        person1 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451",
	                "841-874-6512", "jaboyd@email.com", medicalRecordAdult);
	        person2 = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451",
	                "841-874-6513", "drk@email.com", medicalRecordAdult);
	        person3 = new Person("Eric", "Cadigan", "951 LoneTree Rd", "Culver",
	                "97451", "841-874-7458", "gramps@email.com",
	                medicalRecordAdult);
	       
	        personsList.add(person1);
	        personsList.add(person2);
	        personsList.add(person3);
	   

	        when(safetyData.getSafetyData()).thenReturn(new SafetyData(personsList, firestationsList, new HashMap<>()));
	        List<String> personsPhone = firestationService.phoneAlert("1");
	        		
	        assertThat(personsPhone.get(0)).isEqualTo("841-874-6512");
	        assertThat(personsPhone.get(1)).isEqualTo("841-874-6513");
	    }
	}
	   