package com.sb.endpoint;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.sb.customer.*;
import com.sb.loaneligibility.*;
import com.sb.model.Customer;
import com.sb.services.LoanEligibilityService;

@Endpoint
public class LoanEligibilityEndpoint {

	private static final String NAMESPACE = "http://www.sb.com/loanEligibility";
	private static final String NAMESPACE2 = "http://www.sb.com/customer";

	
	@Autowired
	private LoanEligibilityService service;
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "CustomerRequest")
	@ResponsePayload
	public Acknowledgement getLoanStatus(@RequestPayload CustomerRequest request ) {
		
		return service.checkLoanEligibilityById(request);
	}
	
	@PayloadRoot(namespace = NAMESPACE2, localPart = "CreateCustomerRequest")
	@ResponsePayload
	public CreateCustomerResponse createCustomer(@RequestPayload CreateCustomerRequest request) {
		
		Customer customer = new Customer();
		BeanUtils.copyProperties(request, customer); //copy fields from soap request to customer model object
		return service.AddCustomer(customer);
	}
	
	@PayloadRoot(namespace = NAMESPACE2, localPart = "DeleteCustomerRequest")
	@ResponsePayload
	public DeleteCustomerResponse deleteCustomer(@RequestPayload DeleteCustomerRequest request) {
		
		Customer customer = new Customer();
		BeanUtils.copyProperties(request, customer); //copy fields from soap request to customer model object
		return service.deleteCustomer(customer); //delete
	}
	
	@PayloadRoot(namespace = NAMESPACE2, localPart = "UpdateCustomerRequest")
	@ResponsePayload
	public UpdateCustomerResponse updateCustomer(@RequestPayload UpdateCustomerRequest request) {
		
		Customer customer = new Customer();
		BeanUtils.copyProperties(request, customer); //copy fields from soap request to customer model object
		return service.updateCustomer(customer); //delete
	}

	@PayloadRoot(namespace = NAMESPACE2, localPart = "GetCustomerRequest")
	@ResponsePayload
	public GetCustomerResponse getCustomer(@RequestPayload GetCustomerRequest request) {
		
		//BeanUtils.copyProperties(request, customer); //copy fields from soap request to customer model object
		return service.getCustomer(request); 
	}
	
}
