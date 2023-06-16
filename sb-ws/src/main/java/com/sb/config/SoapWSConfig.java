package com.sb.config;

import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.adapter.method.MethodArgumentResolver;
import org.springframework.ws.server.endpoint.adapter.method.MethodReturnValueHandler;

import java.util.Collections;
import java.util.List;

import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;

import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;

//import org.springframework.xml.xsd.XsdSchema;
import javax.wsdl.WSDLException;

import org.springframework.xml.xsd.XsdSchemaCollection;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;


@Configuration
@EnableWs
public class SoapWSConfig extends WsConfigurerAdapter{

	@Bean
	ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(context);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<MessageDispatcherServlet>(servlet, "/ws/*");
	}

	//WSDL generated Tomcat : http://localhost:8080/ws/loanEligibility.wsdl
	@Bean(name = "loanEligibility")
	//DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
	DefaultWsdl11Definition defaultWsdl11Definition(XsdSchemaCollection schemaCollection) throws WSDLException {
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("LoanEligibilityPortType"); 
		defaultWsdl11Definition.setServiceName("LoanEligibilityService");
		defaultWsdl11Definition.setLocationUri("/ws");
		defaultWsdl11Definition.setTargetNamespace("http://www.sb.com/loanEligibility");
		//defaultWsdl11Definition.setSchema(schema);
		defaultWsdl11Definition.setSchemaCollection(schemaCollection);
		return defaultWsdl11Definition;
	}
	
	//used for single schema
	/*@Bean
	XsdSchema schema() {
		return new SimpleXsdSchema(new ClassPathResource("loaneligibility.xsd"));
	}*/
	
	//used for collection of schemas
	@Bean
	public XsdSchemaCollection schemaCollection() {
		
	    CommonsXsdSchemaCollection commonsXsdSchemaCollection = new CommonsXsdSchemaCollection(
	            new ClassPathResource("loaneligibility.xsd"),
	            new ClassPathResource("Customer.xsd"),
	            new ClassPathResource("loantearms.xsd"));//SchemaCollection 
	   
	    commonsXsdSchemaCollection.setInline(true); //This will merge schemas into a single schema document.
	    return commonsXsdSchemaCollection;
	}
	
	
	@Bean
	public XwsSecurityInterceptor securityInterceptor() {
		XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
		securityInterceptor.setCallbackHandler(callbackHandler());
		securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		return securityInterceptor;
	}

	@Bean
	public SimplePasswordValidationCallbackHandler callbackHandler() {
		SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
		callbackHandler.setUsersMap(Collections.singletonMap("admin", "pwd123"));
		return callbackHandler;
	}

	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(payloadLoggingInterceptor());
		interceptors.add(payloadValidatingInterceptor());
		interceptors.add(securityInterceptor());
	}

	@Bean
	public PayloadLoggingInterceptor payloadLoggingInterceptor() {
		return new PayloadLoggingInterceptor();
	}

	@Bean
	public PayloadValidatingInterceptor payloadValidatingInterceptor() {
		final PayloadValidatingInterceptor payloadValidatingInterceptor = new PayloadValidatingInterceptor();
		
		//used for only one schema
		//payloadValidatingInterceptor.setSchema(new ClassPathResource("loaneligibility.xsd"));
		
		Resource[] schemas = {  new ClassPathResource("loaneligibility.xsd"),
								new ClassPathResource("Customer.xsd"),
								new ClassPathResource("loantearms.xsd") };
		
		payloadValidatingInterceptor.setSchemas(schemas );
		return payloadValidatingInterceptor;
	}
}
