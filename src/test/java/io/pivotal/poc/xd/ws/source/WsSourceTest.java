package io.pivotal.poc.xd.ws.source;

import static org.junit.Assert.assertTrue;
import static org.springframework.xd.dirt.test.process.SingleNodeProcessingChainSupport.chainConsumer;

import java.io.StringReader;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.xd.dirt.server.singlenode.SingleNodeApplication;
import org.springframework.xd.dirt.test.SingleNodeIntegrationTestSupport;
import org.springframework.xd.dirt.test.SingletonModuleRegistry;
import org.springframework.xd.dirt.test.process.SingleNodeProcessingChainConsumer;
import org.springframework.xd.module.ModuleType;
import org.springframework.xd.test.RandomConfigurationSupport;


/**
 * Created by tzoloc on 9/19/16.
 */
public class WsSourceTest {

	private static SingleNodeApplication application;

	private static int RECEIVE_TIMEOUT = 6000;

	private static final String MESSAGE = "<message xmlns=\"http://tempuri.org\">Hello World</message>";

	private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();


	/**
	 * Start the single node container, binding random unused ports, etc. to not conflict with any other instances
	 * running on this host. Configure the ModuleRegistry to include the project module.
	 */
	@BeforeClass
	public static void setUp() {

		RandomConfigurationSupport randomConfigSupport = new RandomConfigurationSupport();

		application = new SingleNodeApplication().run();

		SingleNodeIntegrationTestSupport singleNodeIntegrationTestSupport =
				new SingleNodeIntegrationTestSupport(application);

		singleNodeIntegrationTestSupport.addModuleRegistry(
				new SingletonModuleRegistry(ModuleType.source, "ws-source"));
	}


//	@AfterClass
//	public static void destroy() {
//		application.close();
//		RandomConfigurationSupport.cleanup();
//	}

	@Test
	public void test() {

		SingleNodeProcessingChainConsumer chain = chainConsumer(application, "wsStream", "ws-source");

//		StreamSource source = new StreamSource(new StringReader(MESSAGE));
//		StreamResult result = new StreamResult(System.out);
//		webServiceTemplate.setDefaultUri("http://localhost:9889/");
//		webServiceTemplate.sendSourceAndReceiveToResult(source, result);

		Object payload = chain.receivePayload(RECEIVE_TIMEOUT);
		System.out.println(payload);
//
//		assertTrue(payload instanceof String);

		chain.destroy();
	}

}
