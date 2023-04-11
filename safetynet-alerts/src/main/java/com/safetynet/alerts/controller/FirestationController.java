package com.safetynet.alerts.controller;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.FirestationService;


@RestController
@RequestMapping("firestation")
public class FirestationController {
	@Autowired

	private FirestationService firestationService;
	
	
	
		
		
		@GetMapping("findAll")
		public List<Firestation> findAll() {
			return firestationService.findAll();
		}
		
		@GetMapping("findOne")
		public Firestation findOne(@RequestParam("address") String address, @RequestParam("station") String station) {
			return firestationService.findOne(address,  station);
		}
		
		@PostMapping("add")
		public boolean add(@RequestBody Firestation firestation) throws IOException {
			return firestationService.add(firestation);
		}
		
		@DeleteMapping("delete")
		public boolean delete(@RequestParam("address") String address, @RequestParam("station") String station) throws JsonProcessingException, IOException {
			return firestationService.delete(address,  station);
		}
		
		
	}
	


