package com.java.techhub.custom.util;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.springframework.boot.test.context.SpringBootTest;

import com.java.techhub.batch.demo.SpringBootBatchDemoApplication;

@SpringBootTest(classes = { SpringBootBatchDemoApplication.class })
public class SpringBootBatchDemoApplicationTests {

	private ClientAndServer mockServer;

	@BeforeClass
	public void startServer() {
		mockServer = ClientAndServer.startClientAndServer(8080);
	}

	@AfterClass
	public void stopServer() {
		mockServer.stop();
	}

	@Test
	void contextLoads() {
	}

}
