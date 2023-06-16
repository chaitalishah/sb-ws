package com.sb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/*import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;*/

@Document
public class Customer {
   
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerId;
	private String customerName;
	private int age;
	private int yearlyIncome;
	private int cibilScore;
	private String employmentMode;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getYearlyIncome() {
		return yearlyIncome;
	}
	public void setYearlyIncome(int yearlyIncome) {
		this.yearlyIncome = yearlyIncome;
	}
	public int getCibilScore() {
		return cibilScore;
	}
	public void setCibilScore(int cibilScore) {
		this.cibilScore = cibilScore;
	}
	public String getEmploymentMode() {
		return employmentMode;
	}
	public void setEmploymentMode(String employmentMode) {
		this.employmentMode = employmentMode;
	}
}