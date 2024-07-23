package com.pokeguide.service;

import com.pokeguide.dto.TermsDTO;
import com.pokeguide.entity.Terms;
import com.pokeguide.repository.TermsRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TermsService {

    private final TermsRepository termsRepository;
    private final ModelMapper modelMapper;

    public List<TermsDTO> getAllTerms() {

        List<Terms> termsList = termsRepository.findAll();
        return termsList.stream()
                .map(terms -> modelMapper.map(terms, TermsDTO.class))
                .collect(Collectors.toList());
    }
}
