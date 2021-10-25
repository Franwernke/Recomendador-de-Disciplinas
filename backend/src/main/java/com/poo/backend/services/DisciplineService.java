package com.poo.backend.services;

import java.util.List;
import java.util.stream.Collectors;

import com.poo.backend.dto.DisciplineDTO;
import com.poo.backend.entities.Discipline;
import com.poo.backend.repositories.DisciplineRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplineService {
  
  @Autowired
  private DisciplineRepository disciplineRepo;

  public List<DisciplineDTO> findAll() {
    List<Discipline> disciplines = disciplineRepo.findAll();
    return disciplines.stream().map(d -> new DisciplineDTO(d)).collect(Collectors.toList());
  }
}
