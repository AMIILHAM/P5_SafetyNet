package com.safetynet.alerts.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.tomcat.util.http.parser.MediaType;

import org.springframework.web.context.WebApplicationContext;




@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MidecalRecordControllerTest {

	 @Autowired
	    private MockMvc mockMvc;
	    @Autowired
	    private WebApplicationContext WebContext;

	    @BeforeEach
	    public void setupMockMvc() {
	        mockMvc = MockMvcBuilders.webAppContextSetup(WebContext).build();
	    }

	    @Test
	    @Tag("CREATE")
	    @DisplayName("ERROR CREATE Unknown Person with MedicalRecord")
	    void errorCreateUnknownPersonWithMedicalRecord() throws Exception {
	        this.mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
	                .contentType(APPLICATION_JSON).content(" { \r\n"
	                        + "     \"firstName\":\"ilham\", \r\n"
	                        + "     \"lastName\":\"ami\", \r\n"
	                        + "     \"birthdate\":\"22/10/1993\", \r\n"
	                        + "     \"medications\":[\"hydroxychloroquine:6350mg\", \"anticovid:1000mg\"], \r\n"
	                        + "     \"allergies\":[\"fourmisdesneiges\"] \r\n"
	                        + "     }")
	                .accept(APPLICATION_JSON))
	                .andDo(MockMvcResultHandlers.print())
	                .andExpect(status().isConflict());
	    }
	    @Test
	    @Tag("UPDATE")
	    @DisplayName("UPDATE Person OK with MedicalRecord")
	    void updatePersonOkWithMedicalRecordAndReturnIsOk() throws Exception {
	        this.mockMvc.perform(MockMvcRequestBuilders.put("/medicalRecord")
	                .contentType(APPLICATION_JSON).content(" { \r\n"
	                        + "     \"firstName\":\"lolo\", \r\n"
	                        + "     \"lastName\":\"bahou\", \r\n"
	                        + "     \"birthdate\":\"03/03/1988\", \r\n"
	                        + "     \"medications\":[\"NEW MEDICATION:1550mg\", \"doliprane:1000mg\"], \r\n"
	                        + "     \"allergies\":[\"NEW ALLERGY\"] \r\n"
	                        + "     }")
	                .accept(APPLICATION_JSON))
	                .andDo(MockMvcResultHandlers.print())
	                .andExpect(status().isOk());
	    }
	    @Test
	    @Tag("DELETE")
	    @DisplayName("DELETE Person OK when is in the list")
	    void deletePersonOkWhenMedicalRecordIsDeleteAndInTheList() throws Exception {
	        this.mockMvc.perform(MockMvcRequestBuilders.delete("/medicalRecord")
	                .param("firstName", "lolo").param("lastName", "bahou"))
	                .andExpect(status().isOk());
	    }

}
