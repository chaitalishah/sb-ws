package com.sb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sb.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, Integer>{

}
