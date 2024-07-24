package com.eidiko.oraclesql.repository;

import com.eidiko.oraclesql.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
