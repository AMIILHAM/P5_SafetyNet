package com.safetynet.alerts.service;


import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.*;


/**
 * AgeCalculator unit tests
 */
class AgeCalculatorServiceTest {

    private String birthDay;
    private DateTimeFormatter formatDate;
    private LocalDate currentDate;
    private Person newPerson;
    public static final String DATE = "MM/dd/yyyy";

    @BeforeEach
    private void setUp() {
        formatDate = DateTimeFormatter.ofPattern(DATE);
        MedicalRecord medicalRecord = new MedicalRecord("03/23/1993", null, null);
        newPerson = new Person("ilham", "ami", "IDF", "10 square des genets", "78114",
                "333-333-3333", "ami.ilham10@gmail.com", medicalRecord);
    }

    @Test
    @Tag("Valid")
    @DisplayName("AGE CORRECT - 23 ans")
    void adultPersonTwentyYearsOldReturnCorrectAge() {
        int age = 23;
        currentDate = LocalDate.now().minusYears(age);
        birthDay = formatDate.format(currentDate);
        int result = AgeCalculatorService.calculateAge(birthDay);
        assertThat(age).isEqualTo(result);
    }

    @Test
    @Tag("Valid")
    @DisplayName("AGE CORRECT 1 an")
    void childPersonBabyAgeCorrect() {
        int age = 1;
        currentDate = LocalDate.now().minusYears(age);
        birthDay = formatDate.format(currentDate);
        int result = AgeCalculatorService.calculateAge(birthDay);
        assertThat(age).isEqualTo(result);
    }

    @Test
    @Tag("Valid")
    @DisplayName("AGE CORRECT 6 mois ")
    void childAgeCorrectSixMonthsHalfYear() {
        currentDate = LocalDate.now().minusMonths(6);
        birthDay = formatDate.format(currentDate);
        int age = AgeCalculatorService.calculateAge(birthDay);
        assertThat(age).isEqualTo(1);
    }

    @Test
    @Tag("Invalid")
    @DisplayName("AGE INVALID - BirthDay Null")
    void ageInvalidBecauseBirthDayIsNull() {
        assertThatNullPointerException().isThrownBy(() -> {
        	AgeCalculatorService.calculateAge(null);
        });
    }
    
    @Test
    @Tag("Invalid")
    @DisplayName("AGE INVALID - Less than 1 Year")
    void ageInvalidBecauseLessThanOneYear() {
        currentDate = LocalDate.now().plusYears(1);
        birthDay = formatDate.format(currentDate);
        assertThatIllegalArgumentException().isThrownBy(() -> {
        	AgeCalculatorService.calculateAge(birthDay);
        });
    }

    @Test
    @Tag("Invalid")
    @DisplayName("AGE INVALID - Less than 1 Month")
    void ageInvalidBecauseLessThanOneMonth() {
        currentDate = LocalDate.now().plusMonths(1);
        birthDay = formatDate.format(currentDate);
        assertThatIllegalArgumentException().isThrownBy(() -> {
        	AgeCalculatorService.calculateAge(birthDay);
        });
    }

    @Test
    @Tag("Invalid")
    @DisplayName("AGE INVALID - Less than 1 Day")
    void ageInvalidBecauseLessThanOneDay() {
        currentDate = LocalDate.now().plusDays(1);
        birthDay = formatDate.format(currentDate);
        assertThatIllegalArgumentException().isThrownBy(() -> {
        	AgeCalculatorService.calculateAge(birthDay);
        });
    }

    @Test
    @Tag("isChildren")
    @DisplayName("isChildren - Child")
    void isChildrenIsChild() {
        boolean isChild;
        newPerson.getMedicalRecord().setBirthdate("03/03/2015");
        isChild = AgeCalculatorService.isChild(newPerson);
        assertThat(isChild).isTrue();
    }

    @Test
    @Tag("isChildren")
    @DisplayName("isChildren - Adult")
    void isChildrenAdult() {
        boolean isChild;
        newPerson.getMedicalRecord().setBirthdate("01/01/2000");
        isChild = AgeCalculatorService.isChild(newPerson);
        assertThat(isChild).isFalse();
    }

    

}