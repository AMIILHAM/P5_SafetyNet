package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PersonControllerTest {


    @InjectMocks
    private PersonService personService;


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext WebContext;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(WebContext).build();
    }

    @Test
    @Tag("CreatePerson")
    @DisplayName("Create Person OK when all informations are correct")
    void createPersonOkWhenAllInformationsAreCorrect() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/person/add")
                        .contentType(APPLICATION_JSON)
                        .content("{\"firstName\": \"ilham\",\"lastName\": \"ami\",\"address\": \"magny les hameaux\",\"city\": \"idf\",\"zip\": \"78114\",\"phone\": \"88888888\",\"email\": \"ami.ilham10@gmail.com\"}")
                        .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    @Tag("UpdatePerson")
    @DisplayName("Update Person OK when Person exist")
    void updatePersonOkWhenPersonExist() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/person/update")
                        .contentType(APPLICATION_JSON)
                        .content("{\"firstName\": \"Foster\",\"lastName\": \"Shepard\",\"address\": \"748 Townings Dr\",\"city\": \"Culver\",\"zip\": \"97451\",\"phone\": \"841-874-6544\",\"email\": \"jaboyd@email.com\"}")
                        .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    @Tag("DeletePerson")
    @DisplayName("Delete Person OK if Person in the list")
    void deletePersonOkIfPersonInTheList() throws Exception {
        Person person = new Person();
        person.setFirstName("jule");
        person.setLastName("Cooper");
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/person/delete")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(person))
                        .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Tag("CommunityEmail")
    @DisplayName("Valid city entry 'Culver' - CommunityEmail")
    void validCityEntryCulverCommunityEmail() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/person/communityEmail")
                        .contentType(APPLICATION_JSON).param("city", "Culver"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                		 "[\"drk@email.com\",\"soph@email.com\",\"reg@email.com\",\"jaboyd@email.com\",\"bstel@email.com\",\"clivfd@ymail.com\",\"jpeter@email.com\",\"aly@imail.com\",\"ssanw@email.com\",\"gramps@email.com\",\"zarc@email.com\",\"ward@email.com\",\"tcoop@ymail.com\",\"tenz@email.com\",\"lily@email.com\"]"))              		
                .andExpect(jsonPath("$.length()", is(15)));
    }

    @Test
    @Tag("CommunityEmail")
    @DisplayName("Bad City entry : Paris")
    void badCityEntryParisCommunityEmail()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/person/communityEmail")
                        .contentType(APPLICATION_JSON).param("city", "Paris"))
                .andExpect(status().isOk()).andExpect(content().string(
                        "[\"Not found : Paris\"]"));
    }

   
    @Test
    @Tag("PersonInfo")
    @DisplayName("PersonInfo - LastName is Unknown")
    void personInfoLastNameUnknown() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/person/personInfo")
                        .contentType(APPLICATION_JSON).param("firstName", "ilham")
                        .param("lastName", "ami")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    @Tag("ChildAlert")
    @DisplayName("ChildAlert OK - Address with Childs)")
    void childAlertAddressWithChilds() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/person//childAlert")
                        .contentType(APPLICATION_JSON)
                        .param("address", "1509 Culver St"))
                .andExpect(status().isOk()).andExpect(content().string(
                        "[{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"age\":39},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"age\":34},{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"age\":11},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"age\":5},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\",\"age\":37}]"));
    }

    @Test
    @Tag("ChildAlert")
    @DisplayName("ChildAlert - Address without Childs")
    void childAlertAddressWithoutChilds() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/person/childAlert")
                        .contentType(APPLICATION_JSON).param("address", "29 15th St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));
    }
}

	    
	 