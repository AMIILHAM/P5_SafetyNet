package com.safetynet.alerts.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.service.FirestationService;


@RestController
public class FirestationController {

    private static final Logger logger = LogManager.getLogger(FirestationController.class);

    @Autowired
    private FirestationService firestationService;

    /**
     * Create new FireStation number/address
     *
     * @param stationToCreate Map
     */
    @PostMapping("/firestation")
    public void addAddressForFirestation(
             @RequestBody final Map<String, String> stationToCreate) {

        boolean isAdded = firestationService.addAddressForFirestation(stationToCreate);

        if (isAdded) {
            logger.info("OK 200 - addAddressForFireStation POST request "
                            + "-  FireStation number : {}, Address : {}",
                    stationToCreate.get("station"),
                    stationToCreate.get("address"));
            
        } 
    }

    /**
     * 
     * @param mappingToUpdate Map
     */
    @PutMapping("/firestation")
    public void updateAddressForFireStation(
             @RequestBody final Map<String, String> mappingToUpdate) {

        boolean isUpdated = firestationService.updateAddressForFirestation(mappingToUpdate);

        if (isUpdated) {
            logger.info("OK 200 - UpdateFireStation PUT request " + "-  Firestation number : {}, Address : {}",
                    mappingToUpdate.get("station"),
                    mappingToUpdate.get("address"));
           
        }
    }


    /**
     * Delete address
     *
     * @param address String
     */
    @DeleteMapping("/firestation")
    public void deleteAddressForFireStation(
             @RequestParam final String address) {

        boolean isDeleted = firestationService.deleteAddressForFirestation(address);

        if (isDeleted) {
            logger.info("OK 200 - DeleteFireStation DELETE request "
                    + "-  Address : {}", address);
           
    }
    }
}