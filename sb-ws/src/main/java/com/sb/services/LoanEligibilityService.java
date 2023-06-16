package com.sb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.sb.customer.*;
import com.sb.loaneligibility.*;
import com.sb.repository.CustomerRepository;
import com.sb.model.Customer;

@Service
public class LoanEligibilityService {
	
		@Autowired
		CustomerRepository customerRepository;
		
		 public Acknowledgement checkLoanEligibilityById(CustomerRequest soapRequest) 
		 {
			 
			Acknowledgement acknowledgement = new Acknowledgement();
			List<String> mismatchCriteriaList = acknowledgement.getCriteriaMismatch();
			
			Optional<Customer> customerOptional =customerRepository.findById(Integer.parseInt(soapRequest.getId()));
			
			if (customerOptional.isPresent()) 
			{
			       Customer customer = customerOptional.get();
			
			        if (!(customer.getAge() > 30 && customer.getAge() <= 60)) 
			        {
			        	mismatchCriteriaList.add("Person age should in between 30 to 60");
			        }
			        if (!(customer.getYearlyIncome() > 200000)) 
			        {
			        	mismatchCriteriaList.add("minimum income should be more than 200000");
			        }
			        if (!(customer.getCibilScore() > 500)) 
			        {
			        	mismatchCriteriaList.add("Low CIBIL Score please try aftaer 6 month");
			        }
			}
			else 
			{
				mismatchCriteriaList.add("There is no matching id. Please try another id");
			}     
			if (mismatchCriteriaList.size() > 0) 
			{
	        	acknowledgement.setApprovedAmount(0);
	        	acknowledgement.setIsEligible(false);
	        } else {
	        	acknowledgement.setApprovedAmount(500000);
	        	acknowledgement.setIsEligible(true);
	        	mismatchCriteriaList.clear();
	        }
			return acknowledgement;
		}

		public CreateCustomerResponse AddCustomer(Customer customer) 
		{
			customerRepository.save(customer);
			CreateCustomerResponse response = new CreateCustomerResponse();
			response.setCustomerId(customer.getCustomerId());
			response.setMessage("Customer " +customer.getCustomerId() +" created successfully");
			response.setStatus("SUCCESS");
			return response;
		}
		
		public DeleteCustomerResponse deleteCustomer(Customer customer)
		{ 
			DeleteCustomerResponse response = new DeleteCustomerResponse();
			try 
			{
				customerRepository.deleteById(customer.getCustomerId());
				response.setCustomerId(customer.getCustomerId());
				response.setMessage("Customer " + customer.getCustomerId() + " deleted successfully");
				response.setStatus("SUCCESS");
			} catch (EmptyResultDataAccessException e) 
			{
				// Handle the case when customerId does not exist
				response.setMessage("Customer " + customer.getCustomerId() + " does not exist");
				response.setStatus("FAILURE");
			}
			return response;
		}
		
		public UpdateCustomerResponse updateCustomer(Customer customer)
		{
			UpdateCustomerResponse response = new UpdateCustomerResponse();
			try 
			{
				customerRepository.save(customer);
				response.setCustomerId(customer.getCustomerId());
				response.setMessage("Customer " + customer.getCustomerId() + " updated successfully");
				response.setStatus("SUCCESS");
			} catch (EmptyResultDataAccessException e) 
			{
				// Handle the case when customerId does not exist
				response.setMessage("Customer " + customer.getCustomerId() + " does not exist");
				response.setStatus("FAILURE");
			}
			return response;
		}
		
		public GetCustomerResponse getCustomer(GetCustomerRequest soapRequest) 
		{
			GetCustomerResponse response = new GetCustomerResponse();
			Optional<Customer> customerOptional =customerRepository.findById(soapRequest.getCustomerId());
			Customer customer = customerOptional.get();
			if ( customerOptional.isPresent()) 
			{
					  
				       response.setCustomerId(customer.getCustomerId());
				       response.setAge(customer.getAge());
				       response.setCibilScore(customer.getCibilScore());
				       response.setCustomerName(customer.getCustomerName());
				       response.setEmploymentMode(customer.getEmploymentMode());
				       response.setYearlyIncome(customer.getYearlyIncome());
				       response.setMessage("Found Customer " + customer.getCustomerId() + " successfully");
				       response.setStatus("SUCCESS");
			}
			return response;
		}
}
