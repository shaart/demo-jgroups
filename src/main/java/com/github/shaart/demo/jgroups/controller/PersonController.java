package com.github.shaart.demo.jgroups.controller;

import com.github.shaart.demo.jgroups.dto.PersonsDeleteResponseDto;
import com.github.shaart.demo.jgroups.dto.PersonDto;
import com.github.shaart.demo.jgroups.service.PersonService;
import com.github.shaart.demo.jgroups.dto.PersonsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/persons")
    public PersonsResponseDto getPersons() {
        log.info("Get request for all persons");
        PersonsResponseDto response = personService.findAll();
        log.info("Returned response with {} persons", response.getPersons().size());
        return response;
    }

    @PostMapping("/persons/{name}")
    public PersonDto createPerson(@PathVariable String name) {
        return personService.createPerson(name);
    }

    @DeleteMapping("/persons")
    public PersonsDeleteResponseDto clearPersons() {
        return personService.deleteAllPersons();
    }
}
