package com.safetynet.alerts.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.BDDMockito.*;



import org.springframework.ui.Model;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;



	@RunWith(SpringRunner.class)
	@WebMvcTest(controllers = PersonController.class)
	public class PersonControllerTest {

	    @Autowired
	    private MockMvc mockMvc;


	    @MockBean
	    private PersonService personService;
	    @MockBean
   	Person person;

   	 @MockBean 
   	 Model model;

	    @Test
	   public void testFindAll() throws Exception {
	        mockMvc.perform(get("/person/findAll"))
	          .andExpect(status().isOk());
	    }
	    @Test
		   public void testFindOne() throws Exception {
		        mockMvc.perform(get("/person/findOne"))
		          .andExpect(status().isOk());
		    }
	  
	    
	    }

	    
	 