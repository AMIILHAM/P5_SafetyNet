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
	    public static int calculateAge(LocalDate date) {
	        LocalDate now = LocalDate.now();
	        return Period.between(date, now).getYears();
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


