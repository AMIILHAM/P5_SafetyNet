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
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.SafetyData;

@Service
public class PersonService {

	

	@Value("classpath:safetyAlertsData.json")
	private Resource resource;

	private static ObjectMapper objectMapper = new ObjectMapper();

	public List<Person> findAll() {
		 return getSafetyData().getPersons();
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
	
	public boolean add(Person person) throws IOException {
		final SafetyData saftyData = getSafetyData();
		List<Person> persons = saftyData.getPersons();
		if (persons == null) {
			persons = new ArrayList<>();
		}
		persons.add(person);
		saftyData.setPersons(persons);
		
		// save dans le fichier JSON
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("safetyAlertsData.json").getFile());
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(objectMapper.writeValueAsString(saftyData));
		writer.close();
		
		return true;
	}
	
	public Person findOne(String firstName, String lastName) {
		final List<Person> allPersons = findAll();
		if (allPersons == null || allPersons.isEmpty()) {
			return null;
		}
		return allPersons
				.stream() // java stream pour boucler sur une liste
				.filter(person -> firstName.equalsIgnoreCase(person.getFirstName())
				&& lastName.equalsIgnoreCase(person.getLastName()))
				.findFirst()
				.orElse(null);
	
		
	}
	

	public boolean delete(String firstName, String lastName) throws JsonProcessingException, IOException {
		final SafetyData saftyData = getSafetyData();
		List<Person> persons = saftyData.getPersons();
		if (persons == null) {
			persons = new ArrayList<>();
		}
		
		List<Person> newPersons = persons.stream() // java stream pour boucler sur une liste
		.filter(person -> !firstName.equalsIgnoreCase(person.getFirstName()) // éliminer la personne qui match
		&& !lastName.equalsIgnoreCase(person.getLastName()))	
		.collect(Collectors.toList());
		
		saftyData.setPersons(newPersons); // remettre la liste des personnes modifiées dans l'objet safetyData
		
		// save dans le fichier JSON
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("safetyAlertsData.json").getFile());
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(objectMapper.writeValueAsString(saftyData));
		writer.close();
		
		return true;
	}
	}

