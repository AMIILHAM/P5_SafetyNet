package com.safetynet.alerts.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.SafetyData;

@Service
public class MedicalRecordService {
	

	    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);

	    /**
	     * safetyData for retrieve informations
	     */
	    @Autowired
	    private SafetyData safetyData;

	    /**
	     * Create new MedicalRecord for existing person without MedicalRecord
	     *
	    
	     * @return MedicalRecord | NULL
	     */
	    public MedicalRecord createMedicalRecord(
	            final MedicalRecord newMedicalRecord) {

	        List<Person> personsList = safetyData.getPersons();

	        // creer medicalrecord pour une personne sans
	        for (Person existingPerson : personsList) {
	            if (existingPerson.getFirstName().equalsIgnoreCase(newMedicalRecord.getFirstName().toUpperCase())
	                    && existingPerson.getLastName().equalsIgnoreCase(
	                    newMedicalRecord.getLastName().toUpperCase())
	                    && existingPerson.getMedicalRecord() == null) {
	                existingPerson.setMedicalRecord(newMedicalRecord);
	                return newMedicalRecord;
	            }
	        }
	        logger.error("Error create the MedicalRecord : {} {}, "
	                        + "already existant", newMedicalRecord.getFirstName(), newMedicalRecord.getLastName());
	        return null;
	    }

	    /**
	     * Update MedicalRecord
	     *
	     * @param medicalRecord MedicalRecord
	     * @return boolean isUpdated
	     */
	    public boolean updateMedicalRecord(final MedicalRecord medicalRecord) {
	        boolean isUpdated = false;
	        List<Person> personsList = safetyData.getPersons();

	        // For update an existing person's medicalrecord.
	        for (Person existingPerson : personsList) {
	            if (existingPerson.getMedicalRecord() != null
	                    && existingPerson.getFirstName().equalsIgnoreCase(
	                    medicalRecord.getFirstName().toUpperCase())
	                    && existingPerson.getLastName().equalsIgnoreCase(
	                    medicalRecord.getLastName().toUpperCase())) {
	                existingPerson.setMedicalRecord(medicalRecord);
	                isUpdated = true;
	            }
	        }
	        return isUpdated;
	    }

	    /**
	     * Delete MedicalRecord
	     *
	     * @param firstName String
	     * @param lastName String
	     * @return boolean isDeleted
	     */
	    public boolean deleteMedicalRecord(final String firstName, final String lastName) {

	        boolean isDeleted = false;
	        List<Person> personsList = safetyData.getPersons();

	        // delete le dossier d'une personne
	        for (Person existingPerson : personsList) {
	            if (existingPerson.getMedicalRecord() != null
	                    && existingPerson.getFirstName().equalsIgnoreCase(firstName.toUpperCase())
	                    && existingPerson.getLastName().equalsIgnoreCase(lastName.toUpperCase())) {
	                existingPerson.setMedicalRecord(null);
	                isDeleted = true;
	            }
	        }
	        return isDeleted;
	    }
	}
	

	

	