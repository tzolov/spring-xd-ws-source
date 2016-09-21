package io.pivotal.poc.xd.ws.source;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

/**
 * Created by tzoloc on 9/19/16.
 */
@EnableWs
@Configuration
@ImportResource("classpath:child/ws-child.xml")
public class WebServicesAutoConfiguration {

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		ServletRegistrationBean registration = new ServletRegistrationBean(servlet, "/*");
		///ServletRegistrationBean registration = new ServletRegistrationBean(servlet, "/ws");
		registration.addInitParameter("transformWsdlLocations", "true");
		registration.setLoadOnStartup(1);

		return registration;

	}

}