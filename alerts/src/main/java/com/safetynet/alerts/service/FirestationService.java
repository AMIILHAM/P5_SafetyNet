package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.DTO.AddressDTO;
import com.safetynet.alerts.DTO.FireDTO;
import com.safetynet.alerts.DTO.FloodDTO;
import com.safetynet.alerts.DTO.FoyersFloodDTO;
import com.safetynet.alerts.DTO.PersonFloodDTO;
import com.safetynet.alerts.DTO.PersonStationCounterDTO;
import com.safetynet.alerts.DTO.PersonStationDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.SafetyData;
import com.safetynet.alerts.utils.DataSafety;



@Service
public class FirestationService  {

    private static final Logger logger = LogManager.getLogger(FirestationService.class);

    
    @Autowired
    private DataSafety safetyData;

    /**
     * Create new firestation number/address
     
     * @return isAdded boolean
     */
    public boolean addAddressForFirestation(final Map<String, String> firestationMappingToCreate) {

        try {
            Map<String, Firestation> allFirestationsMapping = safetyData.getSafetyData().getFirestations();
            String newAddress = firestationMappingToCreate.get("address");
            Firestation firestationRecovered = allFirestationsMapping.get(firestationMappingToCreate.get("station"));

            for (Entry<String, Firestation> entry : allFirestationsMapping.entrySet()) {
                Firestation firestationsNumber = entry.getValue();

                
            }
            firestationRecovered.addAddress(newAddress);
            return true;
        } catch (NullPointerException np) {
            throw new NullPointerException("NPE - Verify station number" + np);
        }
    }

    /**
     * Update existing address 
     
     */
    public boolean updateAddressForFirestation(final Map<String, String> firestationMapCreate) {

        try {
            Map<String, Firestation> allFirestationsMapping = safetyData.getSafetyData().getFirestations();
            String address = firestationMapCreate.get("address");
            Firestation firestationNumberRecovered = allFirestationsMapping.get(firestationMapCreate.get("station"));

            for (Entry<String, Firestation> entry : allFirestationsMapping.entrySet()) {
                Firestation firestationsNumber = entry.getValue();

                // 1 - Verifie adresse existante
                // 2 - Delete addresse attribuee a une autre station
                // 3 - MAJ adresse avec l'autre station
                if (firestationsNumber.getAddresses().toString().contains(address)) {
                    firestationsNumber.getAddresses().remove(address);
                    firestationNumberRecovered.addAddress(address);
                    return true;
                }
            }
            logger.error("Address enter : {} does not exist.",
                    firestationMapCreate.values());
            return false;
        } catch (NullPointerException np) {
            throw new NullPointerException("NPE - Please verify the number station " + np);
        }
    }

    /**
     * Delete existing address / update the address/station
     *
     * @param firestationMappingToCreate String
     * @return isDeleted boolean
     */
    public boolean deleteAddressForFirestation(final String firestationMappingToCreate) {
        Map<String, Firestation> allFirestationsMapping = safetyData.getSafetyData().getFirestations();

        for (Entry<String, Firestation> entry : allFirestationsMapping
                .entrySet()) {
            Firestation firestationsNumber = entry.getValue();

            if (firestationsNumber.getAddresses().toString().contains(firestationMappingToCreate)) {
                firestationsNumber.getAddresses().remove(firestationMappingToCreate);
                return true;
            }
        }
        logger.error("Address entered : {} not exist.", firestationMappingToCreate);
        return false;
    }
    
    
    
    /**
     *  return une liste des personnes couvertes par la caserne de pompiers correspondante.
     *
     * @param stationNumber String
     */
    public PersonStationCounterDTO firestationNumber(final String stationNumber) {

        int totalAdultsNumber = 0;
        int totalChildrenNumber = 0;

        Map<String, Firestation> allFirestationsMapping = safetyData.getSafetyData().getFirestations();
        Set<String> addressesRecovered = new HashSet<>();
        for (Entry<String, Firestation> entry : allFirestationsMapping.entrySet()) {
            Firestation firestation = entry.getValue();

            if (firestation.getStation().equals(stationNumber)) {
                addressesRecovered = firestation.getAddresses();
            }
        }
        // verifie
        if (addressesRecovered.isEmpty()) {
            return new PersonStationCounterDTO(null,
                    0, 0);
        }
        List<Person> allPersonsList = safetyData.getSafetyData().getPersons();
        List<PersonStationDTO> personsUnderResponsibility = new ArrayList<>();

        for (Person person : allPersonsList) {
            if (addressesRecovered.contains(person.getAddress())) {
                // Ajout person
                PersonStationDTO personStationDto = new PersonStationDTO(
                        person.getFirstName(), person.getLastName(),
                        person.getAddress(), person.getPhone());
                personsUnderResponsibility.add(personStationDto);
                // Count childs & adults
                if (!AgeCalculatorService.isChild(person)) {
                    totalAdultsNumber++;
                } else {
                    totalChildrenNumber++;
                }
            }
        }
        return new PersonStationCounterDTO(
                personsUnderResponsibility, totalAdultsNumber,
                totalChildrenNumber);
    }
    /**
     * return une liste des numéros de téléphone des résidents desservis par la caserne de
pompiers
     *
     * @param fireStation String
     * @return phoneNumberList
     */
    public List<String> phoneAlert(final String firestation) {
        Map<String, Firestation> allFirestationsMapping = safetyData.getSafetyData().getFirestations();
        Set<String> addressesRecovered = new HashSet<>();

        for (Entry<String, Firestation> entry : allFirestationsMapping.entrySet()) {
            Firestation firestationToRecover = entry.getValue();
            if (firestationToRecover.getStation().equals(firestation)) {
                addressesRecovered = firestationToRecover.getAddresses();
            }
        }
        List<Person> allPersonsList = safetyData.getSafetyData().getPersons();
        List<String> phoneNumberList = new ArrayList<>();

        for (Person person : allPersonsList) {
            if (addressesRecovered.contains(person.getAddress())) {
                phoneNumberList.add(person.getPhone());
            }
        }
        return phoneNumberList;
    }
    /**
     *  retourner la liste des habitants vivant à l’adresse donnée 
     *
     * @param address String
     * @return fireDtoPersonsList
     */
    public List<FireDTO> fire(final String address) {

        Map<String, Firestation> allFirestationsMapping = safetyData.getSafetyData().getFirestations();
        List<FireDTO> fireDtoPersonsList = new ArrayList<>();

        for (Entry<String, Firestation> entry : allFirestationsMapping.entrySet()) {
            Firestation firestation = entry.getValue();
            if (firestation.getAddresses().contains(address)) {
                String stationNumber = firestation.getStation();

                safetyData.getSafetyData();
				// recupere personne par foyer
                Map<String, List<Person>> foyers = SafetyData.getFoyers();
                for (Entry<String, List<Person>> entryset : foyers.entrySet()) {
                    String foyerAddress = entryset.getKey();
                    if (!address.equals(foyerAddress)) {
                        continue;
                    }
                    List<Person> foyerMembersList = entryset.getValue();
                    for (Person person : foyerMembersList) {
                        FireDTO fireDtoPerson = new FireDTO(stationNumber,
                                person.getFirstName(), person.getLastName(),
                                person.getMedicalRecord().getAge(),
                                person.getPhone(),
                                person.getMedicalRecord().getMedications(),
                                person.getMedicalRecord().getAllergies());
                        fireDtoPersonsList.add(fireDtoPerson);
                    }
                }
            }
        }
        return fireDtoPersonsList;
    }
    
    
    /**
     *  retourner une liste de tous les foyers desservis par la caserne - List of information : Name, Age, Phone, MedicalRecord
     *
     * @param stations List<String>
     * @return floodDtoList
     */
    public List<FloodDTO> flood(final List<String> stations) {

        List<FloodDTO> floodDtoList = new ArrayList<>();
        Map<String, Firestation> firestations = safetyData.getSafetyData().getFirestations();
        List<Person> allPersonsList = safetyData.getSafetyData().getPersons();

        for (String station : stations) {
            // recupere toute les adresse des firestations
            Set<String> addressesRecovered = new HashSet<>();
            for (Entry<String, Firestation> entry : firestations.entrySet()) {
                Firestation firestationToRecover = entry.getValue();
                if (firestationToRecover.getStation().contains(station)) {
                    addressesRecovered = firestationToRecover.getAddresses();
                    break;
                }
            }
            if (addressesRecovered.isEmpty()) {
                logger.error("No station found {}", station);
                return null;
            }
            // regroupe les personnes allant à la même adresse de Firestation
            Map<AddressDTO, List<PersonFloodDTO>> foyerDTO = new HashMap<>();
            for (Person person : allPersonsList) {

                if (addressesRecovered.contains(person.getAddress())) {
                    PersonFloodDTO floodPerson = new PersonFloodDTO(
                            person.getFirstName(), person.getLastName(),
                            person.getPhone(),
                            person.getMedicalRecord().getAge(),
                            person.getMedicalRecord().getMedications(),
                            person.getMedicalRecord().getAllergies());
                    AddressDTO addressDTO = new AddressDTO(person.getAddress(), person.getCity(), person.getZip());

                    boolean isSameHouse = false;
                    for (Entry<AddressDTO, List<PersonFloodDTO>> entry : foyerDTO.entrySet()) {
                        if (entry.getKey().getAddress().equals(addressDTO.getAddress()) && entry.getKey().getCity()
                                .equals(addressDTO.getCity()) && entry.getKey().getZip()
                                .equals(addressDTO.getZip())) {
                            entry.getValue().add(floodPerson);
                            isSameHouse = true;
                        }
                    }
                    if (!isSameHouse) {
                        foyerDTO.put(addressDTO, new ArrayList<>());
                        for (Entry<AddressDTO, List<PersonFloodDTO>> entry : foyerDTO.entrySet()) {
                            if (entry.getKey().getAddress().equals(addressDTO.getAddress())
                                    && entry.getKey().getCity().equals(addressDTO.getCity())
                                    && entry.getKey().getZip().equals(addressDTO.getZip())) {
                                entry.getValue().add(floodPerson);
                            }
                        }
                    }
                }
            }
            List<FoyersFloodDTO> foyersFloodList = new ArrayList<>();
            for (Entry<AddressDTO, List<PersonFloodDTO>> entry : foyerDTO.entrySet()) {
               FoyersFloodDTO foyersFloodDTO = new FoyersFloodDTO(entry.getKey(), entry.getValue());
                foyersFloodList.add(foyersFloodDTO);
            }
            FloodDTO flood = new FloodDTO(station,foyersFloodList);
            floodDtoList.add(flood);
        }
        return floodDtoList;
    }




    }