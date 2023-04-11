package com.safetynet.alerts.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.SafetyData;

@Service
public class MedicalRecordService {

	

	@Value("classpath:safetyAlertsData.json")
	private Resource resource;

	private static ObjectMapper objectMapper = new ObjectMapper();

	public List<MedicalRecord> findAll() {
		 return getSafetyData().getMedicalRecords();
	}

	private SafetyData getSafetyData() {
		try (Reader reader = new InputStreamReader(resource.getInputStream())) {
			String elementsAsString = FileCopyUtils.copyToString(reader);
			// deserialisation avec jackson
			SafetyData safetyData = objectMapper.readValue(elementsAsString, SafetyData.class);
			
			if(safetyData == null) {
				return new SafetyData();
			}
			return safetyData;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	public boolean add(MedicalRecord medicalRecord) throws IOException {
		final SafetyData saftyData = getSafetyData();
		List<MedicalRecord> medicalRecords = saftyData.getMedicalRecords();
		if (medicalRecords == null) {
			medicalRecords = new ArrayList<>();
		}
		medicalRecords.add(medicalRecord);
		saftyData.setMedicalRecords(medicalRecords);
		
		// save dans le fichier JSON
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("safetyAlertsData.json").getFile());
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(objectMapper.writeValueAsString(saftyData));
		writer.close();
		
		return true;
	}
	
	
	
	public boolean delete(String firstName, String lastName) throws JsonProcessingException, IOException {
		final SafetyData saftyData = getSafetyData();
		List<MedicalRecord> medicalRecords = saftyData.getMedicalRecords();
		if (medicalRecords == null) {
			medicalRecords = new ArrayList<>();
		}
		
		List<MedicalRecord> newMedicalRecords = medicalRecords.stream() // java stream pour boucler sur une liste
		.filter(medicalRecord -> !firstName.equalsIgnoreCase(medicalRecord.getFirstName()) // éliminer la personne qui match
		&& !lastName.equalsIgnoreCase(medicalRecord.getLastName()))	
		.collect(Collectors.toList());
		
		saftyData.setMedicalRecords(newMedicalRecords); // remettre la liste des personnes modifiées dans l'objet safetyData
		
		// save dans le fichier JSON
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("safetyAlertsData.json").getFile());
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(objectMapper.writeValueAsString(saftyData));
		writer.close();
		
		return true;
	}
	}



