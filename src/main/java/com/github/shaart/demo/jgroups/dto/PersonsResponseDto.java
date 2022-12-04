package com.github.shaart.demo.jgroups.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonsResponseDto {

    private List<PersonDto> persons;
}
