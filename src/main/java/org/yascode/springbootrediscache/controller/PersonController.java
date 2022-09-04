package org.yascode.springbootrediscache.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.yascode.springbootrediscache.entity.Person;
import org.yascode.springbootrediscache.service.PersonService;

import java.util.List;


@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final PersonService personService;

    @RequestMapping(value = "/{personId}", method = RequestMethod.GET)
    public Person getPerson(@PathVariable String personId) {
        LOG.debug("Getting person with ID {}.", personId);
        return personService.fetchPersonById(Long.valueOf(personId));
    }

    @GetMapping("/fetchAllPerson")
    public List<Person> getAllPerson() {
        LOG.debug("Getting all person");
        return personService.fetchAllPerson();
    }

    @PostMapping("")
    public Person savePerson(@RequestBody Person person) {
        LOG.debug("Save a person");
        return personService.savePerson(person);
    }

    @PutMapping("/{personId}")
    public Person updatePerson(@PathVariable String personId, @RequestBody Person person) {
        LOG.debug("Update a person");
        return personService.updatePerson(Long.valueOf(personId), person);
    }

    @DeleteMapping("/{personId}")
    public void deletePerson(@PathVariable String personId) {
        personService.deletePerson(Long.valueOf(personId));
    }

}
