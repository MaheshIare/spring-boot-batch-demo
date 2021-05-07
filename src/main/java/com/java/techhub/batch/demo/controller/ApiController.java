/**
 * 
 */
package com.java.techhub.batch.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.techhub.batch.demo.model.RootModel;
import com.java.techhub.batch.demo.model.StoreResponse;
import com.java.techhub.batch.demo.processor.DataProcessor;

/**
 * @author mahes
 *
 */
@RestController
@RequestMapping("/api/data")
public class ApiController {

	@Autowired
	private DataProcessor dataProcessor;

	@GetMapping
	public ResponseEntity<RootModel> readPatientData() throws Exception {
		return new ResponseEntity<>(dataProcessor.parseJson(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> processPatientData(@RequestBody RootModel rootModel) throws Exception {
		dataProcessor.prettyPrintJson(rootModel);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/store")
	public ResponseEntity<StoreResponse> readStoreResponse() throws Exception {
		return new ResponseEntity<StoreResponse>(dataProcessor.parseStoreJson(), HttpStatus.OK);
	}
}
