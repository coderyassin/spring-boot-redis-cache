package org.yascode.springbootrediscache.service;

import org.yascode.springbootrediscache.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Person savePerson(Person person);

    List<Person> fetchAllPerson();

    Person fetchPersonById(Long id);

    void deletePerson(Long id);

    Person updatePerson(Long id, Person person);
}
