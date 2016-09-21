package io.pivotal.poc.xd.ws.source;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.springframework.integration.xml.source.DomSourceFactory;

/**
 * Created by tzoloc on 9/20/16.
 */
public class SimpleEchoResponder {

	public Source issueResponseFor(DOMSource request) {
		System.out.println("BOZAAAA@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		return new DomSourceFactory().createSource(
				"<echoResponse xmlns=\"http://www.springframework.org/spring-ws/samples/echo\">" +
						request.getNode().getTextContent() + "</echoResponse>");
	}
}
