package com.cliente.retriable.repository;

import com.cliente.retriable.dto.PersonDto;

public interface PersonRepository {
    public PersonDto getPerson(Long id);
}
