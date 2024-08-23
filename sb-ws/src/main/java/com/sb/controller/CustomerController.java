package com.sb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.model.Customer;
import com.sb.repository.CustomerRepository;

//TODO: ADD RequestParam too. So we can identify URL change in code with ? and PathVariable.
//TODO: Add UPSERT operation via PUT
//TODO : Add PAtch operation for only specified update 

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	
	@GetMapping("/findAll")
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	
	@GetMapping("/findById/{id}")
	public Optional<Customer> getCustomer(@PathVariable int id) {
		return customerRepository.findById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable int id) {
		customerRepository.deleteById(id);
		return "Customer deleted with id : " + id;
	}
	
	@PostMapping("/addCustomer")
	public String saveCustomer(@RequestBody Customer customer) {
		customerRepository.save(customer);
		return "Added customer with id : " + customer.getCustomerId();
	}
/**
	@PutMapping("/{id}")
    	public User upsertUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id); // Ensure the ID is set for the upsert
        return userService.saveOrUpdateUser(user);
   	 }
**/
}
