package com.sb.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.stream.StreamSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xsd")
public class XsdController {
	
	//On Jboss7.4 - http://127.0.0.1:8080/demo/hello -check if it read from application.properties
	//On Embedded Tomcat - http://127.0.0.1:8081/hello
	@GetMapping("/hello")
    public String sayHello() {
        return "Hello Chaitali!";
    }
	
	//Embedded Tomcat : http://localhost:8080/xsd/loaneligibility.xsd
	//JBOSS EAP 7.4 : http://127.0.0.1:8080/spring-boot-soap-ws/xsd/loaneligibility.xsd
	@GetMapping(value = "/loaneligibility.xsd", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<ClassPathResource> getloaneligibilityXsdFile() throws IOException {
		ClassPathResource xsdResource = new ClassPathResource("loaneligibility.xsd");
		return ResponseEntity.ok().body(xsdResource);
	}
	    
	//Embedded Tomcat : http://localhost:8080/xsd/loantearms.xsd
	//JBOSS EAP 7.4 : http://localhost:8080/spring-boot-soap-ws/xsd/loantearms.xsd
	@GetMapping(value = "/loantearms.xsd", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<ClassPathResource> getloantearmsXsdFile() throws IOException 
	{
	    ClassPathResource xsdResource = new ClassPathResource("loantearms.xsd");
	    return ResponseEntity.ok().body(xsdResource);
	}
}
