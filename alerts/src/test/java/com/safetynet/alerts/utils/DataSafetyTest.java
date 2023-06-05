package com.safetynet.alerts.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.alerts.model.Person;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest

public class DataSafetyTest {
	
	private DataSafety safetyData;
	private Person person1;
    private Person person2;

    @BeforeEach
    private void setUp() throws IOException {
        person1 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451",
                "841-874-6512", "jaboyd@email.com");
        person2 = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451",
                "841-874-6513", "drk@email.com");
    }
	 @Test
	    @Tag("searchPerson")
	    @DisplayName("Search Person Valid Person")
	    void searchPersonIsValidPerson() {
	        List<Person> personsList = new ArrayList<>();
	        personsList.add(person1);
	        personsList.add(person2);
	        Person personFound = DataSafety.searchPerson("Jacob", "Boyd", personsList);
	        assertThat(personFound).isNotNull();
	        assertThat(personsList).contains(personFound);
	    }
	 @Test
	    @Tag("searchPerson")
	    @DisplayName("Search Person Unknown Person")
	    void searchPersonUnknownPerson() {
	        List<Person> personsList = new ArrayList<>();
	        personsList.add(person1);
	        personsList.add(person2);
	        assertThatIllegalArgumentException().isThrownBy(() -> {
	            DataSafety.searchPerson("BAHOU", "LOLO", personsList);
	        });
	    }
	
}
