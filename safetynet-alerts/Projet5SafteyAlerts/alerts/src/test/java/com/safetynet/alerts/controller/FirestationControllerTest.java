package com.safetynet.alerts.controller;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
	@WebMvcTest(controllers = FirestationController.class)
	public class FirestationControllerTest {
	

	    @ Autowired
	    private MockMvc mockMvc;


	    @MockBean
	    private FirestationService firestationService;
	    @MockBean
   	 Firestation firestation;

   	 @MockBean 
   	 Model model;

	    @Test
	   public void testFindAll() throws Exception {
	        mockMvc.perform(get("/firestation/findAll"))
	          .andExpect(status().isOk());
	    }
	    
	  

	
	}
	  

		
	   

	



