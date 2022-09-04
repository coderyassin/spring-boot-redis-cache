package org.yascode.springbootrediscache.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.yascode.springbootrediscache.entity.Person;
import org.yascode.springbootrediscache.repository.PersonRepository;
import org.yascode.springbootrediscache.repository.cache.RedisRepository;
import org.yascode.springbootrediscache.service.PersonService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final RedisTemplate redisTemplate;
    private final RedisRepository<String, String, Person> redisRepository;
    private static final String KEY = "PERSON";
    private static boolean CALLED;

    @PostConstruct
    public void init() {
        CALLED = false;
    }

    @Override
    public Person savePerson(Person person) {
        Person personSaved = this.personRepository.save(person);
        redisRepository.save(KEY, personSaved.getId().toString(), personSaved);
        return personSaved;
    }

    @Override
    public List<Person> fetchAllPerson() {
        if(!CALLED || (redisRepository.isEmpty(KEY))) {
            List<Person> personList = this.personRepository.findAll();
            if(!personList.isEmpty()) {
                Map<String, Person> redisMap = personList.stream()
                        .collect( Collectors.toMap(p -> p.getId().toString(), p -> p));
                redisRepository.saveAll(KEY, redisMap);
                CALLED = !CALLED;
            }
        }
        return redisRepository.getAllByKey(KEY);
    }

    @Override
    public Person fetchPersonById(Long id) {
        Person person = redisRepository.getByKey(KEY,id.toString());
        if(person != null)
            return person;
        Optional<Person> personById = this.personRepository.findById(id);
        if(personById.isPresent()) {
            Person returnedPerson = personById.get();
            redisRepository.save(KEY, returnedPerson.getId().toString(), returnedPerson);
            return returnedPerson;
        }
        return null;
    }

    @Override
    public void deletePerson(Long id) {
        this.personRepository.deleteById(id);
        redisRepository.delete(KEY,id.toString());
    }

    @Override
    public Person updatePerson(Long id, Person person) {
        Optional<Person> optionalPerson = this.personRepository.findById(id);
        Person person1;
        if(optionalPerson.isPresent()) {
            person1 = optionalPerson.get();
            if(person.getName() != null && (!person1.getName().equalsIgnoreCase(person.getName())))
                person1.setName(person.getName());
            if(person.getFollowers() != 0 && (person1.getFollowers() != (person.getFollowers())))
                person1.setFollowers(person.getFollowers());
            this.personRepository.save(person1);
            redisRepository.save(KEY, person1.getId().toString(), person1);
            return person1;
        }
        return null;
    }
}
