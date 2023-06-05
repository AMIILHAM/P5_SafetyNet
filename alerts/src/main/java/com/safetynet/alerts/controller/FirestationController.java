package com.safetynet.alerts.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.javaparser.quality.NotNull;
import com.safetynet.alerts.dataTransferObject.FireDTO;
import com.safetynet.alerts.dataTransferObject.FloodDTO;
import com.safetynet.alerts.dataTransferObject.PersonStationCounterDTO;
import com.safetynet.alerts.service.FirestationService;


@RestController
@RequestMapping("/firestation")
public class FirestationController {

    private static final Logger logger = LogManager.getLogger(FirestationController.class);

    @Autowired
    private FirestationService firestationService;

    /**
     * Create new FireStation number/address
     *
     * @param stationToCreate Map
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> addAddressForFirestation(
             @RequestBody final Map<String, String> stationToCreate) {

        boolean isAdded = firestationService.addAddressForFirestation(stationToCreate);

        if (isAdded) {
            logger.info("OK 201 - addAddressForFireStation POST request "
                            + "-  FireStation number : {}, Address : {}",
                    stationToCreate.get("station"),
                    stationToCreate.get("address"));
            
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * update address for firestation
     * @param mappingToUpdate Map
     */
    @PutMapping("/update")
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
    @DeleteMapping("/delete")
    public void deleteAddressForFireStation(
             @RequestParam final String address) {

        boolean isDeleted = firestationService.deleteAddressForFirestation(address);

        if (isDeleted) {
            logger.info("OK 200 - DeleteFireStation DELETE request "
                    + "-  Address : {}", address);
           
    }
    }
    
    
    /**
     * Verifier la personne par  station et compter adults / childrens
     *
     * @param stationNumber String
     * @return firestationDto
     */
    @GetMapping("/firestationNumber")
    public PersonStationCounterDTO firestationNumber(
            @NotNull @RequestParam(value = "stationNumber") final String stationNumber) {

        PersonStationCounterDTO firestationDto = firestationService.firestationNumber(stationNumber);

        if (firestationDto.getPersonsStationList() != null) {
            logger.info("OK 200 - FireStationNumber GET request");
           
        } else {
            logger.error("FAILED 404 - No FireStation found : {}. "
                    + "Check the station number", stationNumber);
        
        }
        return firestationDto;
    }
    
    /**
     * verifier les personnes phone number covered by the station
     *
     * @param firestation the station number
     * @return phoneNumberList
     */
    @GetMapping("/phoneAlert")
    public List<String> phoneAlert(
            @NotNull @RequestParam(value = "firestation") final String firestation) {
        List<String> phoneNumberList = firestationService.phoneAlert(firestation);

        if (!phoneNumberList.isEmpty()) {
            logger.info("OK 200 - PhoneAlert GET request");
       
        } else {
            logger.error("FAILED 404 - No FireStation founded for number : {}. "
                            + "Verify the station number entered.",
                    firestation);
           
        }
        return phoneNumberList;
    }

    /**
     * verifier les personnes vivant dans l'adressse entrée avec le firestation number
     *
     * @param address String
     * @return fireDtoPersonsList
     */
    @GetMapping("/fire")
    public List<FireDTO> fire(
            @NotNull @RequestParam(value = "address") final String address) {

        List<FireDTO> fireDTOPersons = firestationService.fire(address);

        if (!fireDTOPersons.isEmpty()) {
            logger.info("SUCCESS - Fire GET request");
            
        } else {
            logger.error("FAILED - No address for : {}.", address);
           
        }
        return fireDTOPersons;
    }
    /**
     * Flood - Infos : Name Age Phone MedicalRecords
     *
     * @param stations List String
     * @return flood as a list
     */
    @GetMapping("/flood/stations")
    public List<FloodDTO> flood(
            @NotNull @RequestParam(value = "stations") final List<String> stations) {

        List<FloodDTO> flood = firestationService.flood(stations);

        if (flood != null) {
            logger.info("OK 200 - Flood GET request");
            
        } else {
            logger.error("FAILED 404 - Flood GET request");
            
        }
        return flood;
    }


}