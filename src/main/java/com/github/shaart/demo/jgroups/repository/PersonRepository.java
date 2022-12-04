package com.github.shaart.demo.jgroups.repository;

import com.github.shaart.demo.jgroups.entity.PersonEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonRepository {

    private final List<PersonEntity> persons = new ArrayList<>();

    public List<PersonEntity> findAll() {
        return persons;
    }

    public PersonEntity create(String name) {
        var createdPerson = PersonEntity.builder()
                .name(name)
                .build();
        persons.add(createdPerson);
        return createdPerson;
    }

    public int deleteAll() {
        int size = persons.size();
        persons.clear();
        return size;
    }
}
