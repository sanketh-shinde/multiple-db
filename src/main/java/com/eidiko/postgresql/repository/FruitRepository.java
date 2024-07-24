package com.eidiko.postgresql.repository;

import com.eidiko.postgresql.entity.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FruitRepository extends JpaRepository<Fruit, Integer> {
}
