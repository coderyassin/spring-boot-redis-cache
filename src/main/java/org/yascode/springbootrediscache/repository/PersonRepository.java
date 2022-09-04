package org.yascode.springbootrediscache.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yascode.springbootrediscache.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long > {
}
