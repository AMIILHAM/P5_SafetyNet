package com.safetynet.alerts.controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;

@RestController
public class MedicalRecordController {

    private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * Create a new MedicalRecord
     *
     * @param newMedicalRecord MedicalRecord
     * @return
     */
    @PostMapping("/medicalRecord")
    public ResponseEntity<Object> createMedicalRecord(
            @RequestBody final MedicalRecord newMedicalRecord) {

        MedicalRecord medicalRecordCreated = medicalRecordService.createMedicalRecord(newMedicalRecord);

        if (medicalRecordCreated != null) {
            logger.info("OK 201 - createMedicalRecord POST request");
            return new ResponseEntity<>(HttpStatus.OK);
            
        } else {
            logger.info("FAILED 409 - CONFLICT");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
            
        }
    }

    /**
     * Update MedicalRecord
     *
     * @param medicalRecord MedicalRecord
     */
    @PutMapping("/medicalRecord")
    public void updateMedicalRecord(
             @RequestBody final MedicalRecord medicalRecord) {

        boolean isUpdated = medicalRecordService.updateMedicalRecord(medicalRecord);

        if (isUpdated) {
            logger.info("OK 200 - MedicalRecord PUT request");
           
        } else {
            logger.error("404 - FAILED to update MedicalRecord for person: {} {}."
                    + " Unknown", medicalRecord.getFirstName(), medicalRecord.getLastName());
           
        }
    }

    /**
     * Delete  MedicalRecord
     *
     * @param firstName String
     * @param lastName  String
     */
    @DeleteMapping("/medicalRecord")
    public void deleteMedicalRecord(
             @RequestParam final String firstName,
             @RequestParam final String lastName) {

        boolean isDeleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);

        if (isDeleted) {
            logger.info("OK 200 - MedicalRecord DELETE request");
   
        } else {
            logger.error("404 - FAILED to delete MedicalRecord for : {} {}" + "Who is this person",
                    firstName, lastName);
           
        }
    }
}


