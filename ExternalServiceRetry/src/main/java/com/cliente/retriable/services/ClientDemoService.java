package com.cliente.retriable.services;

import com.cliente.retriable.dto.PersonDto;
import com.cliente.retriable.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientDemoService {
    private PersonRepository repository;

    public PersonDto getData(Long id) {
        return repository.getPerson(id);
    }
}
