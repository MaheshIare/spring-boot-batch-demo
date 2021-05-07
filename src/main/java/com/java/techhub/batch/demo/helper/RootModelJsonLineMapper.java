package com.java.techhub.batch.demo.helper;

import org.springframework.batch.item.file.LineMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.techhub.batch.demo.model.PatientDetailModel;

public class RootModelJsonLineMapper implements LineMapper<PatientDetailModel> {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Interpret the line as a Json object and create a PatientDetailModel Entity from it.
     * 
     * @see LineMapper#mapLine(String, int)
     */
    @Override
    public PatientDetailModel mapLine(String line, int lineNumber) throws Exception {
        return mapper.readValue(line, PatientDetailModel.class);
    }

}