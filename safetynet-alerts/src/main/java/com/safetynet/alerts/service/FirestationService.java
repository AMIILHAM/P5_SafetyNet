
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
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.SafetyData;

	@Service

     public class FirestationService {

		

		@Value("classpath:safetyAlertsData.json")
		private Resource resource;

		private static ObjectMapper objectMapper = new ObjectMapper();

		public List<Firestation> findAll() {
			 return getSafetyData().getFirestations();
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
		
		public boolean add(Firestation firestation) throws IOException {
			final SafetyData saftyData = getSafetyData();
			List<Firestation> Firestations = saftyData.getFirestations();
			if (Firestations == null) {
				Firestations = new ArrayList<>();
			}
			Firestations.add(firestation);
			saftyData.setFirestations(Firestations);
			
			// save dans le fichier JSON
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("safetyAlertsData.json").getFile());
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(objectMapper.writeValueAsString(saftyData));
			writer.close();
			
			return true;
		}
		public Firestation findOne(String address, String station) {
			final List<Firestation> allFirestation = findAll();
			if (allFirestation == null || allFirestation.isEmpty()) {
				return null;
			}
			return allFirestation
					.stream() // java stream pour boucler sur une liste
					.filter(firestation -> address.equalsIgnoreCase(firestation.getAddress())
					&& station.equalsIgnoreCase(firestation.getStation()))
					.findFirst()
					.orElse(null);
		
			
		}



		public boolean delete(String address, String station) throws JsonProcessingException, IOException {
			final SafetyData saftyData = getSafetyData();
			List<Firestation> firestations = saftyData.getFirestations();
			if (firestations == null) {
				firestations = new ArrayList<>();
			}
			
			List<Firestation> newFirestation = firestations.stream() // java stream pour boucler sur une liste
			.filter(firestation -> !address.equalsIgnoreCase(firestation.getAddress()) // éliminer la personne qui match
					&&!station.equalsIgnoreCase(firestation.getStation()))	
			.collect(Collectors.toList());
			
			saftyData.setFirestations(newFirestation); // remettre la liste des personnes modifiées dans l'objet safetyData
			
			// save dans le fichier JSON
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("safetyAlertsData.json").getFile());
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(objectMapper.writeValueAsString(saftyData));
			writer.close();
			
			return true;
		}
		}




