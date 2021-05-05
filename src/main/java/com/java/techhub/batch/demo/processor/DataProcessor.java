/**
 * 
 */
package com.java.techhub.batch.demo.processor;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.java.techhub.batch.demo.model.Patient;
import com.java.techhub.batch.demo.model.Prescription;
import com.java.techhub.batch.demo.model.RootModel;

/**
 * @author mahes
 *
 */
@Component
public class DataProcessor {
	
	private static final Logger log = LoggerFactory.getLogger(DataProcessor.class);
	
	public RootModel parseJson() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		log.info("Reading data from json file...");
		RootModel rootModel = mapper.readValue(new ClassPathResource("data/data-parser.json").getFile(),
				new TypeReference<RootModel>() {
				});
		return rootModel;
	}

	public RootModel processPatientDetails(RootModel rootModel) throws JsonProcessingException {
		log.info("Processing patients details...");
		if (!rootModel.getPatientDetails().isEmpty()) {
			// Check if prescriptions for the patients can be flagged
			for (Patient record : rootModel.getPatientDetails()) {
				if (!record.getPrescriptionDetails().isEmpty()) {
					for (Prescription prescription : record.getPrescriptionDetails()) {
						// Check if precription check in time falls in last 60 mins
						if (prescription.isPrescriptionFlagged()) {
							record.setNewFlag("Flagged");
							rootModel.setNewFlag("Flagged");
						}
					}
				}
			}
		}
		return rootModel;
	}

	public void prettyPrintJson(RootModel rootModel) throws JsonProcessingException {
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String prettyJson = writer.writeValueAsString(rootModel);
		log.info("Pretty printed JSON: {}\n", prettyJson);
	}
}
