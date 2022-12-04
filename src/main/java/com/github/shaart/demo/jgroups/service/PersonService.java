package com.github.shaart.demo.jgroups.service;

import com.github.shaart.demo.jgroups.dto.PersonDto;
import com.github.shaart.demo.jgroups.dto.PersonsDeleteResponseDto;
import com.github.shaart.demo.jgroups.dto.PersonsResponseDto;
import com.github.shaart.demo.jgroups.entity.PersonEntity;
import com.github.shaart.demo.jgroups.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public PersonsResponseDto findAll() {
        log.info("Searching for persons");
        List<PersonDto> persons = personRepository.findAll()
                .stream()
                .map(this::personToDto)
                .toList();
        log.info("Found {} persons", persons.size());
        return new PersonsResponseDto(persons);
    }

    private PersonDto personToDto(PersonEntity it) {
        return PersonDto.builder()
                .name(it.getName())
                .build();
    }

    public PersonDto createPerson(String name) {
        log.info("Creating person with name = {}", name);
        var created = personRepository.create(name);
        log.info("Created person with name = {}", name);
        return personToDto(created);
    }

    public PersonsDeleteResponseDto deleteAllPersons() {
        log.info("Deleting all persons");
        var count = personRepository.deleteAll();
        log.info("Deleted {} persons", count);
        return PersonsDeleteResponseDto.builder()
                .status("deleted %d persons".formatted(count))
                .build();
    }
}
