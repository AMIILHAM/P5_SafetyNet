package com.safetynet.alerts.service;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

	
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.SafetyData;



@Service
public class FirestationService  {

    private static final Logger logger = LogManager.getLogger(FirestationService.class);

    
    @Autowired
    private SafetyData safetyData;

    /**
     * Create new firestation number/address
     
     * @return isAdded boolean
     */
    public boolean addAddressForFirestation(final Map<String, String> firestationMappingToCreate) {

        try {
            Map<String, Firestation> allFirestationsMapping = safetyData.getFirestations();
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
            Map<String, Firestation> allFirestationsMapping = safetyData.getFirestations();
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
     * @param address String
     * @return isDeleted boolean
     */
    public boolean deleteAddressForFirestation(final String address) {
        Map<String, Firestation> allFirestationsMapping = safetyData
                .getFirestations();

        for (Entry<String, Firestation> entry : allFirestationsMapping
                .entrySet()) {
            Firestation firestationsNumber = entry.getValue();

            if (firestationsNumber.getAddresses().toString().contains(address)) {
                firestationsNumber.getAddresses().remove(address);
                return true;
            }
        }
        logger.error("Address entered : {} not exist.", address);
        return false;
    }

   
    }