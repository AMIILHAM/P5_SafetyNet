package com.safetynet.alerts.service;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynet.alerts.model.Person;


public class AgeCalculatorService {


	    private static final Logger logger = LogManager.getLogger(AgeCalculatorService.class);
	    private static final int MINOR_UNDER = 18;

	    private AgeCalculatorService() {
	    }

	    /**
	     * AgeCalculation 
	     *
	     * @param birthDay String
	     * @return age
	     
	     */
	    public static final String DATE = "MM/dd/yyyy";
	    public static int calculateAge(final String birthDay) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE);
	        LocalDate personsBirthdate = LocalDate.parse(birthDay, formatter);
	        LocalDate currentDate = LocalDate.now();

	        int age = Period.between(personsBirthdate, currentDate).getYears();

	        if (personsBirthdate.isAfter(currentDate)) {
	            logger.error("Person birthday invalid");
	            throw new IllegalArgumentException("Error - BirthDay Unknown\n ");
	        } else if (age == 0) {
	            logger.debug("Baby before a year");
	            age++;
	        } else {
	            return age;
	        }
	        return age;
	    }

	    /**
	     * Calcul if person childs | adults
	     *
	     * @param person Person
	     * @return isChild boolean
	     */
	    public static boolean isChild(final Person person) {
	        boolean isChild = false;
	        int personsAge = 0;
			try {
				personsAge = AgeCalculatorService.calculateAge(person.getMedicalRecord().getBirthday());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if (personsAge <= MINOR_UNDER) {
	            isChild = true;
	        }
	        return isChild;
	    }
	}


