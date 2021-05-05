/**
 * 
 */
package com.java.techhub.batch.demo.interceptors;

import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

/**
 * @author mahes
 *
 */
public class ApiRequestInterceptor implements ClientHttpRequestInterceptor {

	private static final Logger log = LoggerFactory.getLogger(ApiRequestInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		logRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		logResponse(response);
		return response;
	}

	private void logRequest(HttpRequest request, byte[] body) throws IOException {
		log.info("===========================Request begin================================================");
		log.info("URI         : {}", request.getURI());
		log.info("Method      : {}", request.getMethod());
		log.info("Headers     : {}", request.getHeaders());
		if (log.isDebugEnabled())
			log.debug("Request body: {}", new String(body, "UTF-8"));
		log.info("==========================Request end================================================");
	}

	private void logResponse(ClientHttpResponse response) throws IOException {
		log.info("============================Response begin==========================================");
		log.info("Status code  : {}", response.getStatusCode());
		log.info("Status text  : {}", response.getStatusText());
		log.info("Headers      : {}", response.getHeaders());
		if (log.isDebugEnabled())
			log.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
		log.info("=======================Response end=================================================");
	}

}
