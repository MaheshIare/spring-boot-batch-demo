/**
 * 
 */
package com.java.techhub.batch.demo.processor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.java.techhub.batch.demo.model.OperationHours;
import com.java.techhub.batch.demo.model.Patient;
import com.java.techhub.batch.demo.model.PatientDetailModel;
import com.java.techhub.batch.demo.model.Prescription;
import com.java.techhub.batch.demo.model.RootModel;
import com.java.techhub.batch.demo.model.StoreResponse;

/**
 * @author mahes
 *
 */
@Component
public class DataProcessor {

	@Autowired
	private ObjectMapper objectMapper;
	private static final Logger log = LoggerFactory.getLogger(DataProcessor.class);
	private static final String FLAG = "Flagged";
	private static final String EIGHT_PM_TIME = "20:00";

	public void prettyPrintJson(RootModel rootModel) throws JsonProcessingException {
		ObjectWriter writer = objectMapper.writer().withDefaultPrettyPrinter();
		String prettyJson = writer.writeValueAsString(rootModel.getPatientDetailModel());
		log.info("Pretty printed JSON:\n{}", prettyJson);
	}
	
	public PatientDetailModel parseJson() throws JsonParseException, JsonMappingException, IOException {
		log.info("Reading data from json file...");
		PatientDetailModel patientDetailModel = objectMapper.readValue(new ClassPathResource("data/data-parser.json").getFile(),
				new TypeReference<PatientDetailModel>() {
				});
		return patientDetailModel;
	}

	public StoreResponse parseStoreJson() throws JsonParseException, JsonMappingException, IOException {
		log.info("Reading store data from json file...");
		StoreResponse rootModel = objectMapper.readValue(new ClassPathResource("data/store-data.json").getFile(),
				new TypeReference<StoreResponse>() {
				});
		return rootModel;
	}

	public RootModel processPatientDetails(RootModel rootModel) throws IOException {
		boolean flag = validateCurrentTimeWithStoreTime(rootModel);
		if (flag) {
			flagAllPrescriptions(rootModel.getPatientDetailModel());
		} else {
			log.info("Processing individual prescription...");
			if (!rootModel.getPatientDetailModel().getPatientDetails().isEmpty()) {
				// Check if prescriptions for the patients can be flagged
				for (Patient record : rootModel.getPatientDetailModel().getPatientDetails()) {
					if (!record.getPrescriptionDetails().isEmpty()) {
						for (Prescription prescription : record.getPrescriptionDetails()) {
							// Check if precription check in time falls in last 60 mins
							if (isPrescriptionFlagged(prescription.getCheckInTime())) {
								prescription.setNewFlag(FLAG);
								record.setNewFlag(FLAG);
								rootModel.getPatientDetailModel().setNewFlag(FLAG);
							}
						}
					}
				}
			}
		}
		return rootModel;
	}

	private boolean isPrescriptionFlagged(String checkInTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXXXX");
		// Current date time
		LocalDateTime currentDateTime = LocalDateTime.now();
		// Prescription checkin time
		LocalDateTime prescriptionDateTime = LocalDateTime.parse(checkInTime, formatter);
		// Current time minus two hrs
		LocalDateTime currMinusTwoHrs = currentDateTime.minusHours(2);
		// Current time minus one hrs
		LocalDateTime currMinusOneHrs = currentDateTime.minusHours(1);
		log.info("==============START==============");
		log.info("Current date time: {}", currentDateTime);
		log.info("Current Minus Two hrs: {}", currMinusTwoHrs);
		log.info("Current Minus One hrs: {}", currMinusOneHrs);
		log.info("Prescription date time: {}", prescriptionDateTime);
		log.info("Is valid prescription time: {}", prescriptionDateTime.compareTo(currentDateTime));
		log.info("Is Prescription falls under criteria: {}",
				(prescriptionDateTime.isAfter(currMinusTwoHrs) && prescriptionDateTime.isBefore(currMinusOneHrs)));
		System.out.println("==============END==============");
		if (prescriptionDateTime.compareTo(currentDateTime) < 0 && prescriptionDateTime.isAfter(currMinusTwoHrs)
				&& prescriptionDateTime.isBefore(currMinusOneHrs)) {
			return true;
		}
		return false;
	}

	private boolean validateCurrentTimeWithStoreTime(RootModel rootModel) throws IOException {
		StoreResponse storeData = rootModel.getStoreResponse();
		LocalDateTime currentDateTime = LocalDateTime.now();
		String currentTime = currentDateTime.getHour() + ":"
				+ (currentDateTime.getMinute() < 9 ? "0" + currentDateTime.getMinute() : currentDateTime.getMinute());
		String weekDay = currentDateTime.getDayOfWeek().name();
		String closeTime = getCloseTimeFromStore(weekDay, storeData);
		LocalTime storeCloseTime = LocalTime.parse(closeTime);
		LocalTime storeCloseTimeMinusOneHr = storeCloseTime.minusHours(1);
		LocalTime currentLocalTime = LocalTime.parse(currentTime);
		LocalTime eightPmTime = LocalTime.parse(EIGHT_PM_TIME);
		log.info("===========START==========");
		log.info("Current date time: {}", currentDateTime);
		log.info("Current Time: {}", currentTime);
		log.info("WeekDay from current date: {}", weekDay);
		log.info("Store close time for {} : {}", weekDay, closeTime);
		log.info("Store close time in LocalTime format: {}", storeCloseTime);
		log.info("Store close time minus one Hr: {}", storeCloseTimeMinusOneHr);
		log.info("Current time in LocalTime format: {}", currentLocalTime);
		log.info("Eight PM time in LocalTime format: {}", eightPmTime);
		log.info("Is current time > 8PM: {} ", currentLocalTime.isAfter(eightPmTime));
		log.info("Is current time > Store closetime -1 : {}", currentLocalTime.isAfter(storeCloseTimeMinusOneHr));
		log.info("===========END==========");
		if (currentLocalTime.isAfter(eightPmTime) || currentLocalTime.isAfter(storeCloseTimeMinusOneHr)) {
			return true;
		}
		return false;
	}

	private String getCloseTimeFromStore(String weekDay, StoreResponse storeResponse) {
		for (OperationHours opHrs : storeResponse.getPayload().getPharmacyInfo().getHoursOfOperation()) {
			if (opHrs.getDayOfWeek().equalsIgnoreCase(weekDay)) {
				return opHrs.getCloseTime();
			}
		}
		return null;
	}

	private void flagAllPrescriptions(PatientDetailModel patientDetailModel) {
		log.info("Flagging all patients...");
		if (!patientDetailModel.getPatientDetails().isEmpty()) {
			for (Patient record : patientDetailModel.getPatientDetails()) {
				for (Prescription prescription : record.getPrescriptionDetails()) {
					prescription.setNewFlag(FLAG);
				}
				record.setNewFlag(FLAG);
			}
		}
		patientDetailModel.setNewFlag(FLAG);
	}
}
