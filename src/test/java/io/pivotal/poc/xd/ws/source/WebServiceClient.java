package io.pivotal.poc.xd.ws.source;

import java.io.StringReader;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Created by tzoloc on 9/19/16.
 */
public class WebServiceClient {

	private static final String MESSAGE =
			"<message xmlns=\"http://tempuri.org\">Hello World</message>";

	private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();

	public static void main(String[] args) {
		WebServiceClient ws = new WebServiceClient();
		ws.setDefaultUri("http://localhost:9889/");
		//ws.setDefaultUri("http://localhost:8080/XDWebservice");
		ws.simpleSendAndReceive();
	}

	public void setDefaultUri(String defaultUri) {
		webServiceTemplate.setDefaultUri(defaultUri);
	}

	// send to the configured default URI
	public void simpleSendAndReceive() {
		StreamSource source = new StreamSource(new StringReader(MESSAGE));
		StreamResult result = new StreamResult(System.out);
		webServiceTemplate.sendSourceAndReceiveToResult(source, result);
	}

	// send to an explicit URI
	public void customSendAndReceive(String url) {
		StreamSource source = new StreamSource(new StringReader(MESSAGE));
		StreamResult result = new StreamResult(System.out);
		webServiceTemplate.sendSourceAndReceiveToResult(url,
				source, result);
	}

}
