/**
 * 
 */
package com.java.techhub.batch.demo.processor;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
	private static final String EIGHT_PM_TIME = "22:00";
	private static final String DISPLAY_PROMPT = "Y";
	private static final String CHECK_IN_WINDOW_DELIMITTER = "-";

	public void prettyPrintJson(RootModel rootModel) throws JsonProcessingException {
		ObjectWriter writer = objectMapper.writer().withDefaultPrettyPrinter();
		String prettyJson = writer.writeValueAsString(rootModel);
		log.info("Pretty printed JSON:\n{}", prettyJson);
	}

	public RootModel parseJson() throws JsonParseException, JsonMappingException, IOException {
		log.info("Reading data from json file...");
		RootModel rootModel = objectMapper.readValue(new ClassPathResource("data/data-parser.json").getFile(),
				new TypeReference<RootModel>() {
				});
		return rootModel;
	}

	public StoreResponse parseStoreJson() throws JsonParseException, JsonMappingException, IOException {
		log.info("Reading store data from json file...");
		StoreResponse rootModel = objectMapper.readValue(new ClassPathResource("data/store-data.json").getFile(),
				new TypeReference<StoreResponse>() {
				});
		return rootModel;
	}

	public RootModel processPatientDetails(RootModel rootModel) throws IOException {
		StoreResponse storeData = parseStoreJson();
		boolean flag = validateCurrentTimeWithStoreTime(storeData);
		if (flag) {
			flagAllPrescriptions(rootModel);
		} else {
			log.info("Processing individual prescription...");
			if (!rootModel.getPatientDetails().isEmpty()) {
				// Check if prescriptions for the patients can be flagged
				for (Patient record : rootModel.getPatientDetails()) {
					if (!record.getPrescriptionDetails().isEmpty()) {
						for (Prescription prescription : record.getPrescriptionDetails()) {
							// Check if precription check in time falls in last 60 mins
							if (prescription.getDispPrompt().equalsIgnoreCase(DISPLAY_PROMPT) && isPrescriptionFlagged(
									prescription.getCheckInTime(), storeData.getPayload().getValue())) {
								prescription.setNewFlag(FLAG);
								record.setNewFlag(FLAG);
								rootModel.setNewFlag(FLAG);
							}
						}
					}
				}
			}
		}
		return rootModel;
	}

	private boolean isPrescriptionFlagged(String checkInTime, String checkInPromptWindow) {
		SimpleDateFormat sdWithUtc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		sdWithUtc.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		Date prescriptionDateTime = null;
		Date currentDateTime = null;
		try {
			prescriptionDateTime = sd.parse(checkInTime);
			currentDateTime = sd.parse(sdWithUtc.format(new Date()));
		} catch (Exception ex) {
			log.error("Exception occured due to: {}", ex);
		}
		String[] window = checkInPromptWindow.split(CHECK_IN_WINDOW_DELIMITTER, 2);
		// Current time minus window[1] hrs
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDateTime);
		cal.add(Calendar.HOUR, Integer.parseInt(CHECK_IN_WINDOW_DELIMITTER + window[1]));
		Date currMinusTwoHrs = cal.getTime();
		// Current time minus window[0] hrs
		cal = Calendar.getInstance();
		cal.setTime(currentDateTime);
		cal.add(Calendar.HOUR, Integer.parseInt(CHECK_IN_WINDOW_DELIMITTER + window[0]));
		Date currMinusOneHrs = cal.getTime();
		log.info("==============START==============");
		log.info("Current date time: {}", currentDateTime);
		log.info("Current Minus window[1] hrs: {}", currMinusTwoHrs);
		log.info("Current Minus window[0] hrs: {}", currMinusOneHrs);
		log.info("Prescription date time: {}", prescriptionDateTime);
		log.info("Is valid prescription time: {}", prescriptionDateTime.compareTo(currentDateTime));
		log.info("Is Prescription falls under criteria: {}",
				(prescriptionDateTime.after(currMinusTwoHrs) && prescriptionDateTime.before(currMinusOneHrs)));
		log.info("==============END==============");
		if (prescriptionDateTime.compareTo(currentDateTime) < 0 && prescriptionDateTime.after(currMinusTwoHrs)
				&& prescriptionDateTime.before(currMinusOneHrs)) {
			return true;
		}
		return false;
	}

	private boolean validateCurrentTimeWithStoreTime(StoreResponse storeData) throws IOException {
		Date currentDateTime = null;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		SimpleDateFormat time = new SimpleDateFormat("HH:mm");
		SimpleDateFormat dayofWeek = new SimpleDateFormat("EEEE");
		try {
			currentDateTime = sd.parse(sd.format(new Date()));
		} catch (Exception ex) {
			log.error("Exception occured due to: {}", ex);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDateTime);
		String currentTime = time.format(currentDateTime);
		String weekDay = dayofWeek.format(currentDateTime);
		String closeTime = getCloseTimeFromStore(weekDay, storeData);
		Date closeTimeDt = null;
		Date eightPmTimeDt = null;
		Date currentTimeDt = null;
		try {
			closeTimeDt = time.parse(closeTime);
			eightPmTimeDt = time.parse(EIGHT_PM_TIME);
			currentTimeDt = time.parse(currentTime);
		} catch (ParseException pse) {
			log.error("Exception occured due to: {}", pse);
		}
		cal = Calendar.getInstance();
		cal.setTime(closeTimeDt);
		cal.add(Calendar.HOUR, -1);
		Date storeCloseTimeMinusOneHr = cal.getTime();
		log.info("===========START==========");
		log.info("Current date time: {}", currentDateTime);
		log.info("Current Time: {}", currentTime);
		log.info("WeekDay from current date: {}", weekDay);
		log.info("Store close time for {} : {}", weekDay, closeTime);
		log.info("Store close time minus one Hr: {}", storeCloseTimeMinusOneHr);
		log.info("Is current time > 8PM: {} ", currentTimeDt.after(eightPmTimeDt));
		log.info("Is current time > Store closetime -1 : {}", currentTimeDt.after(storeCloseTimeMinusOneHr));
		log.info("===========END==========");
		if (currentTimeDt.after(eightPmTimeDt) || currentTimeDt.after(storeCloseTimeMinusOneHr)) {
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

	private void flagAllPrescriptions(RootModel rootModel) {
		log.info("Flagging all patients...");
		if (!rootModel.getPatientDetails().isEmpty()) {
			for (Patient record : rootModel.getPatientDetails()) {
				for (Prescription prescription : record.getPrescriptionDetails()) {
					prescription.setNewFlag(FLAG);
				}
				record.setNewFlag(FLAG);
			}
		}
		rootModel.setNewFlag(FLAG);
	}
}
