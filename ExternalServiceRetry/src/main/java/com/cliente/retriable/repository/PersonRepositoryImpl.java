package com.cliente.retriable.repository;

import com.cliente.retriable.dto.PersonDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersonRepositoryImpl implements PersonRepository {
    private List<PersonDto> dtoList;

    public PersonRepositoryImpl() {

        dtoList = new ArrayList<>();

        dtoList.add(PersonDto.builder()
                .id(1L)
                .name("Alex")
                .lastName("Sintec")
                .build());

        dtoList.add(PersonDto.builder()
                .id(2L)
                .name("Carla")
                .lastName("Morejon")
                .build());

        dtoList.add(PersonDto.builder()
                .id(3L)
                .name("John")
                .lastName("Arévalo")
                .build());

    }


    @Override
    public PersonDto getPerson(Long id) {
        List result = dtoList.stream().filter(personDto ->
                personDto.getId().equals(id)
        ).collect(Collectors.toList());
        if (result.isEmpty()) {
            return null;
        }
        return (PersonDto) result.get(0);
    }
}
