package com.eidiko;

import com.eidiko.mysql.entity.Employee;
import com.eidiko.mysql.repository.EmployeeRepository;
import com.eidiko.oraclesql.entity.Address;
import com.eidiko.oraclesql.repository.AddressRepository;
import com.eidiko.postgresql.entity.Fruit;
import com.eidiko.postgresql.repository.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultipleDbApplication implements CommandLineRunner {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private FruitRepository fruitRepository;

	public static void main(String[] args) {
		SpringApplication.run(MultipleDbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Employee employee = new Employee(101, "sanketh", "Java");
		employeeRepository.save(employee);

		Address address = new Address(11, "Ameerpet", "Hyderabad", "TS");
		addressRepository.save(address);

		Fruit fruit = new Fruit(20, "Apple", "Red");
		fruitRepository.save(fruit);

	}
}
