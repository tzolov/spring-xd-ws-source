package io.pivotal.poc.xd.ws.source;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * Created by tzoloc on 9/19/16.
 */
public class TomcatWebServiceServer implements InitializingBean, DisposableBean, ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(TomcatWebServiceServer.class);

	private Tomcat tomcatServer;

	private AnnotationConfigWebApplicationContext serverContext;

	private ApplicationContext parentApplicationContext;

	private String createTempDir() {
		try {
			File tempFolder = File.createTempFile("tomcat.", ".workDir");
			tempFolder.delete();
			tempFolder.mkdir();
			tempFolder.deleteOnExit();
			return tempFolder.getAbsolutePath();
		}
		catch (IOException ex) {
			throw new RuntimeException("Unable to create temp directory", ex);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.tomcatServer = new Tomcat();
		//this.tomcatServer.setPort(0);
		this.tomcatServer.setPort(9889);
		this.tomcatServer.setBaseDir(createTempDir());

		this.serverContext = new AnnotationConfigWebApplicationContext();
		this.serverContext.setParent(parentApplicationContext);
		this.serverContext.register(WebServicesAutoConfiguration.class);

		// IS THIS CORRECT?
		this.serverContext.refresh();
		this.serverContext.start();


		Context context = this.tomcatServer.addContext("", System.getProperty("java.io.tmpdir"));

		this.tomcatServer.start();
		logger.info("Tomcat Started! >>>>>>>>>>>>>>>>>");
	}

	@Override
	public void destroy() throws Exception {
		this.tomcatServer.stop();
		logger.info("Tomcat Stopped! >>>>>>>>>>>>>>>>>");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.parentApplicationContext =  applicationContext;
	}
}