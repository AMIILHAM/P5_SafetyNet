package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.FirestationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class FirestationControllerTest {

    @InjectMocks
    private FirestationService firestationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext WebContext;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(WebContext).build();
    }

    @Test
    @Tag("POST")
    @DisplayName("Add Address OK")
    void existingFireStationAddAddressStatusIsCreated() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/firestation/add")
                        .contentType(APPLICATION_JSON)
                        .content("{\"address\": \"NEW ADDRESS\",\"station\": \"1\"}")
                        .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }


    @Test
    @Tag("PhoneAlert")
    @DisplayName("PhoneAlert OK with correct Station Number")
    void checkIfPhoneAlertOkWithCorrectStationNumber()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/firestation/phoneAlert")
                        .contentType(APPLICATION_JSON).param("firestation", "1"))
                .andExpect(status().isOk()).andExpect(content().string(
                        "[\"841-874-6512\",\"841-874-8547\",\"841-874-7462\",\"841-874-7784\",\"841-874-7784\",\"841-874-7784\"]"));
    }

    @Test
    @Tag("PhoneAlert")
    @DisplayName("PhoneAlert ERROR with Unknown Station Number '20'")
    public void checkErrorPhoneAlertWithUnknownStationNumber()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/firestation/phoneAlert")
                        .contentType(APPLICATION_JSON).param("firestation", "20")
                        .accept(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.length()", is(0)));
    }

   
    @Test
    @Tag("Fire")
    @DisplayName("Unknown Address when Fire return not found - ERROR")
    void unknownAddressWhenFireReturnNotFoundError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/firestation/fire")
                        .contentType(APPLICATION_JSON).param("address", "INCONNU"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    @Tag("Flood")
    @DisplayName("Flood two valid Station Entered '2-3' and return - OK")
    void floodTwoValidStationEnteredAndReturnOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/firestation/flood/stations")
                        .contentType(APPLICATION_JSON).param("stations", "2,3"))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("PUT")
    @DisplayName("Update Address OK")
    void updateSuccessWithFireStation2AndCheckIsOk()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/firestation/update")
                        .contentType(APPLICATION_JSON)
                        .content("{\"address\": \"892 Downing Ct\",\"station\": \"2\"}")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("PUT")
    @DisplayName("Update Address ERROR with unknown Station number")
    void updateAddressErrorWithUnknownStationNumber() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/firestation/update")
                        .contentType(APPLICATION_JSON) // APPLICATION_JSON_VALUE
                        .content("{\"address\": \"Unknown address\",\"station\": \"1\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete existing address FireStation OK")
    void deleteExistingAddressFireStationThenReturnOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/delete")
                        .contentType(APPLICATION_JSON).param("address", "951 LoneTree Rd")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
	  

		
	   

	



