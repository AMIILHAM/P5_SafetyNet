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
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;

@RestController
@RequestMapping("medicalRecord")
public class MedicalRecordController {
	
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	@GetMapping("findAll")
	public List<MedicalRecord> findAll() {
		return medicalRecordService.findAll();
	}
	
	
	@PostMapping("add")
	public boolean add(@RequestBody MedicalRecord medicalRecord) throws IOException {
		return medicalRecordService.add(medicalRecord);
	}
	
	@DeleteMapping("delete")
	public boolean delete(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) throws JsonProcessingException, IOException {
		return medicalRecordService.delete(firstName,  lastName);
	}
	
	
}



